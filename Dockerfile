FROM eclipse-temurin:21-jre
WORKDIR /app

# build jar first: mvn clean package -DskipTests
COPY target/das-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
