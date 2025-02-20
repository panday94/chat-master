## v1.1.9

`2025-02-08`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 完善部署文档，同步sql文件
- 更新讯飞星火模型版本，修复模型回复问题
- 客户端中将选择模型下拉菜单设置最大高度，避免模型过多 弹出层变高
- 我的模型列表只返回启用模型，增加模型排序功能支持返回优先级高的模型
- 增加一键部署脚本

## v1.1.8

`2024-09-19`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 支持websocket响应，方便对接移动端
- 支持Langchain-chatchat 对话文档/知识库问答
- 支持Ollama加载本地模型调用
- 支持扣子（Coze）在线接口调用

## v1.1.7

`2024-05-30`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 改为多模块项目，同时支持sse/json响应，更新接口地址，命名与openai对齐
- 优化模型调用逻辑，使用策略模式替换switch case
- 去除无用表及依赖，相关模型代码中增加接口文档地址，方便开发者查看
- 增加数据统计功能，升级智谱sdk，兼容智谱sdk新版/旧版密钥。
- 站点配置信息修改，可动态配置oss、sms及支付
- 增加书生浦语大模型
- 更新万花筒栏目图标
- 账号密码登录和短信登录同时存在
- 增加统计功能
- 修改登录页面同web端一直，统一UI风格
- 简化ChatGLM密钥配置，根据不同模型参数进行配置

## BugFix

- 回复成功扣减电量
- sql增加模型信息
- 处理讯飞星火接口响应问题
- 退出时候恢复主题
- 修复万花筒一直加载问题
- 修复协议跳转问题


## v1.1.6

`2024-03-17`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 将限制访问从系统配置中转到应用配置中，方便配置
- openai兼容本地环境代理
- 更新通义千问sdk版本，更新讯飞星火3.5版本，更新ChatGLM版本到4.0
- 增加模型管理接口

## BugFix

- 解决消息回复失败恢复电量时 没有判断是否开启限制访问（限制访问时才会扣除电量）

## v1.1.5

`2024-03-14`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 增加腾讯云oss及sms方法，发送短信及上传图片功能由后台配置
- 可后台配置用户协议及站点logo，web端和移动端展示
- 用户信息及密钥信息加密返回

## BugFix

- 解决部分页面搜索问题

## v1.1.4

`2024-03-04`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 增加全量chat_master.sql文件，可直接执行该文件即可
- 在readme中增加chat-master部署语雀地址
- 增加默认万花筒信息，方便使用

## BugFix

- 解决openai输出时末尾会多出字符串null

## v1.1.3

`2024-02-29`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 若涉及到上传oss和sms在后台配置，默认本地上传文件，未开通sms
- 将master-common模块移如chat-master项目中，省区再要下载master-common打包步骤

## BugFix

- 修复chat_gpt.sql文件插入openkey表app_key唯一索引问题
- 修复因缺少模型token值启动失败问题
- 修复注册时未赠送免费体验次数问题

## v1.1.2

`2024-01-20`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 增加开启上下文聊天功能
- 增加使用扣除次数功能，可全局开启关闭是否验证使用次数
- 增加修改密码、修改个人信息功能
- 增加上传文件后台配置功能

## BugFix

- 上下文字段从token中存储到数据库
- 更换BaseConfig类至sys模块

## v1.1.1

`2023-01-06`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 增加智谱清言拟人大模型接口支持
- 增加文心一言Stable-Diffusion-XL画图功能（需执行doc/update.sql）

## BugFix

- 代码输出格式化问题；
- 大模型同步响应及异步响应报错时异常处理

## v1.1.0

`2023-12-26`

> [chat-master](https://gitee.com/panday94/chat-master)

## Enhancement

- 增加智谱清言大模型

## v1.0.0

`2023-12-1`

> [chat-master](https://gitee.com/panday94/chat-master) ChatMASTER，基于AI大模型api实现的自建后端ChatGPT服务，支出json返回及流式响应，完美呈现打印机效果。