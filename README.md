# chaTop-back-OCR

## Description
This is the backend service for the chaTop application. It provides APIs for managing users, rentals, and messages.

## API Documentation
API documentation is available through Swagger UI and can be accessed at: http://localhost:[port]/swagger-ui/index.html

## Setup and Configuration

### Prerequisites
- Java 8 or higher
- Maven

### Configuration
Update the `application.properties` file with your Amazon S3 credentials:

```properties
amazonProperties.accessKey=YOUR_ACCESS_KEY
amazonProperties.secretKey=YOUR_SECRET_KEY
amazonProperties.bucketName=YOUR_BUCKET_NAME