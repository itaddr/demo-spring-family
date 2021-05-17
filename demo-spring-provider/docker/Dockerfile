FROM fabric8/java-alpine-openjdk8-jdk

USER root

ARG JAR_FILE

RUN echo "ip_resolve=4" >> /etc/yum.conf
RUN rm -rf /etc/timezone && ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ${JAR_FILE} /opt/app.jar
COPY run.sh /opt/run.sh

CMD ["sh", "-c", "cd /opt; sh run.sh app.jar /opt"]