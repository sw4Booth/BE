package com.sw4.domain.photo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path tempFile = Files.createTempFile("upload-", fileName);
            file.transferTo(tempFile.toFile());

            AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
            S3Client s3 = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .build();

            s3.putObject(PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build(), tempFile);

            return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }
    }
}
