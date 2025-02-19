package by.innowise.imageservice.service.interfaces;

import by.innowise.imageservice.dto.ImageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageDto saveImage(MultipartFile file) throws IOException;

    Page<ImageDto> getAllImages(int page, int size);

    GridFsResource getImageResource(String id) throws IOException;

    byte[] createImage(GridFsResource resource) throws IOException;
}
