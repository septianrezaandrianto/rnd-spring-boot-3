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
# docker run -d --name <container_name> -p <expose_port>:<properites_port> <image_name>:<image_port>    ==> docker run -d --name springboot-3.0 -p 8099:8080 springboot-3.0 ----> for running container docker
# docker run --rm -p 8099:8099 rnd-springboot-3.0

# docker tag <image_name>:<tag> <username>/<image_name>:<tag>    --> docker tag rnd-springboot-3.0:latest septianreza/rnd-springboot-3.0:latest   ==> untuk membuat tag yang akan di push ke docker hub
# docker push <username>/<image_name>:<tag>    --> docker push septianreza/rnd-springboot-3.0:latest    ==> untuk push image ke docker hub


# for running sonar
# docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
# mvn clean verify sonar:sonar -Dsonar.projectKey=springboot-3 -Dsonar.projectName='springboot-3' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_f46184d90a5b2afe773b44071ace8c0127281067