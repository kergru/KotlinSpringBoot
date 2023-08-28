FROM openjdk:17-jdk-slim
LABEL authors="kgr"
ADD ./build/libs/KotlinSpringBoot-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]