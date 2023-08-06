package com.spring.chromavoyage.api.images.controller;

import com.spring.chromavoyage.api.images.domain.ImageDto;
import com.spring.chromavoyage.api.images.domain.ImageInfo;
import com.spring.chromavoyage.api.images.domain.UploadFile;
import com.spring.chromavoyage.api.images.entity.ImageEntity;
import com.spring.chromavoyage.api.images.repository.ImageRepository;
import com.spring.chromavoyage.api.images.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {
    @Autowired
    private final ImageRepository imageRepository;
    @Autowired
    private final FileService fileService; //service
//    private final AmazonS3Client amazonS3Client;

    @PostMapping("/images/{coloring_location_id}")
    public void saveImage (@RequestParam Long group_id,
                           @RequestParam Long location_id,
                           @PathVariable Long coloring_location_id,
                           @ModelAttribute List<MultipartFile> file
                             ) throws IOException {
        log.info("group_id={}", group_id);
        log.info("location_id={}", location_id);
        log.info("coloring_location_id={}", coloring_location_id);

        // coloring_location_id에 해당하는 이미지 객체 생성 및 저장.
        // 단, 해당 이미지가 해당 group, location, coloring location의 id를 모두 가져야 함
        ImageDto imageDto = new ImageDto();

        // 업로드 된 이미지 가져와서 저장 + 저장 후 (원래 이름, 서버용 이름) 객체 리스트 반환
        List<UploadFile> storeImageFiles = fileService.storeFiles(file);
//        UploadFile storeImageFile = fileStore.storeFile(file);

        // 데이터베이스에 저장
        for (UploadFile storeImageFile : storeImageFiles) {

            imageDto.setImageFile(storeImageFile); // 파일이름관리
            imageDto.setImage_path(fileService.getFullPath(storeImageFile.getStoreFileName())); // 파일 저장경로
            imageDto.setFile_name(storeImageFile.getUploadFileName()); // 원본 파일 이름
            // 받아온 id 정보 (group, location, coloring location)
            imageDto.setGroupId(group_id);
            imageDto.setLocationId(location_id);
            imageDto.setColoringLocationId(coloring_location_id);

            ImageEntity imageEntity = imageDto.toEntity();
            imageEntity.setFile_name(imageDto.getFile_name());
            log.info(imageEntity.toString());

            ImageEntity savedImage = imageRepository.save(imageEntity); // image id 하나 DB에 저장
            log.info(savedImage.toString());
        }
    }

    @GetMapping("/images/{coloring_location_id}")
    public ResponseEntity<List<ImageInfo>> retrieveImages(@RequestParam Long group_id,
                                               @RequestParam Long location_id,
                                               @PathVariable Long coloring_location_id) throws IOException {
        HttpHeaders header = new HttpHeaders();

        // 예외 : 필수 파라미터 부재시 - 400:BAD_REQUEST
        if((location_id == null) || (group_id == null)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        log.info("coloring_location_id={}", coloring_location_id);
        log.info("group_id={}", group_id);
        log.info("location_id={}", location_id);

//        List<ImageEntity> imageEntities = imageRepository.findAllImageEntitiesByColoringLocationIdAndGroupIdAndLocationId(coloring_location_id, group_id, location_id);

        // coloring_location_id에 해당하는 이미지 모두 조회
        List<ImageEntity> imageEntities = imageRepository.findAllByColoringLocationId(coloring_location_id);
        log.info("---Entities---");
        log.info(imageEntities.toString());

        // 예외 : 이미지 부재시 or 해당 coloring_location_id 부재시 - 404:NOT_FOUND
        if (imageEntities.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 데이터 존재하면 Content-Type = Application/json
        header.setContentType(MediaType.APPLICATION_JSON);

        // json body = 'ImageInfo' object
        List<ImageInfo> imageInfoList = new ArrayList<>();

        for (ImageEntity imageEntity : imageEntities) {
            // 필수 응답 정보 (저장된 이미지의 id, 이미지 저장 경로, 사용자가 저장한 원본 파일 이름) 담아서 반환
            ImageInfo imageInfo = new ImageInfo(imageEntity.getImageId(), imageEntity.getImage_path(), imageEntity.getFile_name());

            log.info("---imageInfo---");
            log.info(imageInfo.toString());

            imageInfoList.add(imageInfo);
        }
        return new ResponseEntity<>(imageInfoList, header, HttpStatus.OK);
    }


    @DeleteMapping("/images/{coloring_location_id}")
    public ResponseEntity deleteImage(@RequestParam Long group_id,
                       @RequestParam Long location_id,
                       @PathVariable Long coloring_location_id,
                       @RequestBody Map<String, List> req) {
        HttpHeaders header = new HttpHeaders();

        // 예외 : 필수 파라미터 부재시 - 400:BAD_REQUEST
        if((location_id == null) || (group_id == null)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            log.info(req.toString());
            List<Map<String, String>> imageIdMapList = req.get("images");
//            log.info(images.toString());

            for (Map<String, String> imageIdMap : imageIdMapList) {
                log.info(imageIdMap.toString());
                Long imageId = Long.parseLong(imageIdMap.get("image_id"));
                log.info("imageId={}", imageId);
                imageRepository.deleteByImageId(imageId);
            }


            return new ResponseEntity(HttpStatus.NO_CONTENT);

        } catch(Exception e) {return new ResponseEntity(HttpStatus.NOT_FOUND);}
    }
}


