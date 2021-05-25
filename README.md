# Getting Started
## To run the application in a docker container run :
* mvnw clean package -DskipTests  // This generates a JAR
* cp target/challenge-0.0.1-SNAPSHOT.jar src/main/docker // this will copy your jar to where you have your docker-compose.yml and Dockerfile
* docker-compose up  // This will run the two docker services app (for your jar) and db (for postgres)

To rebuild our Docker image when changes are made to the application :
* application JAR file
* ctrl+c  // This exits in terminal
* docker-compose down  // This stops the container
* docker rmi challenge:latest // This deletes the application Docker image
Then you do as explained above.

## Alternatively
Have postgresSql up ad running and run the application in your IDE.

