# Spring Boot Sample Application

A simple Spring Boot application with a `/ping` GET API endpoint.

## Features

- Spring Boot 3.2.0
- Java 21
- REST API with `/ping` endpoint
- Docker support
- Health check endpoints via Spring Actuator

## API Endpoints

### Ping Endpoint
```
GET http://localhost:8080/ping
```

Response:
```json
{
  "status": "ok",
  "message": "pong",
  "timestamp": "2025-12-15T10:30:45.123"
}
```

### Health Check
```
GET http://localhost:8080/actuator/health
```

## Running Locally

### Prerequisites
- Java 21
- Maven 3.6+

### Build and Run with Maven
```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run

# Or run the JAR directly
java -jar target/sample-maven-project-1.0-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## Docker

### Build Docker Image
```bash
docker build -t sample-maven-project:latest .
```

### Run Docker Container
```bash
# Run on port 8080
docker run -p 8080:8080 sample-maven-project:latest

# Run in detached mode
docker run -d -p 8080:8080 --name sample-app sample-maven-project:latest
```

### Stop and Remove Container
```bash
docker stop sample-app
docker rm sample-app
```

## Testing the API

### Using curl
```bash
curl http://localhost:8080/ping
```

### Using a browser
Simply navigate to: `http://localhost:8080/ping`

### Using httpie (if installed)
```bash
http GET http://localhost:8080/ping
```

## Project Structure

```
sample-maven-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       └── App.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/
│               └── AppTest.java
├── Dockerfile
├── .dockerignore
├── pom.xml
└── README.md
```

## Development

### Add New Endpoints

Add new endpoints in `App.java`:

```java
@GetMapping("/your-endpoint")
public Map<String, String> yourEndpoint() {
    Map<String, String> response = new HashMap<>();
    response.put("message", "Your response");
    return response;
}
```

### Configuration

Application configuration can be modified in `src/main/resources/application.properties`

