# Chat MASTER

<p>
    <a href="#联系我们"><img src="https://img.shields.io/badge/%E5%85%AC%E4%BC%97%E5%8F%B7-%E5%A4%A7%E5%B8%88%E5%AD%A6Java-blue" alt="公众号"></a>
</p>

![](https://img.shields.io/badge/SpringBoot-2.3.7-brightgreen.svg)

# 项目简介
ChatMASTER，基于AI大模型api实现的自建后端Chat服务，支出同步响应及流式响应，完美呈现打印机效果。支持ChatGPT模型，同时也支持国内文心一言(支持Stable-Diffusion-XL作图)、通义千问、讯飞星火、智谱清言(ChatGLM)等主流模型，后续模型持续对接中。
项目包含java后台、网页端、移动端及管理后台配置，欢迎小伙伴一起加入交流[添加微信](#联系我们)。参考下面具体介绍：

* 支持文心一言Stable-Diffusion-XL作图功能
* 支持使用assistant模版，按指定prompt输出
* 支持切换模型对话聊天，保存对话记录及根据上下文输出
* 管理端采用Vue2、Element UI，Chat网页端使用Vue3、TypeScript、NaiveUI进行开发
* 后端采用Spring Boot、Spring Security + JWT、Mybatis-Plus、Lombok、 Mysql & Redis，代码通俗易懂，上手即用
* 完善的权限控制，权限认证使用Jwt，支持多终端认证系统
* 管理端前端项目，请移步[chat-master-admin](https://gitee.com/panday94/chat-master-admin)
* 网页端项目，请移步[chat-master-web](https://gitee.com/panday94/chat-master-web)
* 移动端项目，请移步[chat-master-uniapp](https://gitee.com/panday94/chat-master-uniapp)

## 演示

演示地址：https://gpt.panday94.xyz
后台密码：admin 123456

![cover](./doc/file/login.jpg)
![cover2](./doc/file/chat.gif)

## 已实现功能
1. 多模型对话，支持ChatGPT(3.5、4.0)、文心一言(支持Stable-Diffusion-XL作图)、通义千问、讯飞星火、智谱清言(ChatGLM)
2. 支持后台配置及使用assistant模版，按指定prompt输出
3. 存储历史对话及聊天内容，可开启/关闭根据上下文输出
4. 支持按使用次数或者开通会员使用，也可全局判断不校验使用次数及会员。
5. 支持分享功能（基础上开发）
6. 支持个人信息修改

## 待实现功能
1. vip及svip开通功能
2. 分享赠送次数功能
3. 用户上传自己密钥使用

## 模型功能对比

> 版本记录请查看这里[版本记录](./CHANGELOG.md)

| 模型       | 是否支持System     | 天气查询       | 绘画                    |
|-----------|----------------|------------|-----------------------|
| ChatGPT   | 支持             | 不支持        | 支持                    |
| 文心一言    | 不支持(传递会报错)  | 可以回复(不准)   | 使用Stable-Diffusion-XL |
| 通义千问    | 支持             | 支持(效果没讯飞好) | 未接入                   |
| 讯飞星火    | 不支持(传递不会报错) | 支持(准)      | 不支持                   |
| 智谱清言    | 不支持(传递会报错)   | 不支持        | 支持（API待接入目前有点贵）       |


## 内置功能
1. 工作台：集成多个应用和功能的系统页面，该页面主要为用户提供快速访问、信息聚会、个性化等功能。
2. 数据中心：用于管理和分析系统数据的功能，向用户提供直观和易懂的信息，方便使用者快速了解系统数据。
3. 聊天管理：可以后台查看所有模型回复内容。
4. 订单管理：可以接入充值赠送模型使用次数功能。
5. 会员中心：查看所有用户信息，及开通模型次数功能。
6. 助手中心：配置Assistant分类及prompt信息。
7. 配置中心：配置系统可使用模型及移动端信息配置。
8. 系统管理：对系统中基础业务进行管理维护。
9. 系统监控：针对系统运行状态进行查看及定时任务配置管理

## 环境搭建/运行&提示
> ChatGPT需要在后台配置管理中进行站点配置或添加微信交流

``` 
1、下载[master-common](https://gitee.com/panday94/master-common)打包到本地maven仓库
2、idea导入chat-master项目，请更换spring.profiles.active为dev
3、执行doc文件夹下面sys.sql、sys_date.sql、chat-gpt.sql文件，在gpt_model配置可运行模型信息，在gpt_openkey配置模型密钥信息
4、版本更新时候需执行doc/update.sql
5、更改application-dev中redis连接和mysql连接配置
6、启动ChatApplication中main方法
```
- com.master.chat.api.base.config.InitBean 初始化模型
- com.master.chat.gpt.service.IGptService 接口调用
- com.master.chat.gpt.service.SseService sse调用

### 开发环境

| 工具    | 版本号   | 下载                                                                                   |
|-------|-------|--------------------------------------------------------------------------------------|
| JDK   | 1.8   | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql | 5.7   | https://www.mysql.com/                                                               |
| Maven | 3.6.3 | https://maven.apache.org/                                                            |
| nginx | 1.10  | http://nginx.org/en/download.html                            ｜                       |

### 开发技术

| 技术                    | 说明                    | 官网                                                 |
| ---------------------  | ------------------------| ----------------------------------------------------|
| Spring Boot            | 容器+MVC框架            | https://spring.io/projects/spring-boot               |
| Spring Security Oauth2 | 认证和授权框架           | https://spring.io/projects/spring-security-oauth     |
| Lombok                 | 简化Java开发            | https://github.com/JourWon/test-lombok               |
| Netty                  | 网络通信框架             | https://github.com/JourWon/test-lombok               |
| JWT                    | JWT登录支持             | https://github.com/jwtk/jjwt                         |
| MyBatis-plus           | 代码生成、物理分页        | https://baomidou.com/                                |
| dynamic-datasource     | 多数据源                 | https://www.kancloud.cn/tracy5546/dynamic-datasource/2264611|
| Redis                  | 分布式缓存               | https://redis.io/                                    |
| Druid                  | 数据库连接池             | https://github.com/alibaba/druid                     |
| OSS                    | 对象存储                 | https://github.com/aliyun/aliyun-oss-java-sdk        |
| quartz                 | 定时任务                 | https://github.com/quartz-scheduler/quartz           |

### 框架特点

1. 使用@RepeatSubmit(interval = 1000)注解对接口进行重复提交限制，interval默认值为5s内，可以自定义时间范围。
2. 使用@RateLimiter注解对接口进行限流。
3. 使用@DataScope(deptAlias = "t1", userAlias = "t2")进行数据过滤。
4. 使用@PreAuthorize("hasAuthority('system:config:remove')")注解可以对接口进行权限校验。
5. 使用@Log(value = "刷新系统配置缓存", type = SysLogTypeConstant.CONFIG, businessType = BusinessTypeEnum.CLEAN)
   注解进行系统日志存储。
6. 使用@DS("master")切换数据源，优先方法高于类
7. 引入阿里巴巴easy-excel框架，轻松编写导出、导入接口。
8. 使用Lombok简化java代码，省略getter、setter方法。（@Accessors(chain = true)支持链式操作）
9. 完善的自动生成代码功能，增加DTO、Command、VO类。
10. 独立的定时任务列表管理功能。
11. 支持多端登录或只能当前账号登录功能，需要后台配置。
12. 使用阿里云、腾讯云对象存储及短信功能，支持本地存储。

## 参与贡献

贡献之前请先阅读 [贡献指南](./CONTRIBUTING.md)

感谢所有做过贡献的人!

## 联系我们

<div style="display: flex; gap: 20px;">
	<div>
		<img style="max-width: 100%" src="./doc/file/wxcode.jpg" alt="微信" />
		<p>添加微信，加入交流群</p>
	</div>
</div>

## 许可证

[Apache License 2.0](./LICENSE)

Copyright (c) 2022 Master Computer Corporation Limited All rights reserved