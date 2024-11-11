# Readme

This repository contains the API code for a Simple Calculator. The API is build with Spring Boot and Java 17. MySQL is used as the database. The API is deployed in AWS Lambda with Serverless framework.

## Requirements
1. Java 17
2. MySQL

## Setup Instructions
1. Create a database named `calculator`. The database schema is created automatically by the application.
2. Clone the repository
3. Open the project in your favorite IDE
4. Update the `application.properties` file with your MySQL database credentials.
5. Run the project. The API will be available at `http://localhost:8080`
6. Happy coding!

## Deployment
1. Run `mvn clean package` to build the project
2. Run `serverless deploy` to deploy the API to AWS Lambda
