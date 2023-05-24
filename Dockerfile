# For Java 17, try this
FROM openjdk:17-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/springboot-3-0.0.1-SNAPSHOT.jar

EXPOSE 8090
# cd /opt/app
WORKDIR /opt/app

# cp target/springboot-3-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#docker command
# docker build -t <repository _name >:<tag> .    ==> docker build -t springboot-3.0:SNAPSHOOT-0.0.1    --> for create docker image
# docker image ls    --> for look docker image
# docker rmi <image_id>    ==> docker rmi f9e76994174d    --> for delete docr image
# docker ps -a    -> for look all container active / inactive
# docker stop <container_id>    ==> docker stop d0308bcd2b28    --> for stop docker container
# docker rm <container_id>    ==> docker rm d0308bcd2b28    --> for delete docker container
# docker run -d --name <container_name> -p <port>:<port> -p <expose_port>:<expose_port> <image_name>:<image_tag>    ==> docker run -d --name springboot -p 8080:8080 -p 8085:8085 springboot-3.0:SNAPSHOT-0.0.1    --> for running container docker
