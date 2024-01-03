# https://hub.docker.com/r/jenkins/jenkins/tags
FROM jenkins/jenkins:slim-jdk21

USER root

# https://github.com/jenkinsci/pipeline-as-yaml-plugin/pull/77
ARG MAVEN_VERSION=3.9.6
# ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# ENV MAVEN_HOME /usr/share/maven
# ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN git clone --single-branch --branch feature/refresh-december-2023 https://github.com/jonesbusy/pipeline-as-yaml-plugin \
    && cd pipeline-as-yaml-plugin \
    && mvn package

# mvn install
# mvn package -Dmaven.test.skip
# mvn package -DskipTests

# /usr/share/jenkins/ref/plugins/

# /opt/pipeline-as-yaml-plugin/target
# -rw-r--r--  1 root root   6064 Jan  2 16:14 pipeline-as-yaml-0.17-rc-SNAPSHOT.pom
# -rw-r--r--  1 root root 197163 Jan  2 16:24 pipeline-as-yaml.hpi
# -rw-r--r--  1 root root 100400 Jan  2 16:24 pipeline-as-yaml.jar

USER jenkins
