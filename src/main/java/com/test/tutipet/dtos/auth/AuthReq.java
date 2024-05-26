package com.test.tutipet.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class AuthReq {

    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

}
