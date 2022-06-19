FROM zxk000000000/base:jdk17-pip3
MAINTAINER zhangxinkun <zhangxinkun94@gmail.com>

COPY ./target/*.jar /opt/docker/ph-dowload.jar
COPY ./PornHub-downloader-python /opt/docker/PornHub-downloader-python
WORKDIR /opt/docker/

ENTRYPOINT java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:15005 -Dhttps.proxyHost=192.168.31.11 -Dhttps.proxyPort=17890 -Dhttp.proxyHost=192.168.31.11 -Dhttp.proxyPort=17890 -jar /opt/docker/ph-dowload.jar

EXPOSE 15005
EXPOSE 18081
