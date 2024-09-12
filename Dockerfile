FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.jar ./English4Kids-Backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "English4Kids-Backend-0.0.1-SNAPSHOT.jar"]