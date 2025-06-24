FROM gradle:8.7.0 AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home
COPY build.gradle.kts gradle.properties settings.gradle.kts /home/gradle/app/
COPY /gradle/libs.versions.toml /home/gradle/app/gradle/
WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace

FROM gradle:8.7.0 AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/app
WORKDIR /usr/src/app
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM amazoncorretto:21 AS runtime
EXPOSE 5000:5000
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/bakery.jar
ENTRYPOINT ["java", "-jar", "/app/bakery.jar"]