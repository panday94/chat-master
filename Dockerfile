# 该镜像需要依赖的基础镜像
FROM openjdk:8-jre
# 指定维护者的名字
MAINTAINER Hxiang <710957166@qq.com>
# 引用pom文件中JAR_FILE参数
ARG JAR_FILE
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/${JAR_FILE} /app.jar
# 设置容器的挂在卷
VOLUME /usr/local/logs /usr/local/logs
# 声明服务运行在8080端口
EXPOSE 8080
# 编译镜像时创建app.jar文件
RUN bash -c 'touch /app.jar'
# 同步日志打印时间
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
# 指定docker容器启动时运行jar包，为了缩短 Tomcat 启动时间，添加一个系统属性指向 “/dev/./urandom” 作为 Entropy Source
ENTRYPOINT ["java","-Djava.security.egd=uploadFile:/dev/./urandom","-Duser.timezone=GMT+08","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","/app.jar"]
# 设置ADD、RUN、CMD、ENTRYPOINT等指令的工作目录
WORKDIR /
