FROM openjdk:8-alpine
RUN addgroup -S microservicios && adduser -S admin -G microservicios
USER admin:microservicios
VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD target/${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]