package com.tiki.file.service;


import com.cloudinary.Cloudinary;
import com.tiki.file.dto.FileType;
import com.tiki.file.dto.response.FileResponse;
import com.tiki.file.entity.Files;
import com.tiki.file.repository.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileService {

    Cloudinary cloudinary;

    FileRepository fileRepository;

    String Video_Type = "VIDEO";

    String Image_Type = "IMAGE";

    public FileResponse create(MultipartFile multipartFile, String type) {
        HashMap<String, String> map = new HashMap<>();
        String fileId = UUID.randomUUID().toString();
        map.put("public_id", fileId);
        map.put("resource_type", "auto");
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), map);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        String url = uploadResult
                .get("url")
                .toString();
        Files media = Files.builder()
                .id(fileId)
                .status(false)
                .url(url)
                .type(FileType.valueOf(type))
                .build();
        if (type.equals(Video_Type)) {
            String duration = uploadResult.get("duration").toString();
            media.setDuration(duration);
        }

        fileRepository.save(media);

        return FileResponse.fromModel(media);
    }


    public String getUrlById(String uuid) {
        return fileRepository.findById(uuid).orElseThrow(RuntimeException::new).getUrl();
    }
}
