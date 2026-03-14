package ru.evendot.products_service.Models.Responses.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.evendot.products_service.Models.Product;

@Data
@AllArgsConstructor
public class DataResponseProductFull {
    private Product product;
}
