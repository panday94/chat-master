<template>
  <div class="login">
    <div class="top"></div>
    <div class="center">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <div class="title"><span class="font">Chat MASTER</span></div>
        <div class="sub-title">ChatMASTER致于与为用户及伙伴提供智能化AI服务</div>
        <!--  注册  -->
        <el-form-item prop="name">
          <el-input v-model="loginForm.name" type="text" auto-complete="off" placeholder="请输入您的姓名">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="tel">
          <el-input v-model="loginForm.tel" type="text" auto-complete="off" placeholder="请输入您的手机号">
            <svg-icon slot="prefix" icon-class="mobile" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入您的账号">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="请输入您的密码"
            @keyup.enter.native="handleRegister">
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="loginForm.confirmPassword" type="password" auto-complete="off" placeholder="请再次输入您的密码"
            @keyup.enter.native="handleRegister">
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item v-if="captchaOnOff">
          <JcRange status="status" :successFun="onMpanelSuccess" :errorFun="onMpanelError"></JcRange>
        </el-form-item>
        <el-form-item style="width: 100%;">
          <el-button :loading="loading" size="medium" type="primary" style="width: 100%"
            @click.native.prevent="handleRegister">
            <span v-if="!loading">注 册</span>
            <span v-else>注 册 中...</span>
          </el-button>
          <div style="float: right">
            <router-link class="link-type" :to="'/login'">使用已有账号登录</router-link>
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
import logoImg from '@/assets/logo/logo.jpg'
import { register } from "@/api/login";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import JcRange from "@/components/JcRange/index.vue";
export default {
  name: "Login",
  components: { JcRange },
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.loginForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      logo: logoImg,
      loginForm: {
        name: undefined,
        tel: undefined,
        username: undefined,
        password: undefined,
        confirmPassword: undefined,
        code: "",
        uuid: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
        ],
        name: [
          { required: true, trigger: "blur", message: "请输入您的姓名" },
        ],
        tel: [
          { required: true, trigger: "blur", message: "请输入您的手机号" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
        ],
        confirmPassword: [
          { required: true, trigger: "blur", message: "请再次输入您的密码" },
          { required: true, validator: equalToPassword, trigger: "blur" },
        ]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: true,
      isValid: false,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  methods: {
    onMpanelSuccess() {
      this.isValid = true;
      setTimeout(() => {
        this.captchaOnOff = false;
      }, 1000); // 1000 毫秒（即 1 秒）
    },
    onMpanelError() {
      this.isValid = false;
    },
    handleRegister() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          if (!this.isValid) {
            this.$message({
              message: '请先拖动滑块完成校验',
              type: 'error'
            });
            return;
          }
          if (this.loginForm.password == '123456') {
            this.$modal.msgWarning("密码过于简单，请重新输入");
            return;
          }
          this.loading = true;
          register(this.loginForm)
            .then((res) => {
              const username = this.loginForm.username;
              this.$alert(
                "<font color='red'>恭喜你，您的账号 " +
                username +
                " 注册成功！</font>",
                "系统提示",
                {
                  dangerouslyUseHTMLString: true,
                  type: "success",
                }
              )
                .then(() => {
                  this.$router.push("/login");
                })
                .catch(() => {
                  this.captchaOnOff = true;
                  this.isValid = false;
                  this.loading = false;
                });
            })
            .catch(() => {
              this.captchaOnOff = true;
              this.isValid = false;
              this.loading = false;
            });
        }
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  // background-repeat: no-repeat;
  background-position: center;
  background-size: 100%;
  background-color: #f0f2f5;

  .top {
    height: 30px;
  }

  .center {
    margin: 60px 0;
    display: flex;
    width: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .login-form {
      margin-top: 20px;
      width: 350px;

      .title {
        font-weight: bold;
        font-size: 30px;
        height: 40px;
        line-height: 40px;

        .logo {
          float: left;
          width: 40px;
          height: 40px;
          margin-left: 75px;
          margin-right: 10px;
        }

        .font {
          display: inline-block;
        }
      }

      .sub-title {
        text-align: center;
        margin-top: 12px;
        margin-bottom: 30px;
        color: rgba(0, 0, 0, .45);
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
        color: #576B95;
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
        color: rgba(0, 0, 0, .85);
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
  color: rgba(0, 0, 0, .45);
  font-family: Arial;
  font-size: 14px;
  letter-spacing: 1px;
}
</style>