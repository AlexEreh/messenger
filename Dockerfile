FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="xcode111@mail.ru"
EXPOSE 8080
RUN mkdir /app

COPY build/libs/*.jar /app/*.jar

ENTRYPOINT ["java", "-jar","/app/*.jar"]
