FROM amazoncorretto:17-alpine-jdk as build
WORKDIR /app/build
COPY . /app
RUN chmod +x /app/gradlew
RUN /app/gradlew build -x test

#
FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"]
