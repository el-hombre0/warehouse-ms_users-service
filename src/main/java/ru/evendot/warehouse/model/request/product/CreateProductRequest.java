package ru.evendot.warehouse.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.evendot.warehouse.model.Image;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String title;
    private Long article;
    private String description;
    private Double price;
    private List<Image> images;
    private Boolean inStock;
    private Integer sale;
    private int inventory;
//    private Category category;
}
