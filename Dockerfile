FROM openjdk:11

WORKDIR /application

RUN mkdir "files-upload/product-image"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]