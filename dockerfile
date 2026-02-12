# --- Build stage ---
FROM maven:3.5-jdk-8 as buildstage
WORKDIR /app
# Copy pom.xml and download dependencies using Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copy source code and build project
COPY . .
RUN mvn install

# --- Final stage ---
FROM openjdk:8-jre-alpine
WORKDIR /app
# Copy only the final JAR artifact from the build stage
COPY --from=buildstage /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
