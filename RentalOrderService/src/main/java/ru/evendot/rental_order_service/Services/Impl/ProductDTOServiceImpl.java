package ru.evendot.rental_order_service.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.evendot.rental_order_service.DTOs.Product.ProductDTO;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;
import ru.evendot.rental_order_service.Services.ProductDTOService;

@Service
@RequiredArgsConstructor
public class ProductDTOServiceImpl implements ProductDTOService {
    private final ModelMapper modelMapper = new ModelMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.microservice-products.url}")
    String microserviceProductsURL;

    @Override
    public ProductDTO convertToProductDTO(Object object) {
        return modelMapper.map(object, ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        ResponseEntity<DataResponse> response = restTemplate.getForEntity(microserviceProductsURL + "/product/" + productId.toString(), DataResponse.class);
        return convertToProductDTO(response.getBody().getObject());
    }

    @Override
    public int getProductInventory(Long productId){
        ResponseEntity<DataResponse> response = restTemplate.getForEntity(microserviceProductsURL + "/product/" + productId.toString() + "/inventory", DataResponse.class);
        return Integer.parseInt(response.getBody().getObject().toString());
    }

    @Override
    public Integer updateProductInventory(Long productId, Integer inventory) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(inventory.toString(), headers);

        ResponseEntity<DataResponse> response = restTemplate.postForEntity(microserviceProductsURL + "/product/" + productId.toString() + "/inventory", request, DataResponse.class);
        return Integer.parseInt(response.getBody().getObject().toString());
    }
}
