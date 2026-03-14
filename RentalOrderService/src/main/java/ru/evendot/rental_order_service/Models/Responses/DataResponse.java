package ru.evendot.rental_order_service.Models.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResponse {
    private String message;
    private Object object;
}
