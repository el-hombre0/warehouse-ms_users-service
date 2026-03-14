package ru.evendot.rental_order_service.Models.Requests;

import lombok.Data;
import ru.evendot.rental_order_service.DTOs.OrderDTO;

import java.util.List;

@Data
public class OrderForm {
    private List<OrderDTO> productOrders;

}
