package ru.evendot.userservice.Models.Requests;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import ru.evendot.userservice.Models.Address;
import ru.evendot.userservice.Models.UserSocialMedia;


@Data
public class CreateUserRequest {

    @NaturalId
    private String email;

    private String firstname;
    private String lastname;
    private String middlename;
    private String phoneNumber;
    private UserSocialMedia socialMedia;
    private String password;
    private Address address;
}
