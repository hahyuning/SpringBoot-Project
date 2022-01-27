package com.desk.spring.util;

import com.desk.spring.web.dto.PhotoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUtils {

    private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

    private final String uploadPath = Paths.get("D:", "develop", "upload", today).toString();

    private final String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public List<PhotoDto> uploadPhotos(MultipartFile[] photos, Long boardId) {
        if (photos == null) {
            log.debug("Photos is Empty.");
            return Collections.emptyList();
        }

        List<PhotoDto> result = new ArrayList<>();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            log.debug("Folder not exist");
            dir.mkdirs();
        }

        for (MultipartFile photo : photos) {
            try {
                final String extension = org.apache.logging.log4j.core.util.FileUtils.getFileExtension((File) photo);
                final String saveName = getRandomString() + "." + extension;

                File target = new File(uploadPath, saveName);
                photo.transferTo(target);

                PhotoDto photoDto = PhotoDto
                        .builder()
                        .boardId(boardId)
                        .origFileName(photo.getOriginalFilename())
                        .saveName(saveName)
                        .fileSize(photo.getSize())
                        .build();

                result.add(photoDto);

            } catch (Exception e) {
                log.debug("Photo is not changed to Dto");
                e.printStackTrace();
            }
        }
        return result;
    }
}
