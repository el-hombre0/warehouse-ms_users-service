package ru.evendot.rental_order_service.Models.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.evendot.rental_order_service.Models.Order;

@Data
@AllArgsConstructor
public class DataResponseOrderFull {
    private Order order;
}
