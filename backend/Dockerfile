FROM openjdk:17-alpine

WORKDIR .
COPY gradle gradle
COPY src src
COPY build.gradle.kts build.gradle.kts
COPY gradlew gradlew
COPY settings.gradle.kts settings.gradle.kts

RUN ["./gradlew", "bootJar"]

ENTRYPOINT ["java", "-jar", "./build/libs/application-1.0.0.jar"]