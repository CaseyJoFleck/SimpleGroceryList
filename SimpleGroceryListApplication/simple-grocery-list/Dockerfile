#build jar
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR build
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY . .
RUN mvn package

#build image
FROM openjdk:17

EXPOSE 8080

ENV POSTGRES_PASSWORD="${POSTGRES_PASSWORD}" \
    POSTGRES_USER="${POSTGRES_USER}" \
    POSTGRES_DB="${POSTGRES_DB}" \
    POSTGRES_SERVER="postgres-db"

COPY --from=builder /build/target/simple-grocery-list-*.jar simple-grocery-list.jar

ENTRYPOINT ["java", "-jar", "/simple-grocery-list.jar"]