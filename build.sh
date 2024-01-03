#!/bin/bash

set -eu -o pipefail

MAVEN_VERSION=3.9.6

docker run -u 0 -t --rm \
  -v $(pwd):/repo \
  -w /repo \
  jenkins/jenkins:slim-jdk21 /bin/bash -c " \
    mkdir -p /usr/share/maven /usr/share/maven/ref \
    && curl -fsSL -o /tmp/apache-maven.tar.gz https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
    && rm -f /tmp/apache-maven.tar.gz \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn \
    && mvn package \
  "
