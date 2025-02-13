# ChatMASTER 运行部署教程

> 若需要二开建议您参考运行教程，若您是小白只想直接使用，可以查看[一键部署教程](#部署)
- 后台管理系统默认密码为admin 123456 
- 客户端账号密码自行注册，登录即注册


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

1. docker[部署](#docker部署)

2. Docker Compose [一键部署](#docker-compose-一键部署推荐)（推荐）

## docker部署

> 数据库名称chat_master，账号chat_master 密码chat_master

### 安装Docker(如已安装则跳过，可能会出现镜像源无法拉取问题自行百度)

```shell
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

```

### 安装Docker For Redis 或自行安装

> 需要将./redis/redis.conf 配置文件 上传到自己服务器/usr/local/data/redis/conf/目录下。 该目录在创建容器的时候可自行修改，ChatMaster服务默认未设置redis密码，如需需要设置密码，请自行修改redis.conf文件中的requirepass配置和重新打包。

```shell
# 拉取Redis镜像
docker pull redis:latest

# 创建容器
docker run --network="host" -d --name redis --restart always -p 6379:6379 -v /usr/local/data/redis/conf:/etc/redis -v /usr/local/data/redis/data:/data redis redis-server /etc/redis/redis.conf  --appendonly yes
```

### 安装Docker For Mysql5.7 或自行安装

> 1、需要将./mysql/my.cnf 配置文件 上传到自己服务器/usr/local/data/mysql/conf/目录下。 该目录在创建容器的时候可自行修改，ChatMaster数据库默认账号密码都为`chat_master`，请勿擅自修改，否则会导致服务连接不上数据库。

> 2、将./mysql/init.d/ 下的sql文件上传到自己服务器/usr/local/data/mysql/init.d/目录下。 

```shell
# 拉取Mysql镜像
docker pull mysql:5.7

# 创建容器
docker run --network="host" -p 3306:3306 --name mysql -v /usr/local/data/mysql/conf:/etc/mysql/conf.d -v /usr/local/data/mysql/logs:/logs -v /usr/local/data/mysql/data:/var/lib/mysql -v /usr/local/data/mysql/init.d:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=12345678 -e MYSQL_DATABASE=chat_master -e MYSQL_USER=chat_master  -e MYSQL_PASSWORD=chat_master -d mysql:5.7

# 如需mysql远程连接，需要进入Mysql容器内部
[root@bogon  /]# docker exec -it 容器id /bin/bash 

root@74442a33569c:/# mysql -uroot -p
# 允许远程连接
mysql> grant all privileges on *.* to 'root'@'%';
# 刷新权限
flush privileges;
```

### 运行ChatMaster服务端
> 服务端版本号：1.1.9，

```shell
# 拉取Chat-Master服务端镜像
docker pull registry.cn-beijing.aliyuncs.com/chat-master/master-server:[服务端版本号]
# 运行Chat-Master服务端容器
# SPRING_PROFILES_ACTIVE变量可切换服务端的配置文件启动，如dev、test、prod
docker run --network="host" --name chat-master-server --restart always -p 8088:8088 -d registry.cn-beijing.aliyuncs.com/chat-master/master-server:1.1.9 --SPRING_PROFILES_ACTIVE=dev
```

### 运行ChatMaster客户端
> 客户端版本号：1.1.9

```shell
# 拉取Chat-Master服务端镜像
docker pull registry.cn-beijing.aliyuncs.com/chat-master/master-nginx:[客户端版本号]

# 简易版启动
docker run --network="host" --name chat-master-nginx --restart always -p 80:80 -p 443:443 -d registry.cn-beijing.aliyuncs.com/chat-master/master-nginx:1.1.9

# 如需自定义nginx配置，请自行修改./nginx/nginx.conf文件，并上传到自己服务器/usr/local/data/nginx/conf/目录下。如域名配置，自行修改nginx.conf文件中的server_name配置。
# 如需自定义证书，请自行修改./nginx/cert/目录文件，并上传到自己服务器/usr/local/data/nginx/cert/目录下。
# 如需更换前端打包文件，请将打包好的web端及admin内容分别上传到自己服务器'/usr/local/data/nginx/html/chat-master/'及'/usr/local/data/nginx/html/chat-master/admin/'目录下。
docker run --network="host" --name chat-master-nginx -p 80:80 -p 443:443 -v /usr/local/data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /usr/local/data/nginx/cert:/etc/nginx/cert -v /usr/local/data/nginx/html:/usr/local/html -d registry.cn-beijing.aliyuncs.com/chat-master/master-nginx:1.1.9
```
> 如四个容器全都运行成功如下所示，试着打开以下地址确认是否部署成功，如已成功访问，即代表部署成功。

```shell
$ docker ps
CONTAINER ID   IMAGE                                                              COMMAND                   CREATED          STATUS          PORTS     NAMES
a13d54bbff81   registry.cn-beijing.aliyuncs.com/chat-master/master-nginx:1.1.9    "/docker-entrypoint.…"   13 minutes ago   Up 13 minutes             chat-master-nginx
e44ba185b8c0   registry.cn-beijing.aliyuncs.com/chat-master/master-server:1.1.9   "java -Djava.securit…"   6 hours ago      Up 6 hours                chat-master-server
a684fe52d5c6   mysql:5.7                                                          "docker-entrypoint.s…"   20 hours ago     Up 20 hours               mysql
1ed1557351cd   redis                                                              "docker-entrypoint.s…"   21 hours ago     Up 21 hours               redis
```

- 打开ChatMaster客户端，访问地址：http://你的ip

- 打开ChatMaster管理端，访问地址：http://你的ip/admin

## Docker Compose 一键部署（推荐）

> 待实现

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