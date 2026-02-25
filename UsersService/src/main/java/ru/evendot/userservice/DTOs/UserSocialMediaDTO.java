package ru.evendot.userservice.DTOs;

import lombok.Data;

@Data
public class UserSocialMediaDTO {
    private Long id;
    private String vk;
    private String tg;
}
