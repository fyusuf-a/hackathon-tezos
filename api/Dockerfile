FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:resolve
RUN mvn package -DskipTests=true || true

COPY src ./src
RUN mvn package -DskipTests=true

FROM eclipse-temurin:21-alpine

WORKDIR /app
EXPOSE 8080

COPY --from=builder /app/target/*.jar ./

CMD [ "java", "-jar", "tezos-latest.jar" ]
