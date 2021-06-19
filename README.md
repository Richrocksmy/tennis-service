# Tennis Service

<hr>

The Tennis Service is a Java REST Microservice that allows the client to query information about tennis matches
(either ongoing or in the future).

## Usage

<hr>

### Prerequisites

- Docker (you can get it [here](https://www.docker.com/products/docker-desktop)).

### Testing the service

From the project root, run ```./gradlew test```

### Building the project

From the root of the project, run ```./gradlew clean build```

### Starting the service

From the root of the project, run```./gradlew clean build```to compile and build. Then run ```docker-compose up```to
start the services.

### Consuming the service

Once the service is running, its API can be found [here](http://localhost:8080/swagger/). 
