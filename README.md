# chaTop-back-OCR

## Description
This is the backend service for the chaTop application. It provides APIs for managing users, rentals, and messages.

## API Documentation (Swagger)
Documentation is available through Swagger UI at: http://localhost:3001/swagger-ui/index.html

## Setup and Configuration

### Prerequisites
- Java 8 or higher
- Maven
- Front-End (https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

### Configuration

Git clone:

```
git clone https://github.com/simontessard/chaTop-back-OCR.git
```

Go inside folder and install dependencies:

```
mvn install
```

Create a database named chatop and run the SQL script provided.

Update the `application.properties` file with your Amazon S3 credentials:

```
amazonProperties.accessKey=YOUR_ACCESS_KEY
amazonProperties.secretKey=YOUR_SECRET_KEY
amazonProperties.bucketName=YOUR_BUCKET_NAME
```