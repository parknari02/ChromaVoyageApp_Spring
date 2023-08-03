package com.spring.chromavoyage.api.images.service;

import com.spring.chromavoyage.api.images.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    // /User/documents/../파일명.png 전체 경로 반환
    public String getFullPath(String filename) {
        return fileDir + filename;
    }



    // 여러 개의 파일을 업로드하는 경우 iteration을 돌면서 모두 저장하고, 리스트에 담아서 저장된 객체들을 반환해줌
    // UploadFile --> fileName 관리하는 객체
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) { //파일이 들어있으면
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    // 기본 파일 저장 함수
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) { // 파일이 없으면 그냥 null 반환
            return null;
        }

        // 사용자가 업로드 한 파일이름 (원래 이름)
        String originalFilename = multipartFile.getOriginalFilename(); // image.png
        // 서버에 저장할 파일이름 (중복 방지용 이름)
        String storeFileName = createStoreFileName(originalFilename); // createStoreFileName을 통해 uuid 이름 생성

        //File(저장시킬 파일 전체 경로)을 생성해서 transferTo를 통해 해당 경로에 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        // 저장한 파일의 원래 이름과, 서버에 저장시킨 이름을 관리하는 객체 UploadFile을 생성 및 반환
        return new UploadFile(originalFilename, storeFileName);
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
