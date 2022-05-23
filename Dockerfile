FROM zxk000000000/base:jdk17-pip3
MAINTAINER zhangxinkun <zhangxinkun94@gmail.com>

COPY ./target/*.jar /opt/docker/ph-dowload.jar
WORKDIR /opt/docker/
#CMD sh start-rss-carrier.sh
CMD java -jar ph-dowload.jar
ENTRYPOINT ["java", "-jar", "/opt/docker/ph-dowload.jar"]

EXPOSE 8080
