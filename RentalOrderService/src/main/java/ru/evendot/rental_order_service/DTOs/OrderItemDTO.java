package ru.evendot.rental_order_service.DTOs;

import lombok.Data;
import ru.evendot.rental_order_service.DTOs.Product.ProductDTO;

@Data
public class OrderItemDTO {
    private Long productId;
    private ProductDTO product;
    private int quantity;
    private Double price;
}
