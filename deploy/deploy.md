# ChatMASTER 运行部署教程

> 若需要二开建议您参考运行教程，若您是小白只想直接使用，可以查看[一键部署教程](#docker一键部署)

## 运行（适用于windows/mac/linux）

### 运行服务端（chat-master-server）
- 环境安装（选择下方的下载链接或自行百度下载）
    - Jdk1.8（[Windows](https://www.oracle.com/java/technologies/downloads/#java8) / [Linux、Mac](https://www.oracle.com/java/technologies/downloads/#java8)）
    - Mysql5.7（[Windows](https://dev.mysql.com/downloads/installer/) / [Linux、Mac](https://dev.mysql.com/downloads/mysql/5.7.html)）
    - Redis（[Windows](https://github.com/microsoftarchive/redis/releases) / [Linux、Mac](https://redis.io/download)）
    - Maven（[Windows](https://maven.apache.org/download.cgi) / [Linux、Mac](https://maven.apache.org/download.cgi)）

- 运行

1. idea导入chat-master项目，请更换spring.profiles.active为dev
2. 使用navicat或其他数据库管理工具导入../chat-master-server/sql/chat_master全量sql文件，在gpt_model配置可运行模型信息，在gpt_openkey配置模型密钥信息
3. 版本更新时候需执行doc/sql/update.sql或者查看版本更新记录获取最新sql
4. 更改application-dev中redis连接和mysql连接配置
5. 启动ChatApplication中main方法

- 主要实现
    - com.master.chat.api.config.InitBean 初始化模型
    - com.master.chat.llm.base.service.LLMService 模型接口实现

### 运行管理端（chat-master-admin）
> 如不更改配置无需运行管理端，修改密钥可在mysql数据库直接更改。

> node 要求建议14.20或14.21，建议使用nvm 安装node版本，可进行切换多版本控制，[安装nvm](https://github.com/nvm-sh/nvm) 或直接安装node，[安装node](https://nodejs.org/zh-cn/download)


```shell
# 使用nvm安装node
nvm install 14.21

# 切换不同node版本
nvm use 14 
或
nvm use 18

# 前提已安装好node，进入目录
cd chat-master-admin

# 安装依赖
npm i

# 运行管理端项目
npm run dev
```

### 运行客户端（chat-master-web）
> 因客户端使用vue3，node 需要 `^14 || ^16 || ^18 || ^19` 版本（`node >= 14` 需要安装 [fetch polyfill](https://github.com/developit/unfetch#usage-as-a-polyfill)），建议使用nvm 安装node版本，可进行切换多版本控制，[安装nvm](https://github.com/nvm-sh/nvm) 或直接安装node，[安装node](https://nodejs.org/zh-cn/download)

```shell
# 使用nvm安装node
nvm install 18

# 切换不同node版本
nvm use 14 
或
nvm use 18

# 前提已安装好node，进入目录
cd chat-master-web

# 如果你没有安装过 `pnpm`
npm install pnpm -g
```
- pnpm和npm 命令二选一， 安装pnpm时

```shell
# 安装依赖
pnpm bootstrap

# 运行客户端项目
pnpm dev
```
- 没安装pnpm时

```shell
# 安装依赖
npm i

# 运行客户端项目
npm run dev
```

## 部署
## docker一键部署

```shell
# 安装Docker
# 设置仓库
sudo yum install -y yum-utils device-mapper-persistent-data lvm2

# 设置稳定的仓库
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 安装Docker
sudo yum install docker-ce docker-ce-cli containerd.io docker-compose-plugin

# 启动Docker
sudo systemctl start docker

# 开机默认启动
systemctl enable docker

# 运行ChatMaster服务端

# 运行ChatMaster客户端

```

## 手动部署

### 打包服务端（chat-master-server）
> 使用idea中Maven插件打包 或 maven clean package 手动打包

### 打包管理端（chat-master-admin）
```shell
# 构建测试环境
cp .env.development .env.staging
# 修改测试环境配置信息 
npm run build:stage

# 构建生产环境
cp .env.development .env.production
# 修改生成环境配置信息
npm run build:prod
```

### 打包客户端（chat-master-web）
```shell
# 构建生产环境
# 注意：需切换至node 18版本 并安装pnpm
cp .env.development .env.production
# 修改生成环境配置信息
pnpm build:prod
```


## 防止爬虫抓取

**nginx**

将下面配置填入nginx配置文件中，可以参考 `docker-compose/nginx/nginx.conf` 文件中添加反爬虫的方法

```
    # 防止爬虫抓取
    if ($http_user_agent ~* "360Spider|JikeSpider|Spider|spider|bot|Bot|2345Explorer|curl|wget|webZIP|qihoobot|Baiduspider|Googlebot|Googlebot-Mobile|Googlebot-Image|Mediapartners-Google|Adsbot-Google|Feedfetcher-Google|Yahoo! Slurp|Yahoo! Slurp China|YoudaoBot|Sosospider|Sogou spider|Sogou web spider|MSNBot|ia_archiver|Tomato Bot|NSPlayer|bingbot")
    {
      return 403;
    }
```