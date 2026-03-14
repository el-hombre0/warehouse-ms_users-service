package ru.evendot.rental_order_service.Services;


import ru.evendot.rental_order_service.DTOs.OrderDTO;
import ru.evendot.rental_order_service.Models.Order;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrders();

    OrderDTO getOrder(Long id);

    List<OrderDTO> getUserOrders(Long userId);

    Order placeOrder(Long userId);

    OrderDTO convertToOrderDTO(Order order);

//    void deleteById(Long id);

//    DataResponseOrder updateOrder(CreateOrder order);
}
