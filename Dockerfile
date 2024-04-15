FROM  openjdk:17-alpine AS builder

WORKDIR /src
COPY . /src

RUN ./mvnw test && \
    ./mvnw clean package

FROM openjdk:17-alpine

WORKDIR /app
COPY --from=builder /src/target/registration.jar /app/registration.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/registration.jar"]