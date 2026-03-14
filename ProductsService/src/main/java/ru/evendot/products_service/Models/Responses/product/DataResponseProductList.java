package ru.evendot.products_service.Models.Responses.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.evendot.products_service.Models.Product;

import java.util.List;

@Data
@AllArgsConstructor
public class DataResponseProductList {
    private List<Product> products;
}
