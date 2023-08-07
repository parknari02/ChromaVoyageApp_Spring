package com.spring.chromavoyage.api.profiles.controller;


import com.spring.chromavoyage.api.profiles.domain.ProfileDto;
import com.spring.chromavoyage.api.profiles.domain.UploadImage;
import com.spring.chromavoyage.api.profiles.entity.ProfileEntity;
import com.spring.chromavoyage.api.profiles.repository.ProfileRepository;
//import com.spring.chromavoyage.api.images.service.FileService;
import com.spring.chromavoyage.api.profiles.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private final ProfileRepository profileRepository;
    @Autowired
    private final ProfileService profileService;

//    @Autowired
//    private final FileService fileService; //service


    // 프로필 정보 수정 -> 사용자 이름 및 사용자 프로필 이미지 수정
    @PostMapping("/profiles")
    public ResponseEntity<ProfileDto> updateProfile(@RequestParam Long user_id,
                            @RequestParam String name,
                            @RequestParam MultipartFile file) {

        // 예외 : 필수 파라미터 부재시 - 400:BAD_REQUEST
        if ( (user_id == null) || (name == null && file == null))  {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        log.info("user_id={}", user_id);
        log.info("user_name={}", name);

        HttpHeaders header = new HttpHeaders();

        try {
            // aws 서버에 파일이름 변경 및 경로 저장
            UploadImage storeImage = fileStore.storeFile(file);
            // 이미지 파일 서버에 저장 후, 절대 경로 가져오기.
            String fullpath = fileService.getFullPath(storeImage.getStoreImgName());
            ProfileDto profileDto = new ProfileDto(name, fullpath, storeImage);

            // 해당 경로 -> 사용자의 프로필 이미지 경로로 업데이트
            // 요청으로 들어온 사용자 이름으로 업데이트
            if (name == null && file != null) { // 이미지 파일만 변경되는 경우
                ProfileEntity savedProfile = profileService.updateProfileImg(user_id, fullpath);

            } else if (file == null && name != null) { // 사용자 이름만 변경되는 경우
                ProfileEntity savedProfile = profileService.updateProfileName(user_id, name);

            } else if (file != null && name != null) { // 사용자 이름과 이미지가 모두 변경되는 경우
                profileService.updateProfileName(user_id, name);
                ProfileEntity savedProfile = profileService.updateProfileImg(user_id, fullpath);
            }
            log.info(savedProfile.toString());
            header.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(profileDto, header, HttpStatus.OK);
        }
        catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.BAD_GATEWAY)
        }
    }


    // 프로필 정보 조회
    @GetMapping("/profiles")
    public ResponseEntity<ProfileDto> retrieveProfile(@RequestParam Long user_id) throws IOException {
        // 예외 : 필수 파라미터 부재시 - 400:BAD_REQUEST
        if (user_id == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        log.info("user-id={}", user_id);

//        List<ImageEntity> imageEntities = imageRepository.findAllImageEntitiesByColoringLocationIdAndGroupIdAndLocationId(coloring_location_id, group_id, location_id);

        try {
            // user_id 이용해서 해당 유저의 프로필 가져오기 (이름 및 프로필 사진 경로)
            ProfileEntity profileEntity = profileRepository.findByUserId(user_id);

            log.info("---Entity---");
            log.info(profileEntity.toString());

            HttpHeaders header = new HttpHeaders();
            // 데이터 존재하면 Content-Type = Application/json
            header.setContentType(MediaType.APPLICATION_JSON);

            ProfileDto profileDto = new ProfileDto();
            profileDto.setUserId(profileEntity.getUserId());
            profileDto.setUser_name(profileEntity.getUser_name());
            profileDto.setProfileImg_path(profileEntity.getProfileImg_path());

            log.info("----dto----");
            log.info(profileDto.toString());

            return new ResponseEntity<>(profileDto, header, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/images")
    public ResponseEntity deleteProfileImg(@RequestParam Long user_id) {
        // 예외 : 필수 파라미터 부재시 - 400:BAD_REQUEST
        if (user_id == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // 유저가 가지고 있는 프로필 이미지 경로에 있는 이미지 파일 삭제
        // 이후 기본 프로필 사진(우선 검은색 png 파일로 대체)으로 변경
        // 기본 프로필 사진은 고정 경로(임의로 설정)에서 꺼내오기
        try {
            profileService.deleteProfileImg(user_id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        catch(Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);}
    }

}


