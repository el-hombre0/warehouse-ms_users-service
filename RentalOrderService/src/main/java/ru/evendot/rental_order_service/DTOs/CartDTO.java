package ru.evendot.rental_order_service.DTOs;


import lombok.Data;

import java.util.Set;

@Data
public class CartDTO {
    private Long id;
    private Double totalAmount;
    private Set<CartItemDTO> cartItems;
}
