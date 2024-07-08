FROM maven:3.9.8-amazoncorretto-17 as BUILD
WORKDIR /home/maven
COPY . .
RUN mvn clean compile package
# Start with a base image containing Java runtime
FROM amazoncorretto:17-alpine
VOLUME /tmp
# Copy app
COPY --from=build /home/maven/target/transaction-realtime-service-1.0.jar app.jar
# Make port 8080 available to the world outside this container
# EXPOSE 8080
ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]