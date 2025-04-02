# 该镜像需要依赖的基础镜像
FROM nginx:latest

# 指定维护者的名字
MAINTAINER panday94 <710957166@qq.com>

# 将nginx的配置文件包复制到docker容器的/目录下
ADD conf/nginx.conf /etc/nginx/nginx.conf

# 将Nginx前端文件复制到docker容器的/目录下
ADD html /usr/local/data/nginx/html

# 设置容器的挂在卷
VOLUME /usr/local/data/nginx/logs /var/log/nginx
VOLUME /usr/local/data/nginx/html /usr/local/data/nginx/html
VOLUME /usr/local/data/nginx/cert /usr/local/data/nginx/cert

# 暴露 80 端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]