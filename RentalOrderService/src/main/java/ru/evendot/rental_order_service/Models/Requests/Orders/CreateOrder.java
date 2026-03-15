package ru.evendot.rental_order_service.Models.Requests.Orders;

import lombok.Data;
import ru.evendot.rental_order_service.DTOs.OrderDTO;
//import ru.evendot.warehouse.dto.OrderDTO;
//import ru.evendot.warehouse.model.*;

import java.util.List;

@Data
public class CreateOrder {
    private Double cost;
    private Long paymentMethod;
    private List<OrderDTO> productOrders;
    private Long userId;
    private String comment;
    private String addressId;
}
