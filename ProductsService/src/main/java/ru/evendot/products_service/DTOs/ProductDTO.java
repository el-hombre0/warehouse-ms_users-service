package ru.evendot.products_service.DTOs;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private Long article;
    private String description;
    private Double price;
    private Integer sale;
}
