package ru.evendot.rental_order_service.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;
import ru.evendot.rental_order_service.Services.UserDTOService;

@Service
@RequiredArgsConstructor
public class UserDTOServiceImpl implements UserDTOService {
    private final ModelMapper modelMapper = new ModelMapper();
    @Value("${spring.microservice-users.url}")
    private String microserviceUsersURL;

    @Override
    public UserDTO convertToUserDTO(Object object) {
        return modelMapper.map(object, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId){
        RestTemplate restTemplate = new RestTemplate();
        String url = microserviceUsersURL + "/" + userId;

        try {
            ResponseEntity<DataResponse> dataResponse = restTemplate.getForEntity(url, DataResponse.class);
            return convertToUserDTO(dataResponse.getBody());
        }
        catch (HttpClientErrorException.NotFound e){
            System.out.println("User with id: " + userId + " not found: " + e.getMessage() );
            return null;
        }
        catch (RestClientException e){
            System.out.println("Error fetching data: " + e.getMessage());
            return null;
        }

    }
}
