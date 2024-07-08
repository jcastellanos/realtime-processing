FROM gradle:8-jdk17-focal as BUILD
WORKDIR /home/gradle/src
COPY . .
RUN gradle build --no-daemon
# Start with a base image containing Java runtime
FROM amazoncorretto:17-alpine
VOLUME /tmp
# Copy app
COPY --from=build /home/gradle/src/build/libs/transaction-realtime-service.jar app.jar
# Make port 8080 available to the world outside this container
# EXPOSE 8080
ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]