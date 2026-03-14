package ru.evendot.rental_order_service.Services;

import ru.evendot.rental_order_service.DTOs.Product.ProductDTO;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;

public interface ProductDTOService {
    ProductDTO convertToProductDTO(DataResponse dataResponse);

    ProductDTO getProductById(Long productId);
}
