package com.hidq.products.service;

import com.hidq.products.exception.CustomException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.puiblic-host}")
    private String minioHost;

    public String save(MultipartFile file) throws Exception {
        log.info("MinioServiceImpl.save entry: {}", file.getOriginalFilename());
        String fileName = LocalDateTime.now()+file.getOriginalFilename().replaceAll(" ", "");
        try{
            log.info("Saving image");
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
            String fileSavedUrl = extractUrlImage(fileName);
            log.info("return image url: {}", fileSavedUrl);
            return fileSavedUrl;
        } catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private String extractUrlImage(String filename){
        return minioHost+bucket+"/"+filename;
    }


}
