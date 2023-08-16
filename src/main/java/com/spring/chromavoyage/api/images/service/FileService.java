package com.spring.chromavoyage.api.images.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spring.chromavoyage.api.images.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    // 기본 파일 저장 함수
    public List<UploadFile> storeFiles(List<MultipartFile>  multipartFiles) throws IOException {
        if(multipartFiles.isEmpty()) { // 파일이 없으면 그냥 null 반환
            return null;
        }
        List<UploadFile> imageUrls= multipartFiles.stream().map((multipartFile) -> {
            // 사용자가 업로드 한 파일이름 (원래 이름)
            String originalFilename = multipartFile.getOriginalFilename(); // image.png
            // 서버에 저장할 파일이름 (중복 방지용 이름)
            String storeFileName = createStoreFileName(originalFilename); // createStoreFileName을 통해 uuid 이름 생성

            // 파일 정보(메타데이터)와 함께, S3 버킷에 오브젝트 저장
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            try {
                amazonS3Client.putObject(bucket, storeFileName, multipartFile.getInputStream(),metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new UploadFile(originalFilename, storeFileName, amazonS3Client.getUrl(bucket, storeFileName).toString());
        }).collect(Collectors.toList());

        // 저장한 파일의 원래 이름과, 서버에 저장시킨 이름을 관리하는 객체 UploadFile을 생성 및 반환
        return imageUrls;
    }

    public UploadFile storeFile(MultipartFile  multipartFile) throws IOException {
        if(multipartFile.isEmpty()) { // 파일이 없으면 그냥 null 반환
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // image.png
            // 서버에 저장할 파일이름 (중복 방지용 이름)
        String storeFileName = createStoreFileName(originalFilename); // createStoreFileName을 통해 uuid 이름 생성

            // 파일 정보(메타데이터)와 함께, S3 버킷에 오브젝트 저장
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        try {
            System.out.println(multipartFile.getOriginalFilename());
            amazonS3Client.putObject(bucket, storeFileName, multipartFile.getInputStream(),metadata);
            System.out.println(amazonS3Client.getUrl(bucket, storeFileName).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadFile(originalFilename, storeFileName, amazonS3Client.getUrl(bucket, storeFileName).toString());

    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        // "qwe-ert-123-ewr-oind" <-- UUID (중복 방지용)

        // original file로부터 확장자 가져오기
        String extent = extractExtention(originalFilename);
        
        return uuid + "." + extent; // (새 이름.확장자) --> storeFilename
    }

    // 원래 파일 이름으로부터 . 기준으로 확장자만 분리해서 반환
    private String extractExtention(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
