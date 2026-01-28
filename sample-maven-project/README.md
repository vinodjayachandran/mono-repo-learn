# Spring Boot Sample Application

A Spring Boot application demonstrating REST APIs and WebSocket capabilities with asynchronous processing.

## Features

- Spring Boot 3.2.0
- Java 21
- REST API endpoints (`/ping`, `/time`)
- WebSocket support with STOMP protocol
- Asynchronous processing with CompletableFuture
- Docker support
- Health check endpoints via Spring Actuator

## API Endpoints

### API Documentation (Swagger UI)
```
Swagger UI: http://localhost:8080/swagger-ui/index.html
Raw OpenAPI spec: http://localhost:8080/v3/api-docs
```

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

### Time Endpoint
```
GET http://localhost:8080/time
```

Response (after 3-second delay):
```json
{
  "status": "ok",
  "currentTime": "2025-12-15T10:30:48.456",
  "message": "Time retrieved after 3 seconds delay"
}
```

**Note:** This endpoint demonstrates asynchronous processing. The response is delayed by 3 seconds to showcase async capabilities.

### Health Check
```
GET http://localhost:8080/actuator/health
```

### Redis Endpoints
```
POST http://localhost:8080/redis/add?key=<key>&value=<value>
PUT  http://localhost:8080/redis/update?key=<key>&value=<value>
GET  http://localhost:8080/redis/get?key=<key>
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

## WebSocket Endpoints

The application supports WebSocket communication using the STOMP protocol for real-time messaging.

### WebSocket Endpoints

- **SockJS Endpoint:** `ws://localhost:8080/ws` (for browser clients with SockJS fallback)
- **Raw WebSocket Endpoint:** `ws://localhost:8080/ws-raw` (for raw WebSocket clients)

### WebSocket Topics

- **Subscribe to:** `/topic/time` - Receives time responses
- **Send messages to:** `/app/time` - Triggers time request

### WebSocket Message Flow

1. Connect to WebSocket endpoint (`/ws` or `/ws-raw`)
2. Send STOMP CONNECT frame to establish session
3. Subscribe to `/topic/time` to receive responses
4. Send message to `/app/time` to request current time
5. Receive response on `/topic/time` after 3-second delay

## Testing the API

### REST API Testing

#### Using curl
```bash
# Test ping endpoint
curl http://localhost:8080/ping

# Test time endpoint
curl http://localhost:8080/time
```

#### Using a browser
Simply navigate to:
- `http://localhost:8080/ping`
- `http://localhost:8080/time`

#### Using httpie (if installed)
```bash
http GET http://localhost:8080/ping
http GET http://localhost:8080/time
```

### WebSocket Testing

#### Using the WebSocket Test Page (Recommended)

The application includes a built-in test page for testing both REST and WebSocket endpoints:

1. Start the application
2. Navigate to: `http://localhost:8080/websocket-test.html`
3. Use the interactive UI to:
   - Test REST API endpoints
   - Connect to WebSocket
   - Subscribe to topics
   - Send messages and receive responses

The test page provides:
- Visual feedback for all operations
- Response time measurements
- Real-time WebSocket message display
- Error handling and status indicators

#### Using curl (REST only)
```bash
curl http://localhost:8080/time
```

**Note:** WebSocket testing requires a WebSocket client. Use the test page or a WebSocket client tool like Postman, Insomnia, or `wscat`.

## Project Structure

```
sample-maven-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── App.java                    # Main application class
│   │   │       ├── config/
│   │   │       │   ├── WebSocketConfig.java     # WebSocket configuration
│   │   │       │   ├── StompMessageInterceptor.java  # STOMP message interceptor
│   │   │       │   └── WebSocketEventListener.java    # WebSocket event listener
│   │   │       ├── controller/
│   │   │       │   ├── PingController.java     # REST controller for /ping
│   │   │       │   ├── TimeController.java     # REST controller for /time
│   │   │       │   └── TimeWebSocketController.java  # WebSocket controller
│   │   │       └── service/
│   │   │           ├── PingService.java        # Ping service implementation
│   │   │           └── TimeService.java        # Time service implementation
│   │   └── resources/
│   │       ├── application.properties          # Application configuration
│   │       └── static/
│   │           └── websocket-test.html         # WebSocket test page
│   └── test/
│       └── java/
│           └── com/example/
│               └── AppTest.java
├── Dockerfile
├── pom.xml
└── README.md
```

## Architecture

The application follows a layered architecture:

- **Controllers** (`controller` package): Handle HTTP requests and WebSocket messages
- **Services** (`service` package): Contain business logic with asynchronous processing
- **Configuration** (`config` package): WebSocket and STOMP configuration

### Asynchronous Processing

Both REST and WebSocket endpoints use `CompletableFuture` for asynchronous processing:
- Services are annotated with `@Async`
- Controllers return `CompletableFuture<Map<String, Object>>`
- Spring handles async execution automatically

## Development

### Adding New REST Endpoints

1. Create a service in `com.example.service` package:
```java
@Service
public class YourService {
    @Async
    public CompletableFuture<Map<String, Object>> yourMethod() {
        // Your async logic
        return CompletableFuture.completedFuture(response);
    }
}
```

2. Create a controller in `com.example.controller` package:
```java
@RestController
@RequiredArgsConstructor
public class YourController {
    private final YourService yourService;
    
    @GetMapping("/your-endpoint")
    public CompletableFuture<Map<String, Object>> yourEndpoint() {
        return yourService.yourMethod();
    }
}
```

### Adding New WebSocket Endpoints

1. Add a method to your WebSocket controller:
```java
@MessageMapping("/your-topic")
@SendTo("/topic/your-response")
public CompletableFuture<Map<String, Object>> handleYourMessage(Map<String, String> message) {
    return yourService.yourMethod();
}
```

2. Clients subscribe to `/topic/your-response` and send messages to `/app/your-topic`

### Configuration

Application configuration can be modified in `src/main/resources/application.properties`:
- Server port
- Logging levels
- Actuator endpoints
- WebSocket settings

