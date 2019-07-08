FROM openjdk:8-jre-alpine
MAINTAINER ouzhx <javastar920905@163.com>

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/springbootupload/myservice.jar
# ENTRYPOINT 指令不要换行
ENTRYPOINT ["/usr/bin/java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5117", "/usr/share/springbootupload/myservice.jar","-Dspring.profiles.active=default"]