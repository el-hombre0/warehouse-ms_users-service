package ru.evendot.rental_order_service.Models.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.evendot.rental_order_service.Models.Order;

import java.util.List;

@Data
@AllArgsConstructor
public class DataResponseOrderList {
    private List<Order> orders;
}
