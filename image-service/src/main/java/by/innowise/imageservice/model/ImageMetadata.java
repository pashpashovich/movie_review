package by.innowise.imageservice.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ImageMetadata {
    private String id;
    private String fileName;
    private Date uploadDate;
    private long size;
}

