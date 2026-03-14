package ru.evendot.rental_order_service.Services;


import ru.evendot.rental_order_service.DTOs.CartDTO;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Models.Cart;

public interface CartService {
    Cart getCart(Long id);

    Cart getCartByUserId(Long userId);

    void clearCart(Long id);

    Double getTotalPrice(Long id);

    Cart initializeNewCart(UserDTO userDTO);

    CartDTO convertToCartDTO(Cart cart);
}
