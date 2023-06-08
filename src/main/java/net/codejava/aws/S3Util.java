package net.codejava.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class S3Util {
    private static final String BUCKET = "nitin-s3-bucket-6643";
     
    public static void uploadFile(String fileName, InputStream inputStream, MultipartFile file)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        /*S3Client client = S3Client.builder().build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(fileName)
                            .build();*/

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        Optional<Map<String, String>> optionalMetaData = Optional.of(metadata);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        AWSCredentials awsCredentials =
                new BasicAWSCredentials("AKIAW3EKGTSMBEEIVYOY", "K+rZIUBDrkkCf/oVRKf4c40Wfe8TRGQJuAO58NNc");
         AmazonS3ClientBuilder
                .standard()
                .withRegion("ap-south-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build().putObject("nitin-s3-bucket-6643", fileName, inputStream, objectMetadata);

        /*client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));*/
    }
}