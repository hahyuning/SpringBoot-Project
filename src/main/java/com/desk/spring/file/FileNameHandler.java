package com.desk.spring.file;

import com.desk.spring.file.dto.PhotoRequestDto;
import com.desk.spring.domain.Photo;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FileNameHandler {

    public List<Photo> parseFile(List<MultipartFile> multipartFiles) throws Exception {
        List<Photo> files = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absPath = new File("").getAbsolutePath() + File.separator + File.separator;
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                if (!wasSuccessful) {

                }
            }

            for (MultipartFile multipartFile : multipartFiles) {

                String origFileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if (contentType.contains("image/jpeg")) {
                        origFileExtension = ".jpg";
                    }
                    else if (contentType.contains("image/png")) {
                        origFileExtension = ".png";
                    }
                    else break;
                }

                String new_file_name = System.nanoTime() + origFileExtension;
                PhotoRequestDto photoDto = PhotoRequestDto.builder()
                        .origName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                Photo photo = new Photo(photoDto.getOrigName(), photoDto.getFilePath(), photoDto.getFileSize());

                files.add(photo);

                file = new File(absPath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }

        }
        return files;
    }
}
