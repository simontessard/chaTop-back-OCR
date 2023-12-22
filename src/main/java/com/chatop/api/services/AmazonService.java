package com.chatop.api.services;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class AmazonService {

    private final S3Client s3Client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    public AmazonService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void listObjects() {
        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjResponse = s3Client.listObjectsV2(listObjectsReqManual);

        for (S3Object content : listObjResponse.contents()) {
            System.out.println(content.key());
        }
    }

    public void getObject(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.getObject(getObjectRequest, Paths.get("downloaded-" + key));
    }

    public String putObject(String key, String filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, Paths.get(filePath));

        // Get the URL of the uploaded object
        String url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();

        return url;
    }
}