package by.innowise.imageservice.service.impl;

import by.innowise.imageservice.dto.ImageDto;
import by.innowise.imageservice.exception.ImageNotFoundException;
import by.innowise.imageservice.exception.InvalidImageFormatException;
import by.innowise.imageservice.mapper.ImageMapper;
import by.innowise.imageservice.model.ImageMetadata;
import by.innowise.imageservice.service.interfaces.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );
    private final GridFsTemplate gridFsTemplate;
    private final ImageMapper imageMapper;

    @Override
    public ImageDto saveImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new InvalidImageFormatException(contentType);
        }
        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        ImageMetadata imageMetadata = ImageMetadata.builder()
                .id(fileId.toString())
                .fileName(file.getOriginalFilename())
                .uploadDate(new Date())
                .size(file.getSize())
                .build();
        return imageMapper.toDto(imageMetadata);
    }

    @Override
    public Page<ImageDto> getAllImages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Query query = new Query().with(pageable);

        List<ImageMetadata> metadataList = new ArrayList<>();
        List<GridFSFile> files = gridFsTemplate.find(query).into(new ArrayList<>());

        files.forEach(gridFSFile -> {
            ImageMetadata metadata = ImageMetadata.builder()
                    .id(gridFSFile.getObjectId().toString())
                    .fileName(gridFSFile.getFilename())
                    .uploadDate(gridFSFile.getUploadDate())
                    .size(gridFSFile.getLength())
                    .build();
            metadataList.add(metadata);
        });
        List<ImageDto> listDto = imageMapper.toListDto(metadataList);

        long total = gridFsTemplate.find(new Query()).into(new ArrayList<>()).size();

        return new PageImpl<>(listDto, pageable, total);
    }

    @Override
    public GridFsResource getImageResource(String id) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (gridFSFile == null) {
            throw new ImageNotFoundException(id);
        }
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        if (!resource.exists()) {
            throw new ImageNotFoundException(id);
        }
        return resource;
    }

    @Override
    public byte[] createImage(GridFsResource resource) throws IOException {
        return IOUtils.toByteArray(resource.getInputStream());
    }
}
