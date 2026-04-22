# Use Java 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy backend files
COPY backend /app

# Build app
RUN chmod +x mvnw || true
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Run app
CMD ["java", "-jar", "target/tracker-1.0.0.jar"]