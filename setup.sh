#!/bin/bash
#
# We configure the distro, here before it gets imported into docker
# to reduce the number of UFS layers that are needed for the Docker container.
#

export http_proxy=http://172.25.30.117:6060/
export https_proxy=http://172.25.30.117:6060/


set -e
mkdir -p /opt/java-spring-boot-barebones

cd /opt/java-spring-boot-barebones

mkdir -p /log
ln -s /log logs