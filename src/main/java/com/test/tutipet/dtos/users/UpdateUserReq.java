package com.test.tutipet.dtos.users;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.enums.GenderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserReq {

    @Size(min = 5, max = 255, message = MessageException.REQUIRED_FULL_NAME)
    private String fullName;

    @NotNull(message = MessageException.REQUIRED_GENDER)
    private GenderType gender;

}
