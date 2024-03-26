FROM openjdk:8u252

MAINTAINER Ife Jeremiah <jerryroboy.16@gmail.com>

EXPOSE 3000

# Install and setup
COPY install.sh /root/java-spring-boot-barebones/install.sh
COPY setup.sh /root/java-spring-boot-barebones/setup.sh


RUN chmod +x /root/java-spring-boot-barebones/setup.sh

RUN /root/java-spring-boot-barebones/setup.sh

ADD target/java-spring-boot-barebones-*.jar /opt/java-spring-boot-barebones/java-spring-boot-barebones-*.jar
WORKDIR /opt/java-spring-boot-barebones

RUN chmod +x /root/java-spring-boot-barebones/install.sh
CMD  /root/java-spring-boot-barebones/install.sh