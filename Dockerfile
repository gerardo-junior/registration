FROM  openjdk:17-alpine as builder

WORKDIR /src
COPY . /src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-alpine

WORKDIR /app
COPY --from=builder /src/target/registration.jar /app/registration.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/registration.jar"]