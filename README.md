
# Note Rest-API Kotlin-MyBatis-Mysql

Simple example to use kotlin + MyBatis + Mysql and testing
The intention is to have an example/template for future references

## Migrations

Go to the db folder: `cd db` 

To start the docker service you need to run the script: 
`sh exec-start.sh`

Wait for the completion of the migrations, if you don't have flyway, make it manually (pending)

To stop the containers: `sh exec-stop.sh`

To check the scheme go to: `db/sql`

## Tests

After the migration 

`mvn clean test`

With docker

`docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.8.1-jdk-11 mvn clean install`
