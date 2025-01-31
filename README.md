# TaskManager

This project is a basic CRUD Spring Boot application designed to handle a "Task".

## Features

- Create, update, delete and list a Task
- Validation enabled for fieldds
- Uses jms for task creation

## Getting Started

### Prerequisites

- Java 17 or higher
- apache artemis

### Installation

1. **Clone the repository**
2. **Build the project**:
   ```sh
   mvn clean install
   ```
3. **Run artemis**
4. **Setup your env variables**:
    ```sh
   For windows:
    $env:ACTIVEMQ_USER="admin"
    $env:ACTIVEMQ_PASSWORD="securepassword"
   
   For Linux /macOS:
   export ACTIVEMQ_USER="admin"
   export ACTIVEMQ_PASSWORD="securepassword"
   ```
6. **Run the application**:
   ```sh
   mvn spring-boot:run
