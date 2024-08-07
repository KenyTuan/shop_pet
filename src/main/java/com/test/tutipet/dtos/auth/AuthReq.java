package com.test.tutipet.dtos.auth;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class AuthReq {

    @Email(message = MessageException.INVALID_EMAIL,regexp = "^(.+)@(\\S+)$")
    private String email;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = MessageException.INVALID_PASSWORD
    )
    private String password;

}
