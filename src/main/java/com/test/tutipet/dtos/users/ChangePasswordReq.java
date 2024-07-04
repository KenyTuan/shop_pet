package com.test.tutipet.dtos.users;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class ChangePasswordReq {

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = MessageException.INVALID_PASSWORD_OLD
    )
    private String oldPassword;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = MessageException.INVALID_PASSWORD_NEW
    )
    private String newPassword;

}
