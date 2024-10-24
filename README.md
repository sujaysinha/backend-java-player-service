## Player service

## Project Overview
Player service is a spring boot based microservice which serves the contents of `Player.csv` through a REST API. This service also integrates with Ollama using [Ollama4j](https://ollama4j.github.io/ollama4j/intro) library.

## Key Features

- Serve the contents of `Player.csv` through a REST API
- In memory H2 database
- Integrate with Ollma using Ollama4j library

### API Endpoints
- `GET /v1/players` - returns the list of all players.
- `GET /v1/players/{playerId}` - returns a single player by `playerId`.
- `GET /v1/chat/list-models` - returns the list of Ollama models available in the Gitpod workspace
- `POST /v1/chat/` - Endpoint to chat with the available Ollama model

The database connected to this service is `in-memory H2 Database`.

### Project Structure

- `/collection` folder contains sample requests for player service.
- `/src`: Source code
    - `/config`: Configuration for Ollama4j library
    - `/controller`: API controllers
    - `/model`: Data models
    - `/repository`: Data repositories
    - `/service`: Service layer implementations
- `/player-service-model` folder contains a dummy AI model for `Player.csv` data.

### Technology Stack

- Java 17
- Maven
- Spring Boot 3.3.4 (with Spring Web MVC, Spring Data JPA)
- H2 Database
- Docker
- [Ollama4j](https://ollama4j.github.io/ollama4j/intro)

## Prerequisites

Before you begin, ensure you have the following installed on your development machine:

1. **Java 17**
    - Verify installation: `java --version`

2. **Maven**
    - Download and install from [maven.apache.org](https://maven.apache.org/install.html)
    - Verify installation: `mvn --version`

2 **Docker**
    - Download and install from [docker.com](https://www.docker.com/)
    - Verify installation: `docker --version`

3. **Git**
    - Download and install from [git-scm.com](https://git-scm.com/)
    - Verify installation: `git --version`

## Getting Started

### Local Setup

1. Fork the repository:
    - Visit the GitHub repository
    - Click the "Fork" button in the top-right corner to create your own copy of the repository

2. Clone your forked repository:
   ```
   git clone https://github.com/your-username/player-service-java.git
   cd player-service-java
   ```

3. Install dependencies:
   ```
   mvn clean install -DskipTests
   ```

4. Start the server:
   ```
   mvn spring-boot:run
   ```

5. Open your browser and visit `http://localhost:8080` or `http://127.0.0.1:8080/` to test the application.


6. Run tests:
   ```
   mvn clean install
   ```
   
### GenAI Integration with Ollama

1. Pull and run Ollama docker image and download `tinyllama` model

    - `docker pull ollama/ollama`
    - `docker run -it -v ~/ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama`
    - `docker exec -it ollama ollama run tinyllama`

2. Test API server

    - `curl -v --location 'http://localhost:11434/api/generate' --header 'Content-Type: application/json' --data '{"model": "tinyllama","prompt": "why is the sky blue?", "stream": false}'`
  
### Run with Gitpod

This app can also be run in Gitpod. We recommend running using Gitpod to build and run this app on the browser. To run this app in Gitpod you will need -
- Github account
- Gitpod Classic account

Click on the below link to open this repository in Gitpod

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/Intuit-A4A/player-service-python)

- Select the IDE of your choice. We recommend `VSCode IDE in browser`
- Standard Configuration
- Click `Continue`


<img width="1117" alt="Screenshot 2024-10-23 at 8 29 12 PM" src="https://github.com/user-attachments/assets/5344cb4a-5754-4c92-9fd9-27263e25e00c">




