package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.dto.File.FileResponseDto;
import com.modasby.sparkusbackend.model.File;
import com.modasby.sparkusbackend.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;

@Service
public class FileStorageService {

    private final FileRepository fileRepository;

    @Autowired
    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileResponseDto saveImage(MultipartFile file) {

        try {
            File image = new File();

            image.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            image.setContentType(file.getContentType());
            image.setSize(file.getSize());
            image.setData(file.getBytes());

            File savedFile = fileRepository.save(image);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/")
                    .path(savedFile.getId())
                    .toUriString();

            return new FileResponseDto(
                    savedFile.getId(),
                    savedFile.getName(),
                    fileDownloadUri
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public File getFile(String id) {

        return fileRepository.findById(id).get();
    }
}
