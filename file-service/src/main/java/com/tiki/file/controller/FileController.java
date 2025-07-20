package com.tiki.file.controller;

import com.tiki.file.dto.ApiResponse;
import com.tiki.file.dto.response.FileResponse;
import com.tiki.file.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileController {

    FileService fileService;

    @PostMapping("/create")
    public ApiResponse<FileResponse> save (@RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam("type") String type) {
        FileResponse fileResponse = fileService.create(multipartFile, type);
        return ApiResponse.<FileResponse>builder()
                .message("save success")
                .result(fileResponse)
                .build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<MediaDto> updateUrl (@RequestParam("file")MultipartFile multipartFile,
//                                               @PathVariable("id") String id,
//                                               @RequestParam("type") String type
//    ) {
//        MediaDto media = mediaService.updateUrl(multipartFile, id, type);
//        return ResponseEntity.ok().body(media);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<MediaDto> updateStatus (
//            @PathVariable("id") String id,
//            @RequestParam("status") boolean status) {
//        MediaDto media = mediaService.updateStatus(id, status);
//        return ResponseEntity.ok().body(media);
//    }

    @GetMapping("/{id}")
    public ApiResponse<String> getUrlById(@PathVariable("id") String id) {
        String url = fileService.getUrlById(id);
        return ApiResponse.<String>builder().result(url).build();
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete (@PathVariable("id") String id) {
//        mediaService.deleteFile(id);
//        return ResponseEntity.noContent().build();
//    }
}
