package ru.evendot.products_service.DTOs;

import lombok.Data;


@Data
public class ImageDTO {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
