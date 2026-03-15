package ru.evendot.rental_order_service.Services;

import ru.evendot.rental_order_service.DTOs.Product.ProductDTO;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;

public interface ProductDTOService {
    ProductDTO convertToProductDTO(Object object);

    ProductDTO getProductById(Long productId);

    int getProductInventory(Long productId);

    Integer updateProductInventory(Long productId, Integer inventory );
}
