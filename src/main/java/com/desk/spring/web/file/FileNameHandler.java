package com.desk.spring.web.file;

import com.desk.spring.domain.Photo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FileNameHandler {

    public List<Photo> parseFileInfo(List<MultipartFile> multipartFiles) throws Exception {
        List<Photo> fileList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String currentDate = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "images" + File.separator + currentDate;
            File file = new File(path);

            boolean wasSuccessful = false;
            if (!file.exists()) {
                wasSuccessful = file.mkdirs();
            }

            if (!wasSuccessful) {
                log.info("was not successful");
            }

            for (MultipartFile multipartFile : multipartFiles) {
                // 파일의 확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String newFileName = System.nanoTime() + originalFileExtension;

                // 파일 DTO 생성
                PhotoDto photoDto = PhotoDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + newFileName)
                        .fileSize(multipartFile.getSize())
                        .build();

                // 파일 DTO 이용하여 Photo 엔티티 생성
                Photo photo = new Photo(
                        photoDto.getOrigFileName(),
                        photoDto.getFilePath(),
                        photoDto.getFileSize()
                );

                // 생성 후 리스트에 추가
                fileList.add(photo);

                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + newFileName);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }

        }
        return fileList;
    }
}
