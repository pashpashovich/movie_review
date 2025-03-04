package by.innowise.imageservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ImageDto {
    private String id;
    private String fileName;
    private Date uploadDate;
    private long size;
}
