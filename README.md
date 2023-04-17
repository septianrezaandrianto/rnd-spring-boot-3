# rnd-spring-boot-3

##General Info, this project using :
Java JDK 17,
Java Spring Boot 3.1.0-SNAPSHOT version,
Maven 3.6.3.

##Library :
Spring Web,
Spring Boot DevTools,
Lombok,
Spring Data JPA,
H2 Database,
Gson,
Apache POI.

## Rules :
1. Import the postman collection from postman-collection folder in this project to your postman.
2. mvn spring-boot:run : Use this script for running this project using CMD / Terminal.
3. Open http://localhost:8080/h2-console in your browser for check Memory databse.

## I provide some services:
1. Insert Car List into DB (http://localhost:8080/api/saveCars) -> using form-data, and you can upload the raw data in csv in raw-data folder)
2. Get Car List in DB (http://localhost:8080/api/getAllCar)
3. Generate Excel in Backgrond process (http://localhost:8080/report/generate)
