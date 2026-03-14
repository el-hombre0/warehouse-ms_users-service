package ru.evendot.rental_order_service.Services;


import ru.evendot.rental_order_service.DTOs.CartItemDTO;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Models.CartItem;

public interface CartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);

    CartItemDTO convertToCartItemDTO(CartItem cartItem);

    UserDTO getUserById(Long userId);
}
