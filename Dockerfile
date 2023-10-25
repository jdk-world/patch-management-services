#FROM openjdk:19-jdk-alpine3.15
#FROM openjdk:19-jdk-alpine
#FROM openjdk:11-jre-alpine
FROM openjdk:11-jre-slim


#/home/gayushi_gupta/it-patch-mgmt/target/it-patch-mgmt-0.0.1-SNAPSHOT.jar
# Open port 8080 from docker image where springboot server is running
EXPOSE 8080
WORKDIR /

# Add item request jar to docker file
ADD target/it-patch-mgmt-0.0.1-SNAPSHOT.jar /run/it-patch-mgmt-0.0.1-SNAPSHOT.jar

RUN mkdir -p /run
RUN java --version

# Added library for using alpine image https://github.com/grpc/grpc-java/issues/8751 , https://github.com/netty/netty-tcnative/issues/111
#RUN apk update
#RUN apk upgrade

ENTRYPOINT exec java -jar /run/it-patch-mgmt-0.0.1-SNAPSHOT.jar
