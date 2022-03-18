FROM maven:3.8.4-openjdk-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17
COPY --from=build /usr/src/app/target/Mvc-demo-0.0.1-SNAPSHOT.jar /usr/app/Mvc-demo-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/Mvc-demo-0.0.1-SNAPSHOT.jar"]
