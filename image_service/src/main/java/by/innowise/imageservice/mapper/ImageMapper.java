package by.innowise.imageservice.mapper;

import by.innowise.imageservice.dto.ImageDto;
import by.innowise.imageservice.model.ImageMetadata;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(ImageMetadata imageMetadata);
    List<ImageDto> toListDto(List<ImageMetadata> imageMetadata);
}
