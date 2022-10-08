package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.File.FileResponseDto;
import com.modasby.sparkusbackend.model.File;
import com.modasby.sparkusbackend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") String id) {
        File file = fileStorageService.getFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @PostMapping("/upload")
    public FileResponseDto saveFile(@RequestParam MultipartFile file) {
        return fileStorageService.saveImage(file);
    }
}
