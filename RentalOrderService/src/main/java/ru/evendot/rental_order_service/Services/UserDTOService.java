package ru.evendot.rental_order_service.Services;

import ru.evendot.rental_order_service.DTOs.User.UserDTO;
import ru.evendot.rental_order_service.Models.Responses.DataResponse;

public interface UserDTOService {
    UserDTO convertToUserDTO(DataResponse dataResponse);

    UserDTO getUserById(Long userId);
}
