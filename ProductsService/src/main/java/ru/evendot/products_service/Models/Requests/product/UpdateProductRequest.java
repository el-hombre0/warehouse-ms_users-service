package ru.evendot.products_service.Models.Requests.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductRequest {
    private String title;
    private Long article;
    private String description;
    private Double price;
    private Boolean inStock;
    private Integer sale;
    private int inventory;
//    private Category category;
}
