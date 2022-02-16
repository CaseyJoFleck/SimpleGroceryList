# Simple Grocery Application Database

This is a postgres database to manage data for groceries.

## Author
**[Casey Jo Fleck](https://github.com/CaseyJoFleck)** - Software Developer

### Docker
A Docker container can be built and executed using the following. 

#### Building using Dockerfile directly
To build the image using Dockerfile:
`docker image build -t postgres-db  .`

Then start the container:
```bash 
docker run -d \
-p 5432:5432 \
--env POSTGRES_PASSWORD="docker" \
--env POSTGRES_USER="postgres" \
--env POSTGRES_DB="grocery" 
postgres-db
```

Access DB at http://localhost:8080

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
Services will be located at http://localhost:8080 or postgres-db