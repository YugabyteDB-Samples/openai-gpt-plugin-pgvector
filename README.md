# YugaPlus
A sample streaming service with your favorite movies and series. Built on YugabyteDB.

## Prerequisites

1. The latest version of Docker.
2. Node.js 20+
3. Java 21+. Use [sdkman](https://sdkman.io) to install it within a minute.
4. Maven 3.9+

## Start Postgres in Docker

1. Create the `postgres-volume` directory for the Postgres container's volume in your home dir. The volume is handy if you'd like to access the logs easily and don't want to lose data when the container is recreated from scratch:
    ```shell
    mkdir ~/postgres-volume
    ```
2. Create a custom Docker network:
    ```shell
    docker network create yugaplus-network
    ```
3. Start the Postgres container with the pgvector extension:
    ```shell
    docker run --name postgres --net yugaplus-network \
        -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password \
        -p 5432:5432 \
        -v ~/postgresql-volume/:/var/lib/postgresql/data \
        -d ankane/pgvector:latest
    ```
3. Make sure the container is running:
    ```shell
    docker container ls -f name=postgres
    ```

## Start the Backend

1. Provide your OpenAI key in the `backend/src/main/resources/application.properties` file:
    ```properties
    spring.ai.openai.api-key=sk-
    ```
2. Go to the backend directory and start the app:
    ```shell
    cd backend
    mvn spring-boot:run
    ```

## Start the Frontend

1. Go to the frontend directory:
    ```shell
    cd frontend
    ```
2. Start the app:
    ```shell
    npm start
    ```

## Test the App

Sign in using the following credentials:

* `user1@gmail.com/password` - already has some movies in the library
* `user2@gmail.com/password` - the library is empty
 

Try a few prompts:
*A movie about a space adventure.*
*A kids-friendly movie with unexpected ending.*