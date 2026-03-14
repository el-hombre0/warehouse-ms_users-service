package ru.evendot.rental_order_service.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.evendot.rental_order_service.DTOs.Product.ProductDTO;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;
import ru.evendot.rental_order_service.Services.ProductDTOService;

@Service
@RequiredArgsConstructor
public class ProductDTOServiceImpl implements ProductDTOService {
    private ModelMapper modelMapper;

    @Value("${spring.microservice-products.url}")
    String microserviceProductsURL;

    @Override
    public ProductDTO convertToProductDTO(DataResponse dataResponse) {
        return modelMapper.map(dataResponse.getObject(), ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Long productId) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(microserviceProductsURL + "/" + productId.toString(), String.class);

        return null;
    }
}
