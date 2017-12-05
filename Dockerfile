FROM debian:jessie
MAINTAINER Jean-Christophe Sirot <jcsirot@chelonix.com>

RUN apt-get update -qq && apt-get install -yqq curl wget

# Install java
RUN wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/jdk-8u151-linux-x64.tar.gz | tar xfz - -C /

ENV JAVA_HOME /jdk1.8.151_b12
ENV PATH $PATH:$JAVA_HOME/bin

# Install maven
RUN curl -s http://apache.crihan.fr/dist/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz | tar xzf - -C /

ENV MAVEN_HOME /apache-maven-3.3.3
ENV PATH $PATH:$MAVEN_HOME/bin

WORKDIR /home/atmo

ADD . /home/atmo
RUN mvn package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java", "-DPROD_MODE=true", "-Xmx2G", "-jar", "target/atmo-calc.jar"]
