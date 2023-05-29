# rnd-spring-boot-3

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

## Medium : 
https://medium.com/@septianrezaa/tutorial-membuat-grpc-crud-dengan-java-17-dan-springboot-3-127aefd9e742 


# Redis 
## Library :
1. Redisson

## I provide service for save data into Redis : 
1. http://localhost:8080/redis/save


# Docker Command :
## docker build -t <repository _name >:<tag> .    ==> docker build -t springboot-3.0:SNAPSHOOT-0.0.1    --> for create docker image
## docker image ls    --> for look docker image
## docker rmi <image_id>    ==> docker rmi f9e76994174d    --> for delete docr image
## docker ps -a    -> for look all container active / inactive
## docker stop <container_id>    ==> docker stop d0308bcd2b28    --> for stop docker container
## docker rm <container_id>    ==> docker rm d0308bcd2b28    --> for delete docker container
## docker run -d --name <container_name> -p <expose_port>:<properites_port> <image_name>:<image_port>    ==> docker run -d --name springboot-3.0 -p 8099:8080 springboot-3.0 ----> for running container docker


# Sonarqube
## Library :
1. Jacoco

# Rules :
1. Run Sonarqube Container at Docker : docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
2. Open on your browser and type http://localhost:9000 for open Sonarqube page
3. Type in your service for scan your project : mvn clean verify sonar:sonar -Dsonar.projectKey=springboot-3 -Dsonar.projectName='springboot-3' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_f46184d90a5b2afe773b44071ace8c0127281067
