<template>
  <div class="login">
    <video autoplay loop muted class="login-bg">
      <source src="../assets/media/index-back.mp4" type="video/mp4" />
    </video>
    <div class="center">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <div class="title">
          <img src="../assets/logo/logo.png" class="logo" />
          <span class="font">Chat MASTER</span>
        </div>
        <div class="sub-title">ChatMASTER致于与为用户及伙伴提供智能化AI服务</div>
        <el-tabs v-if="activeName != 'third'" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="账号密码登陆" name="first"></el-tab-pane>
          <el-tab-pane label="手机号登陆" name="second"></el-tab-pane>
        </el-tabs>
        <!--  账号密码登陆  -->
        <div v-if="activeName == 'first'">
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="请输入您的账号"
            >
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="请输入您的密码"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item v-if="captchaOnOff">
            <JcRange status="status" :successFun="onMpanelSuccess" :errorFun="onMpanelError"></JcRange>
          </el-form-item>
        </div>
        <!--  手机验证码登陆  -->
        <div v-if="activeName == 'second'">
          <el-form-item prop="tel">
            <el-input
              v-model="loginForm.tel"
              type="text"
              auto-complete="off"
              placeholder="请输入您的手机号"
            >
              <svg-icon slot="prefix" icon-class="mobile" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div class="code">
              <el-input
                v-model="loginForm.code"
                auto-complete="off"
                placeholder="验证码"
                style="width: 55%"
              >
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
              </el-input>
              <div class="login-code">
                <el-button v-show="smsShow" @click="getSmsCode">获取验证码</el-button>
                <el-button v-show="!smsShow">{{ count }}s后重新获取</el-button>
              </div>
            </div>
          </el-form-item>
        </div>
        <!--  扫码登陆  -->
        <div v-if="activeName == 'third'">
          <div id="qr-code" class="qr-code">
            <!-- <div id="qrcode" ref="qrcode" style="margin-left: 100px"></div> -->
          </div>
          <div class="account-login" @click="otherLogin(1)">使用帐号登陆</div>
        </div>
        <el-form-item v-if="activeName != 'third'" style="width: 100%; margin-top: -10px;">
          <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
          <div style="float: right" v-if="register">
            <router-link class="link-type" :to="'/register'">立即注册</router-link>
          </div>
        </el-form-item>
        <el-form-item v-if="activeName != 'third'" style="width: 100%; margin-top: -10px;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width: 100%"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
          <div class="other-login">
            <span>其他登陆方式：</span>
            <svg-icon icon-class="wechat" @click="otherLogin(2)" class="login-icon hover-effect" />
            <svg-icon icon-class="alipay" @click="otherLogin(3)" class="login-icon hover-effect" />
            <svg-icon icon-class="dingding" @click="otherLogin(4)" class="login-icon hover-effect" />
          </div>
        </el-form-item>
      </el-form>
    </div>
    <!--  底部  -->
    <div class="footer">
      <span>Copyright © 2023 Chat MASTER All Rights Reserved.</span>
    </div>
  </div>
</template>
<script>
import { getCodeImg } from "@/api/login";
import logoImg from "@/assets/logo/logo.jpg";
import { getConfigKey } from "@/api/sys/common";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import JcRange from "@/components/JcRange/index.vue";
import QRCode from "qrcodejs2";
export default {
  name: "Login",
  components: { JcRange },
  data() {
    return {
      codeUrl: "",
      logo: logoImg,
      loginForm: {
        username: undefined,
        tel: undefined,
        password: undefined,
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        tel: [{ required: true, trigger: "blur", message: "请输入您的手机号" }],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },

      loading: false,
      // 验证码开关
      captchaOnOff: true,
      // 注册开关
      register: false,
      redirect: undefined,
      activeName: "first",
      isValid: false,
      // 短信验证码
      smsShow: true,
      count: "",
      timer: null
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCookie();
    this.getConfigKey("sys.account.registerUser").then(res => {
      this.register = res.data === "true";
    });
  },
  methods: {
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    getSmsCode() {
      const TIME_COUNT = 60;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.smsShow = false;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--;
          } else {
            this.smsShow = true;
            clearInterval(this.timer);
            this.timer = null;
          }
        }, 1000);
      }
    },
    resetCaptchaOnOff() {
      this.captchaOnOff = true;
      this.isValid = false;
    },
    handleClick(tab, event) {
      this.resetCaptchaOnOff();
    },
    onMpanelSuccess() {
      this.isValid = true;
      setTimeout(() => {
        this.captchaOnOff = false;
      }, 1000); // 1000 毫秒（即 1 秒）
    },
    onMpanelError() {
      this.isValid = false;
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          if (!this.isValid) {
            this.$message({
              message: "请先拖动滑块完成校验",
              type: "error"
            });
            this.loading = false;
            return;
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" }).catch(() => {});
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaOnOff) {
                this.getCode();
              }
            });
        }
      });
    },
    otherLogin(type) {
      if (type === 1) {
        this.resetCaptchaOnOff();
        this.activeName = "first";
      } else if (type === 2) {
        // alert("微信登陆")
        this.activeName = "third";
        this.$nextTick(() => {
          var obj = new WxLogin({
            self_redirect: true,
            id: "qr-code",
            appid: "xxxx",
            scope: "snsapi_login",
            redirect_uri:
              "https://jobcloud.myjiedian.com/talents-cloud-api/api/v1/wechat-app/admin-login-callback",
            state: "ceshi",
            style: "black",
            href: ""
          });
        });
      } else if (type === 3) {
        alert("支付宝登陆");
      } else if (type === 4) {
        alert("钉钉登陆");
      }
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  overflow-x: hidden;
  // background-image: url("../assets/images/login-background.jpg");
  // background-repeat: no-repeat;
  // background-position: center;
  // background-size: 100%;
  // background-color: #f0f2f5;

  .top {
    height: 30px;
  }

  .login-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    overflow: hidden;
  }

  .center {
    display: flex;
    width: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .login-form {
      z-index: 1;
      margin-top: 110px;
      width: 350px;
      background: #fff;
      width: 500px;
      padding: 20px 40px;
      border-radius: 20px;
      .title {
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        .logo {
          width: 60px;
          height: 60px;
          margin-right: 5px;
        }
        .font {
          font-weight: bold;
          font-size: 30px;
        }
      }

      .sub-title {
        text-align: center;
        margin-top: 12px;
        margin-bottom: 30px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 14px;
      }

      .login-tip {
        font-size: 13px;
        text-align: center;
        color: #bfbfbf;
      }

      .code {
        display: flex;
        justify-content: space-between;
      }

      .login-code {
        img {
          cursor: pointer;
          vertical-align: middle;
        }
      }

      .login-code-img {
        height: 35px;
      }

      .account-login {
        margin-bottom: 10px;
        display: flex;
        justify-content: center;
        font-size: 14px;
        color: #576b95;
        cursor: pointer;
        text-decoration: underline;
      }

      .qr-code {
        display: flex;
        justify-content: center;
      }

      .other-login {
        float: left;
        margin-top: 22px;
        height: 28px;
        color: rgba(0, 0, 0, 0.85);
        font-size: 14px;

        span {
          float: left;
          height: 28px;
          line-height: 28px;
        }

        .login-icon {
          width: 28px;
          height: 28px;
          color: #bfbfbf;
          margin-right: 5px;

          &.hover-effect {
            cursor: pointer;
            transition: all 0.3s;

            &:hover {
              color: #1890ff;
            }
          }
        }
      }
    }
  }
}

.footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: rgba(0, 0, 0, 0.45);
  font-family: Arial;
  font-size: 14px;
  letter-spacing: 1px;
}
</style>