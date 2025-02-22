FROM openjdk:17-alpine as build
WORKDIR /app
COPY *.gradle gradle.* gradlew /app/
COPY gradle /app/gradle
RUN chmod +x gradlew
RUN apk add --no-cache bash
COPY src /app/src
RUN ./gradlew clean build -x test --no-daemon
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]