package by.innowise.imageservice.controller;

import by.innowise.imageservice.dto.ImageDto;
import by.innowise.imageservice.exception.ImageNotFoundException;
import by.innowise.imageservice.service.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleImageNotFoundException(ImageNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        ImageDto imageDto = imageService.saveImage(file);
        return ResponseEntity.ok(imageDto);
    }

    @GetMapping
    public ResponseEntity<Page<ImageDto>> getAllImages(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<ImageDto> images = imageService.getAllImages(page, size);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("id") String id) throws IOException {
        GridFsResource resource = imageService.getImageResource(id);
        byte[] imageData = imageService.createImage(resource);
        HttpHeaders headers = new HttpHeaders();
        String contentType = resource.getContentType();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(imageData.length);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}