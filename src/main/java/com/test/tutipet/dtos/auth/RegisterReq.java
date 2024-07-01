package com.test.tutipet.dtos.auth;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class RegisterReq {

    @Email(message = MessageException.INVALID_EMAIL,regexp = "^(.+)@(\\S+)$")
    private String email;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = MessageException.INVALID_PASSWORD
    )
    private String password;

    @Size(min = 5, max = 255, message = MessageException.REQUIRED_FULL_NAME)
    private String fullName;

    private boolean gender;
}
