## v1.1.3

`2024-02-29`

> [chat-master](https://gitee.com/yoli9/chat-master)

## Enhancement
- 若涉及到上传oss和sms在后台配置，默认本地上传文件，未开通sms
- 将master-common模块移如chat-master项目中，省区再要下载master-common打包步骤

## BugFix
- 修复chat_gpt.sql文件插入openkey表app_key唯一索引问题
- 修复因缺少模型token值启动失败问题
- 修复注册时未赠送免费体验次数问题

## v1.1.2

`2024-01-20`

> [chat-master](https://gitee.com/yoli9/chat-master)

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

> [chat-master](https://gitee.com/yoli9/chat-master)

## Enhancement
- 增加智谱清言拟人大模型接口支持
- 增加文心一言Stable-Diffusion-XL画图功能（需执行doc/update.sql）

## BugFix
- 代码输出格式化问题；
- 大模型同步响应及异步响应报错时异常处理

## v1.1.0

`2023-12-26`

> [chat-master](https://gitee.com/yoli9/chat-master)

## Enhancement
- 增加智谱清言大模型

## v1.0.0

`2023-12-1`

> [chat-master](https://gitee.com/yoli9/chat-master) ChatMASTER，基于AI大模型api实现的自建后端ChatGPT服务，支出json返回及流式响应，完美呈现打印机效果。