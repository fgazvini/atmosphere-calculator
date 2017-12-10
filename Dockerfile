FROM debian:jessie
MAINTAINER Jean-Christophe Sirot <jcsirot@chelonix.com>

RUN apt-get update -qq && apt-get install -yqq curl

# Install java
RUN curl -k -L -s -H "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/jdk-8u151-linux-x64.tar.gz | tar xfz - -C /

ENV JAVA_HOME /jdk1.8.0_151
ENV PATH $PATH:$JAVA_HOME/bin

# Install maven
RUN curl -s http://apache.mirrors.ovh.net/ftp.apache.org/dist/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz | tar xzf - -C /

ENV MAVEN_HOME /apache-maven-3.5.2
ENV PATH $PATH:$MAVEN_HOME/bin

WORKDIR /home/atmo

ADD . /home/atmo
ADD ./settings.xml /root/.m2/settings.xml
RUN mvn package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java", "-DPROD_MODE=true", "-Xmx1G", "-jar", "target/atmo-calc.jar"]
