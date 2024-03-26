#!/bin/bash
#
# We configure the distro, here before it gets imported into docker
# to reduce the number of UFS layers that are needed for the Docker container.
#

export http_proxy=http://172.25.30.117:6060/
export https_proxy=http://172.25.30.117:6060/

#Change OS time zone
mv -f /etc/localtime /etc/localtime.bak
ln -s /usr/share/zoneinfo/Africa/Lagos /etc/localtime

cd /opt/java-spring-boot-barebones/

java -jar -Dspring.profiles.active=docker java-spring-boot-barebones-*.jar