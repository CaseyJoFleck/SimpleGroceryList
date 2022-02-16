# Simple Grocery REST Application

This is a REST application to manage services for groceries. 

## Author
**[Casey Jo Fleck](https://github.com/CaseyJoFleck)** - Software Developer

## Documentation and Swagger UI 

- To view the Swagger UI and test the service:
  - **[http://{server}:{port}/swagger-ui/api-docs.html](http://server:port/swagger-ui/api-docs.html)**
- To view the OpenAPI Specs as JSON: 
  - **[http://{server}:{port}/v3/api-docs/](http://server:port/v3/api-docs/)**
- To download the OpenAPI yaml:
  - **[http://{server}:{port}/v3/api-docs.yaml](http://server:port/v3/api-docs/)**

#### Viewing Documentation Locally

- **[http://localhost:8080/swagger-ui/api-docs.html](http://server:port/swagger-ui/api-docs.html)**
- **[http://localhost:8080/v3/api-docs/](http://server:port/v3/api-docs/)**
- **[http://localhost:8080/v3/api-docs.yaml](http://server:port/v3/api-docs/)**

### Docker
A Docker container can be built and executed using the following. \
\
**NOTE: YOU MUST HAVE THE THE POSTGRES DATABASE UP AND RUNNING IN DOCKER BEFORE BEING ABLE TO RUN THIS APPLICATION.**
#### Building using Dockerfile directly
To build the jar and image using Dockerfile:
`docker image build -t simple-grocery-list  .`

If you already have the jar file built for this application, use the following command to build the image:
`docker build -t simple-grocery-list -f Dockerfile.buildimageonly .`

Then start the container:
```bash 
docker run -d \
-p 8080:8080 \
--env POSTGRES_PASSWORD="docker" \
--env POSTGRES_USER="postgres" \
--env POSTGRES_DB="grocery" 
simple-grocery-list
```

Service will be located at http://localhost:8080

#### Start container using docker-compose
If you would like to specify the environment variables
inside a docker-compose file instead of through the docker-cli,
do the following:

1. Open docker-compose.yml file and replace the default environment values with your credentials.
```yaml 
    environment:
      - POSTGRES_PASSWORD=docker
      - POSTGRES_USER=postgres
      - POSTGRES_DB=grocery
```
To start the service, just type `docker-compose up -d` into the console. \
To stop the service and destroy the containers created, just type `docker-compose down`. \
Services will be located at http://localhost:8080

* Side note:
  - This docker-compose file will create the image if it is not already created.
  - If you would like to build just the image, please edit the line inside the yml file from
    `build: .` to:
```yaml 
build:
  context: .
  dockerfile: Dockerfile.buildimageonly
```
- In order to rebuild the image, run either `docker-compose build` or `docker-compose up -d --build`

