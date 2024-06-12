# Chat Master Web

<p>
    <a href="#联系我们"><img src="https://img.shields.io/badge/%E5%85%AC%E4%BC%97%E5%8F%B7-%E5%A4%A7%E5%B8%88%E5%AD%A6Java-blue" alt="公众号"></a>
</p>

> 声明：此项目只发布于码云和GitHub，基于 MIT 协议，免费且作为开源学习使用，禁止转卖、谨防受骗。如需商用必须保留版权信息，请自觉遵守。确保合法合规使用，在运营过程中产生的一切任何后果自负，与作者无关。

项目框架基于[chatgpt-web](https://github.com/Chanzhaoyu/chatgpt-web)项目改造，页面UI借鉴ChatGLM项目。支持一键切换ChatGPT(3.5、4.0)模型、月之暗面（Kimi）、文心一言、通义千问、讯飞星火、智谱清言(ChatGLM)等主流模型进行对话，支持文心一言(Stable-Diffusion-XL作图)功能，支持模型及助手后台自定义配置。

GitHub直通车[点我传送](https://github.com/panday94/chatgpt-master)

欢迎小伙伴或有合作意向一起加入交流群[添加微信](#联系我们)或提Issues。

* 服务端项目，请移步[chat-master](https://gitee.com/panday94/chat-master)
* 管理端项目，请移步[chat-master-web](https://gitee.com/panday94/chat-master-admin)
* 移动端项目，请移步[chat-master-uniapp](https://gitee.com/panday94/chat-master-uniapp)
* 如需了解更多可访问[这里](https://www.yuque.com/the6/ct0azl/ehxcgoy0xg41l9c3?singleDoc# 《ChatMASTER部署教程》)

* 阿里云折扣场：[点我进入](https://www.aliyun.com/minisite/goods?userCode=iqguofg4)，腾讯云秒杀场：[点我进入](https://curl.qcloud.com/11y0ob0f)&nbsp;&nbsp;
* 阿里云优惠券：[点我领取](https://www.aliyun.com/daily-act/ecs/activity_selection?userCode=iqguofg4)，腾讯云优惠券：[点我领取](https://curl.qcloud.com/EUbjrCcu)&nbsp;&nbsp;

## 访问

网页端地址：https://gpt.panday94.xyz 

移动端可关注公众号使用 [扫码](#联系我们)

![cover](./docs/login.jpg)
![cover](./docs/index.png)
![cover2](./docs/chat.gif)

- [Chat Master Web](#chat-master-web)
	- [介绍](#介绍)
	- [待实现路线](#待实现路线)
	- [前置要求](#前置要求)
		- [Node](#node)
		- [PNPM](#pnpm)
	- [安装依赖](#安装依赖)
		- [前端](#前端)
	- [测试环境运行](#测试环境运行)
		- [前端网页](#前端网页)
	- [打包](#打包)
		- [防止爬虫抓取](#防止爬虫抓取)
		- [手动打包](#手动打包)
			- [前端网页](#前端网页-1)
	- [常见问题](#常见问题)
	- [参与贡献](#参与贡献)
	- [赞助](#赞助)
	- [License](#license)
## 介绍

项目基于ChatGPT、文心一言、通义千问、讯飞星火、智谱清言、月之暗面等主流模型开发，已对接模型如下：

| 名称                                          | 免费？ | 是否国内     | 地址 |
| --------------------------------------------- | ------ | ---------- | ---- |
| ChatGpt                          | 否     | 否       | https://chat.openai.com/ |
| 文心一言 | 否     | 是 | https://yiyan.baidu.com/ |
| 通义千问 | 否     | 是 | https://tongyi.aliyun.com/ |
| 讯飞星火 | 否     | 是 | https://xinghuo.xfyun.cn/ |
| 智谱清言 | 否     | 是 | https://chatglm.cn/ |
| 月之暗面 | 否     | 是 | https://kimi.moonshot.cn/ |
| 书生浦语 | 否     | 是 | https://internlm-chat.intern-ai.org.cn/ |

提示：
1. ChatGPT 通过`Cloudflare`访问openai接口
2. ChatGPT及国内模型密钥由后台系统配置
3. 后期可接入使用自己token或者key使用

## 已实现路线
[✓] 多模型、多版本切换

[✓] 多会话储存和上下文逻辑

[✓] 对代码等消息类型的格式化美化处理

[✓] 个人信息修改及分享

[✓] 会员功能，兑换码、分享功能

[✓] 界面多语言、界面主题

[✓] 禁止代码调试

[✗] 文档问答、知识库

## 前置要求

### Node

`node` 需要 `^14 || ^16 || ^18 || ^19` 版本（`node >= 14` 需要安装 [fetch polyfill](https://github.com/developit/unfetch#usage-as-a-polyfill)），使用 [nvm](https://github.com/nvm-sh/nvm) 可管理本地多个 `node` 版本 

> node14版本建议14.20版本以上，若使用admin管理后台，可以选择安装node14版本，即运行两端项目时候无需切换node版本。

```shell
node -v
```

### PNPM（16^版本安装）

如果你没有安装过 `pnpm`
```shell
npm install pnpm -g
```
## 安装依赖

### 前端
根目录下运行以下命令
```shell
# 安装pnpm时
pnpm bootstrap
# 命令二选一
# 没安装pnpm时，即node选择14版本
npm i
```

## 测试环境运行

### 前端网页
根目录下运行以下命令
```shell
# 安装pnpm时
pnpm dev
# 命令二选一
# 没安装pnpm时，即node选择14版本
npm run dev
```

#### 防止爬虫抓取

**nginx**

将下面配置填入nginx配置文件中，可以参考 `docker-compose/nginx/nginx.conf` 文件中添加反爬虫的方法

```
    # 防止爬虫抓取
    if ($http_user_agent ~* "360Spider|JikeSpider|Spider|spider|bot|Bot|2345Explorer|curl|wget|webZIP|qihoobot|Baiduspider|Googlebot|Googlebot-Mobile|Googlebot-Image|Mediapartners-Google|Adsbot-Google|Feedfetcher-Google|Yahoo! Slurp|Yahoo! Slurp China|YoudaoBot|Sosospider|Sogou spider|Sogou web spider|MSNBot|ia_archiver|Tomato Bot|NSPlayer|bingbot")
    {
      return 403;
    }
```

### 手动打包

#### 前端网页

1、修改根目录下 `.env` 文件中的 `VITE_GLOB_API_URL` 为你的实际后端接口地址

2、根目录下运行以下命令，然后将 `dist` 文件夹内的文件复制到你网站服务的根目录下

[参考信息](https://cn.vitejs.dev/guide/static-deploy.html#building-the-app)

```shell
pnpm build
```

## 常见问题
Q: 为什么 `Git` 提交总是报错？

A: 因为有提交信息验证，请遵循 [Commit 指南](./CONTRIBUTING.md)

Q: 如果只使用前端页面，在哪里改请求接口？

A: 根目录下 `.env` 文件中的 `VITE_GLOB_API_URL` 字段。

Q: 文件保存时全部爆红?

A: `vscode` 请安装项目推荐插件，或手动安装 `Eslint` 插件。

Q: 前端没有打字机效果？

A: 一种可能原因是经过 Nginx 反向代理，开启了 buffer，则 Nginx 会尝试从后端缓冲一定大小的数据再发送给浏览器。请尝试在反代参数后添加 `proxy_buffering off;`，然后重载 Nginx。其他 web server 配置同理。

## 参与贡献

贡献之前请先阅读 [贡献指南](./CONTRIBUTING.md) [版本记录](./CHANGELOG.md)

个人的力量始终有限，任何形式的贡献都是欢迎的，包括但不限于贡献代码，优化文档，提交 issue 和 PR 等。
感谢所有做过贡献的人!

## 赞助

如果你觉得这个项目对你有帮助，并且情况允许的话，可以给我一点点支持，总之非常感谢支持～

接定制开发，欢迎老板下单！

<div style="display: flex; gap: 20px;">
	<div style="text-align: center">
		<img style="max-width: 100%" src="./docs/wepay.jpg" alt="微信" />
		<p>WeChat Pay</p>
	</div>
</div>

## 联系我们
<div style="display: flex;">
    <img style="width: 100%" src="./docs/wechat.png" alt="微信" />
</div>

## 扫码进群
<div style="display: flex; gap: 20px;">
    <img style="max-width: 100%" src="https://gpt.panday94.xyz/files/wx_group.jpg" alt="微信群" />
</div>

## License
MIT © [Master](./license)
