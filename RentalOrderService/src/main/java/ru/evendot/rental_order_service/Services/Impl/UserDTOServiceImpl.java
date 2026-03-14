package ru.evendot.rental_order_service.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;
    private RestTemplate restTemplate;
    private final String USER_SERVICE_URL = "http://localhost:8080/api/v1/users";


    @Override
    public UserDTO convertToUserDTO(DataResponse dataResponse) {
        return modelMapper.map(dataResponse.getObject(), UserDTO.class);
    }

    public UserDTO getUserById(Long userId){
        String url = USER_SERVICE_URL + "/" + userId;

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
