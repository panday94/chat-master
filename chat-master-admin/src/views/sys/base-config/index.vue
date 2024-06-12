<template>
    <div class="app-container">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基础信息" name="baseInfo"></el-tab-pane>
            <el-tab-pane label="应用信息" name="appInfo"></el-tab-pane>
            <el-tab-pane label="微信信息" name="wxInfo"></el-tab-pane>
            <el-tab-pane label="Oss/Sms信息" name="extraInfo"></el-tab-pane>
        </el-tabs>
        <el-form v-if="activeName == 'baseInfo'" ref="form" :model="baseInfo" label-width="130px">
            <el-form-item label="站点名称">
                <el-input style="width: 200px" placeholder="请填写站点名称" v-model="baseInfo.siteTitle"></el-input>
            </el-form-item>
            <el-form-item label="站点logo">
                <imageUpload v-model="baseInfo.siteLogo" :limit="1" />
            </el-form-item>
            <el-form-item label="站点关键词">
                <el-tag :key="tag" v-for="tag in dynamicTags" closable :disable-transitions="false"
                    @close="handleClose(tag)">
                    {{ tag }}
                </el-tag>
                <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small"
                    @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm">
                </el-input>
                <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新标签</el-button>
            </el-form-item> 
            <el-form-item label="内容安全审查接口">
                <el-switch v-model="baseInfo.contentSecurity" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
                <div>如开启，需配置小程序appid和secret（使用小程序的接口，多端通用）</div>
            </el-form-item>
            <el-form-item label="代理方案">
                <el-select v-model="baseInfo.proxyType" placeholder="请选择套代理方案">
                    <el-option v-for="item in proxyTypes" :key="item.value" :label="item.label"
                        :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="反代服务器" v-if="baseInfo.proxyType == 2">
                <el-input style="width: 260px" placeholder="请填写反代服务器地址" v-model="baseInfo.proxyServer">
                </el-input>
            </el-form-item>
            <el-form-item label="代理地址" v-if="baseInfo.proxyType == 3">
                <el-input style="width: 260px" placeholder="请填写代理地址，如'127.0.0.1:8088'" v-model="baseInfo.proxyAddress">
                </el-input>
            </el-form-item>
            <el-form-item label="绑定域名">
                <el-input style="width: 260px" placeholder="请填写访问域名" v-model="baseInfo.domain">
                </el-input>
            </el-form-item>
            <el-form-item label="站点版权">
                <el-input style="width: 500px" placeholder="请填写站点版权" v-model="baseInfo.copyright">
                </el-input>
            </el-form-item>
            <el-form-item label="站点描述">
                <el-input type="textarea" :rows="4" style="width: 500px" placeholder="请填写站点描述" v-model="baseInfo.descrip">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermi="['sys:base:config:save']" type="primary" @click="onSubmit('baseInfo', baseInfo)">保存</el-button>
            </el-form-item>
        </el-form>
        <el-form v-if="activeName == 'appInfo'" ref="form" :model="appInfo" label-width="140px">
            <el-form-item label="是否无限制访问GPT">
                <el-switch v-model="appInfo.isGptLimit" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item label="是否开启兑换码">
                <el-switch v-model="appInfo.isRedemption" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item label="是否开启注册短信">
                <el-switch v-model="appInfo.isSms" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item label="是否分享获取电量">
                <el-switch v-model="appInfo.isShare" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item label="分享获取电量">
                <el-input style="width: 220px" placeholder="请填写分享获取电量" v-model="appInfo.shareNum">
                </el-input>
            </el-form-item>
            <el-form-item label="注册赠送电量">
                <el-input style="width: 220px" placeholder="请填写注册赠送电量" v-model="appInfo.freeNum"></el-input>
            </el-form-item>
            <el-form-item label="H5地址">
                <el-input style="width: 400px" placeholder="请填写H5地址" v-model="appInfo.h5Url"></el-input>
                <div>如托管到服务器，此处填写“域名/h5/”</div>
            </el-form-item>
            <el-form-item label="首页公告">
                <el-input style="width: 400px" :rows="3" type="textarea" placeholder="请填写首页公告"
                    v-model="appInfo.homeNotice">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermi="['sys:base:config:save']" type="primary" @click="onSubmit('appInfo', appInfo)">保存</el-button>
            </el-form-item>
        </el-form>
        <el-form v-if="activeName == 'wxInfo'" ref="form" :model="wxInfo" label-width="130px">
            <el-form-item label="是否使用小程序">
                <el-switch v-model="wxInfo.isWxapp" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxapp == 1" label="小程序appId">
                <el-input style="width: 200px" placeholder="请填写小程序appId" v-model="wxInfo.wxAppId"></el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxapp == 1" label="小程序secret">
                <el-input style="width: 300px" placeholder="请填写小程序secret" v-model="wxInfo.wxAppSecret"></el-input>
            </el-form-item>
            <el-form-item label="是否使用公众号">
                <el-switch v-model="wxInfo.isMp" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item v-if="wxInfo.isMp == 1" label="公众号appId">
                <el-input style="width: 200px" placeholder="请填写公众号appId" v-model="wxInfo.mpAppId"></el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isMp == 1" label="公众号secret">
                <el-input style="width: 300px" placeholder="请填写公众号secret" v-model="wxInfo.mpSecret"></el-input>
            </el-form-item>
            <el-form-item label="是否开启支付">
                <el-switch v-model="wxInfo.isWxPay" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item label="是否开启服务商">
                <el-switch v-model="wxInfo.isWxSpPay" active-text="开启" :active-value="1" :inactive-value="0"
                    inactive-text="关闭">
                </el-switch>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxSpPay ==1 && wxInfo.isWxPay == 1" label="服务商appId">
                <el-input style="width: 200px" placeholder="请填写微信服务商appId" v-model="wxInfo.spAppId">
                </el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxSpPay ==1 && wxInfo.isWxPay == 1" label="服务商商户号">
                <el-input style="width: 200px" placeholder="请填写微信服务商商户号" v-model="wxInfo.spMchId">
                </el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxPay == 1" label="支付商户号">
                <el-input style="width: 200px" placeholder="请填写微信支付商户号" v-model="wxInfo.mchId">
                </el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxPay == 1" label="商户密钥V3">
                <el-input style="width: 300px" placeholder="请填写微信支付V3密钥" v-model="wxInfo.apiV3Key">
                </el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxPay == 1" label="应用私钥">
                <el-input style="width: 300px" placeholder="请填写应用私钥（对应apiclient_key.pem）" v-model="wxInfo.privateKey">
                </el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxPay == 1" label="证书序列号">
                <el-input style="width: 300px" placeholder="请填写商户证书序列号" v-model="wxInfo.mchSerialNo">
                </el-input>
            </el-form-item>
            <el-form-item v-if="wxInfo.isWxPay == 1" label="支付证书">
                <el-input style="width: 300px" placeholder="请填写服务器支付证书地址" v-model="wxInfo.certPath">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermi="['sys:base:config:save']" type="primary" @click="onSubmit('wxInfo', wxInfo)">保存</el-button>
            </el-form-item>
        </el-form>
        <el-form v-if="activeName == 'extraInfo'" ref="form" :model="extraInfo" label-width="130px">
            <el-form-item label="上传类型">
                <el-select v-model="extraInfo.ossType" placeholder="请选择上传类型">
                    <el-option v-for="item in dict.type.sys_oss_type" :key="item.value" :label="item.label"
                        :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="上传大小限制(MB)">
                <el-input style="width: 200px" placeholder="请填写上传大小限制" v-model="extraInfo.uploadSize"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.ossType != 1" label="仓库区域">
                <el-input style="width: 250px" placeholder="请输入ENDPOINT如：cn-shanghai" v-model="extraInfo.endpoint"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.ossType != 1" label="仓库名称">
                <el-input style="width: 250px" placeholder="请输入Oss仓库BUCKET_NAME" v-model="extraInfo.bucketName"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.ossType != 1" label="密钥ID">
                <el-input style="width: 200px" placeholder="请填写密钥ID" v-model="extraInfo.ossKeyId"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.ossType != 1" label="密钥Secret">
                <el-input style="width: 200px" placeholder="请填写密钥Secret" v-model="extraInfo.ossKeySecret"></el-input>
            </el-form-item>
            <el-form-item label="短信类型">
                <el-select v-model="extraInfo.smsType" placeholder="请选择短信类型">
                    <el-option v-for="item in dict.type.sys_sms_type" :key="item.value" :label="item.label"
                        :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item v-if="extraInfo.smsType != 0" label="短信区域">
                <el-input style="width: 250px" placeholder="请输入REGION如：cn-shanghai" v-model="extraInfo.smsRegionId"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.smsType == 2" label="应用ID">
                <el-input style="width: 250px" placeholder="请输入AppId" v-model="extraInfo.smsAppId"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.smsType != 0" label="密钥ID">
                <el-input style="width: 200px" placeholder="请填写密钥ID" v-model="extraInfo.smsKeyId"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.smsType != 0" label="密钥Secret">
                <el-input style="width: 200px" placeholder="请填写密钥Secret" v-model="extraInfo.smsKeySecret"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.smsType != 0" label="短信签名">
                <el-input style="width: 200px" placeholder="请填写短信签名" v-model="extraInfo.smsSign"></el-input>
            </el-form-item>
            <el-form-item v-if="extraInfo.smsType != 0" label="注册模版">
                <el-input style="width: 200px" placeholder="请填写密钥注册模版" v-model="extraInfo.registerTemplate"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermi="['sys:base:config:save']" type="primary" @click="onSubmit('extraInfo', extraInfo)">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
  
<script>
import {
    getBaseConfig,
    addBaseConfig,
} from "@/api/sys/baseConfig";

export default {
    name: "Site",
    dicts: ['sys_oss_type', 'sys_sms_type'],
    data() {
        return {
            activeName: "baseInfo",
            dynamicTags: [],
            inputVisible: false,
            inputValue: '',
            proxyTypes: [
                { label: '无需代理', value: 1 },
                { label: '反向代理', value: 2 },
                { label: '本地代理', value: 3 },
            ],
            form: {},
            baseInfo: {
            },
            appInfo: {
                isGptLimit: 0
            },
            wxInfo: {
            },
            extraInfo: {
                ossType: 1
            },
        };
    },
    created() {
        this.getBaseConfig(this.activeName ? this.activeName : 'baseInfo');
    },
    methods: {
        handleClose(tag) {
            this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },
        showInput() {
            this.inputVisible = true;
            this.$nextTick(_ => {
                this.$refs.saveTagInput.$refs.input.focus();
            });
        },
        handleInputConfirm() {
            let inputValue = this.inputValue;
            if (inputValue) {
                this.dynamicTags.push(inputValue);
            }
            this.baseInfo.keywords = this.dynamicTags;
            this.inputVisible = false;
            this.inputValue = '';
        },
        handleClick(tab) {
            this.getBaseConfig(tab.name);
        },
        getBaseConfig(name) {
            getBaseConfig(name).then((res) => {
                if (!res.data) {
                    return;
                }
                if (res.data.keywords) {
                    this.dynamicTags = res.data.keywords;
                }
                if (name == 'baseInfo') {
                    this.baseInfo = res.data;
                } else if (name == 'appInfo') {
                    this.appInfo = res.data
                } else if (name == 'wxInfo') {
                    this.wxInfo = res.data
                } else if (name == 'extraInfo') {
                    this.extraInfo = res.data
                }
            });
        },
        onSubmit(name, data) {
            this.form.name = name;
            this.form.data = JSON.stringify(data);
            addBaseConfig(this.form).then((res) => {
                this.$modal.msgSuccess("编辑成功");
                this.getBaseConfig(name);
            });
        },
    },
};
</script>
<style scoped>
.el-tag+.el-tag {
    margin-left: 10px;
}

.button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
}

.input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
}
</style>