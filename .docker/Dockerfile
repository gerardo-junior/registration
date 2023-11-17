FROM  adoptopenjdk:17-jdk-hotspot-focal as builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM adoptopenjdk:17-jre-hotspot-alpine3.18

WORKDIR /app

COPY --from=builder /app/target/seu-app.jar /app/seu-app.jar

# Expose port 8080
EXPOSE 8080

# Specify the default command to run on boot
CMD ["java", "-jar", "/app/seu-app.jar"]