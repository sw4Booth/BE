package com.sw4.controller;

import com.sw4.domain.photo.Photo;
import com.sw4.domain.photo.PhotoService;
import com.sw4.dto.PhotoResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController implements PhotoControllerDocs {

    private final PhotoService photoService;

    @PostMapping("/upload")
    public PhotoResponse upload(@RequestParam MultipartFile file) {
        Photo p = photoService.upload(file);
        return new PhotoResponse(p.getId(), p.getImageUrl());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        photoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<PhotoResponse> getAllPhotos() {
        return photoService.findAll().stream()
                .map(p -> new PhotoResponse(p.getId(), p.getImageUrl()))
                .collect(Collectors.toList());
    }

}
