FROM  openjdk:17-alpine as builder

WORKDIR /app

COPY . /app

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app/target/registration.jar /app/registration.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/registration.jar"]