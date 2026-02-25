package ru.evendot.userservice.Models.Requests;

import lombok.Data;
import ru.evendot.userservice.Models.Address;
import ru.evendot.userservice.Models.UserSocialMedia;


@Data
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private String middlename;
    private UserSocialMedia socialMedia;
    private Address address;
}
