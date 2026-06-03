# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . /app
# Build the fat jar using Ktor plugin's buildFatJar task
RUN gradle buildFatJar --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy the generated fat jar from the build stage
COPY --from=build /app/build/libs/*-all.jar /app/app.jar

# Railway automatically provides the PORT environment variable
ENV PORT=8080
EXPOSE ${PORT}

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
