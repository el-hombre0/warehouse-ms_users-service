package ru.evendot.rental_order_service.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Exceptions.ResourceNotFoundException;
import ru.evendot.rental_order_service.Models.Cart;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;
import ru.evendot.rental_order_service.Services.CartItemService;
import ru.evendot.rental_order_service.Services.CartService;
import ru.evendot.rental_order_service.Services.UserDTOService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;
//    private final UserService userService;
    private final UserDTOService userDTOService;
    /**
     * Добавление товара в корзину
     *
     * @param productId ID товара
     * @param quantity  Кол-во товаров
     * @return Сообщение о добавлении товара в корзину или об ошибке поиска корзины
     */
    @PostMapping("/add")
    public ResponseEntity<DataResponse> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            //TODO User 1 For testing purposes
//            User user = userService.getUserById(1L);
            UserDTO userDTO = userDTOService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(userDTO);
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new DataResponse("Item added to cart!", cartService.convertToCartDTO(cart)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataResponse(
                    "Error while adding item to cart!", e.getMessage()));
        }
    }

    /**
     * Удаление товара из корзины
     *
     * @param cartId    ID корзины
     * @param productId ID товара
     * @return Сообщение об успешности удаления товара из корзины или сообщение об ошибке поиска корзины
     */
    @DeleteMapping("/remove")
    public ResponseEntity<DataResponse> removeItemFromCart(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new DataResponse("Item removed from cart!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataResponse(
                    "Cart or product not found!", e.getMessage()));
        }
    }

    /**
     * Изменение кол-ва единиц товара
     *
     * @param cartId    ID корзины
     * @param productId ID товара
     * @param quantity  Кол-во единиц товара
     * @return Сообщение об успешности изменения кол-ва единиц товара или сообщение об ошибке поиска корзины
     */
    @PatchMapping("/updateQuantity")
    public ResponseEntity<DataResponse> updateItemQuantity(@RequestParam Long cartId,
                                                           @RequestParam Long productId, @RequestParam int quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok().body(new DataResponse(
                    "Item quantity updated successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataResponse(
                    "Cart or product not found!", e.getMessage()));
        }
    }

}
