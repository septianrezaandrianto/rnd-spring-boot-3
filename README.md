# rnd-spring-boot-3

## Medium : 
1. gRPC Implementation : https://medium.com/@septianrezaa/tutorial-membuat-grpc-crud-dengan-java-17-dan-springboot-3-127aefd9e742
2. Redis Implementation : https://medium.com/@septianrezaa/tutorial-implementasi-redis-pada-project-java-spring-boot-3-dengan-docker-c8526cc76f0
3. Jenkins Installation : https://medium.com/@septianrezaa/tutorial-install-jenkins-di-operating-system-windows-4b0cb4788325
4. Sonarqube and Jenkins Implementation (Part I) : https://medium.com/@septianrezaa/tutorial-implementasi-code-quality-check-dengan-sonarqube-dan-jenkins-pada-project-spring-boot-d7555be1507b
5. Sonarqube and Jenkins Implementation (Part II) : https://medium.com/@septianrezaa/tutorial-implementasi-code-quality-check-dengan-sonarqube-dan-jenkins-pada-project-spring-boot-ddbb800ded7e

##General Info, this project using :
1. Java JDK 17.
2. Java Spring Boot 3.1.0-SNAPSHOT version.
3. Maven 3.9.1.

## Libraries :
1. Spring Web.
2. Spring Boot DevTools.
3. Lombok.
4. Spring Data JPA.
5. H2 Database.
6. Gson.
7. Apache POI.
8. openapi (swagger3).
9. jUnit5.

## Rules :
1. Import the postman collection from postman-collection folder in this project to your postman.
2. mvn spring-boot:run : Use this script for running this project using CMD / Terminal.
3. Open http://localhost:8080/h2-console in your browser for checking Memory databse.
4. Open http://localhost:8080/swagger-ui/index.html in your browser for opening swagger.
5. Open http://localhost:8080/api-docs in your browser for opening API docs.

## I provide some services :
1. Insert Car List into DB (http://localhost:8080/api/saveCars) -> using form-data, and you can upload the raw data in csv in raw-data folder)
2. Get Car List in DB (http://localhost:8080/api/getAllCar)
3. Generate Excel in Backgrond process (http://localhost:8080/report/generate)

# gRPC
## Libraries :
1. GRPC Spring Boot Starter.
2. Grpc Stub.
3. Grpc Protobuf.
4. Javax Anotation.

# Redis 
## Library :
1. Redisson

## I provide service for save data into Redis : 
1. http://localhost:8080/redis/save

# SonarQube
## Library :
1. Jacoco

# Docker Command :
1. docker build -t <repository _name >:<tag> .    ==> docker build -t springboot-3.0:SNAPSHOOT-0.0.1    --> for create docker image
2. docker image ls    --> for look docker image
3. docker rmi <image_id>    ==> docker rmi f9e76994174d    --> for delete docr image
4. docker ps -a    -> for look all container active / inactive
5. docker stop <container_id>    ==> docker stop d0308bcd2b28    --> for stop docker container
6. docker rm <container_id>    ==> docker rm d0308bcd2b28    --> for delete docker container
7. docker run -d --name <container_name> -p <expose_port>:<properites_port> <image_name>:<image_port>    ==> docker run -d --name springboot-3.0 -p 8099:8080 springboot-3.0 ----> for running container docker


# Sonarqube
## Library :
1. Jacoco

# Rules :
1. Run Sonarqube Container at Docker : docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
2. Open on your browser and type http://localhost:9000 for open Sonarqube page
3. Type in your service for scan your project : mvn clean verify sonar:sonar -Dsonar.projectKey=springboot-3 -Dsonar.projectName='springboot-3' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_f46184d90a5b2afe773b44071ace8c0127281067
