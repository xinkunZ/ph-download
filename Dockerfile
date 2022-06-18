FROM zxk000000000/base:jdk17-pip3
MAINTAINER zhangxinkun <zhangxinkun94@gmail.com>

COPY ./target/*.jar /opt/docker/ph-dowload.jar
WORKDIR /opt/docker/
#CMD sh start-rss-carrier.sh
#CMD java -jar "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:15005" /opt/docker/ph-dowload.jar
CMD [ "java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:15005","-Dhttps.proxyHost=127.0.0.1","-Dhttps.proxyPort=17890",
"-Dhttp.proxyHost=127.0.0.1", "-Dhttp.proxyPort=17890",
 "-jar", "/opt/docker/ph-dowload.jar" ]

EXPOSE 15005
EXPOSE 18081
