package com.test.tutipet.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class RegisterReq {

    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

    @Size(min = 5, max = 255, message = "Full Name length ranges from 5 to 255 characters!")
    private String fullName;

    private boolean gender;
}
