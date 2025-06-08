# HRSkillNinja

A lightweight candidate-tracking microservice for internal HR department use.
Educational application.

## Features

- REST/JSON API for candidate management
- Enforced candidate lifecycle state machine
- Full-text search capabilities
- Input validation
- Error handling

## Technology Stack

- Java 21 (LTS)
- Spring Boot 3.x
- PostgreSQL 15+
- Maven 3.9+

## Prerequisites

- Java 21 or later
- Maven 3.9 or later
- PostgreSQL 15 or later

## Configuration

The application is configured via `application.yaml`. Key settings:

```yaml
server:
  port: 8090
```

## Building

```bash
mvn clean package
```

## API Endpoints

Base URL: `http://localhost:8080/api/v1/candidates`

### Create Candidate
```
POST /
Content-Type: application/json

{
  "fio": "Ivanov Ivan Ivanovich",
  "age": 32,
  "position": "Senior Java Developer",
  "cvInfo": "..."
}
```

### Update Candidate
```
PUT /{id}
Content-Type: application/json

{
  "fio": "Ivanov Ivan Ivanovich",
  "age": 33,
  "position": "Senior Java Developer",
  "cvInfo": "..."
}
```

### Change Status
```
PUT /{id}/status
Content-Type: application/json

{
  "status": "INTERVIEW"
}
```

### Change Comment
```
PUT /{id}/comment
Content-Type: application/json

{
  "comment": "Passed tech screen"
}
```

### Get All Candidates
```
GET /
```

### Get Candidate by ID
```
GET /{id}
```

### Search Candidates
```
GET /search?fio=Ivanov&status=NEW,CV_REVIEW&position=Developer
```

## Candidate Status Flow

```
NEW → CV_REVIEW → SCHEDULED_FOR_INTERVIEW → INTERVIEW → OFFER → ACCEPTED → STARTED_WORKING
   ↘ DECLINED
```

Any state can transition to DECLINED.
