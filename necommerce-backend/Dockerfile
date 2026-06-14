# openjdk:11 снят с Docker Hub — заменён на Eclipse Temurin (официальный JDK)
FROM eclipse-temurin:11-jdk
WORKDIR /src
COPY . .
RUN ./gradlew bootJar
RUN mkdir /app && mv ./build/libs/necommerce-1.0.jar /app/
WORKDIR /app
COPY ./src/main/resources/static media
CMD ["java", "-jar", "necommerce-1.0.jar"]