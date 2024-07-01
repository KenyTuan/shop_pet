package com.test.tutipet.dtos.users;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserReq {

    @Size(min = 5, max = 255, message = MessageException.REQUIRED_FULL_NAME)
    private String fullName;

    private boolean gender;

}
