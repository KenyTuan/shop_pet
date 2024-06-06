package com.test.tutipet.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserReq {

    @Size(min = 5, max = 255, message = "Name length ranges from 5 to 255 characters")
    private String fullName;

    private boolean gender;

    @Email(message = "Invalid Email!")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;


//    @NotNull(message = "Role is required.")
//    private Role role;


}
