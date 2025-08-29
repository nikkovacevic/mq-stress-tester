# MQ Stress Tester

This project is a Quarkus-based application for stress-testing message queue (MQ) applications.
It sends a configurable number of messages to an AMQP 1.0 broker with optional metadata and parameterized message templates.

## Features
* REST API to trigger message sending.
* Configurable:
  - Number of messages.
  - AMQP metadata (sent as application properties).
  - Message template with placeholders ({{placeholder}}).
  - List of message parameters (cycled or expanded across messages).
* Messages sent asynchronously with an unbounded buffer for high throughput.

### Example request DTO
```json
{
  "numberOfMessages": 10,
  "amqpMetadata": {
    "correlationId": "stress-test-001",
    "priority": "5"
  },
  "message": "Hello {{placeholder}}",
  "messageParameters": ["Alice", "Bob", "Charlie"]
}
```

### Example output DTO
```text
Hello Alice
Hello Bob
Hello Charlie
Hello Alice
Hello Bob
...
```

## Configuration
* Broker: Artemis running on amqp://localhost:5672
* Queue: mq-stress-tester
* Credentials: U=admin, P=admin

## Running the project

1. Build the project with `./mvnw clean install`
2. Run the docker containers with `docker compose up --build`