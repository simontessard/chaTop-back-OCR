package com.chatop.api.services;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * Service class for handling Amazon S3 operations.
 */
@Service
public class AmazonService {

    /**
     * The S3 client used for interacting with Amazon S3.
     */
    private final S3Client s3Client;

    /**
     * The name of the S3 bucket to interact with.
     */
    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    /**
     * Constructor for the AmazonService class.
     *
     * @param s3Client The S3 client to use for interacting with Amazon S3.
     */
    public AmazonService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Uploads an object to the S3 bucket.
     *
     * @param key      The key to assign to the uploaded object.
     * @param filePath The path to the file to upload.
     * @return The URL of the uploaded object.
     */
    public String putObject(String key, String filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, Paths.get(filePath));

        String url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
        return url;
    }
}