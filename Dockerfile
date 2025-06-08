# Use Java 21 slim Alpine-based image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar to the image
COPY target/tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose port if needed (e.g., 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
