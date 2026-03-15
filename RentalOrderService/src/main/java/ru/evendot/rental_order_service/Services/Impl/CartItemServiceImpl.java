package ru.evendot.rental_order_service.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.evendot.rental_order_service.DTOs.CartItemDTO;
import ru.evendot.rental_order_service.DTOs.Product.ProductDTO;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Exceptions.ResourceNotFoundException;
import ru.evendot.rental_order_service.Models.Cart;
import ru.evendot.rental_order_service.Models.CartItem;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;
import ru.evendot.rental_order_service.Repositories.CartItemRepository;
import ru.evendot.rental_order_service.Repositories.CartRepository;
import ru.evendot.rental_order_service.Services.CartItemService;
import ru.evendot.rental_order_service.Services.CartService;
import ru.evendot.rental_order_service.Services.ProductDTOService;
import ru.evendot.rental_order_service.Services.UserDTOService;
//import ru.evendot.warehouse.model.Product;
//import ru.evendot.warehouse.repository.CartRepository;
//import ru.evendot.warehouse.service.CartService;

/**
 * Реализация элемента корзины покупок
 */
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepo;
    private final CartRepository cartRepo;
//    private final ProductService productService;
    private final ProductDTOService productDTOService;
    private final CartService cartService;
//    private final UserDTOService userDTOService;
    private final ModelMapper modelMapper;



    /**
     * Добавление товара в корзину
     *
     * @param cartId    ID корзины
     * @param productId ID продукта
     * @param quantity  количество единиц продукта в корзине
     */
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
//        Product product = productService.getProductById(productId);
        ProductDTO productDTO = productDTOService.getProductById(productId);

        // Среди всех cartItem'ов ищем тот, который содержит указанный продукт, иначе создаем новый cartItem
        CartItem cartItem = cart.getCartItems()
                .stream()
//                .filter(item -> item.getProductDTO().getId().equals(productId))
                .filter(item -> item.getProductId().equals(productId))
                .findFirst().orElse(new CartItem());

        // Если cartItem, содержащий указанный продукт, не найден, то обрабатываем новый cartItem
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
//            cartItem.setProduct(product);
            cartItem.setProductId(productDTO.getId());
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(productDTO.getPrice());
        } else {
            // Если cartItem, содержащий указанный продукт, найден (товар уже лежит в корзине),
            // то увеличиваем его количество
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepo.save(cartItem);
        // Error Создается второй cartItem
        cartRepo.save(cart);
    }

    /**
     * Удаление товара из корзины
     *
     * @param cartId    ID корзины
     * @param productId ID товара
     */
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepo.save(cart);
    }

    /**
     * Изменение количества единиц товара в корзине
     *
     * @param cartId    ID корзины
     * @param productId ID товара
     */
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        ProductDTO productDTO = productDTOService.getProductById(productId);

        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
//                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
//                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setUnitPrice(item.getUnitPrice());
                    item.setTotalPrice();
                });
        Double totalAmount = cart.getCartItems()
                .stream().mapToDouble(CartItem::getTotalPrice).sum();
        cart.setTotalAmount(totalAmount);
//        cartRepo.updateCart(cart);
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
                .filter(item -> item.getProductId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found!"));
    }

    @Override
    public CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

//    public UserDTO getUserById(Long userId){
//        String url = USER_SERVICE_URL + "/" + userId;
//
//        try {
//            ResponseEntity<DataResponse> dataResponse = restTemplate.getForEntity(url, DataResponse.class);
//            return userDTOService.convertToUserDTO(dataResponse.getBody());
//        }
//        catch (HttpClientErrorException.NotFound e){
//            System.out.println("User with id: " + userId + " not found: " + e.getMessage() );
//        }
//        catch (RestClientException e){
//            System.out.println("Error fetching data: " + e.getMessage());
//            return null;
//        }
//
//    }
}
