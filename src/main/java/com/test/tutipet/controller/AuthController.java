package com.test.tutipet.controller;


import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.auth.*;
import com.test.tutipet.dtos.users.ChangePasswordReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiEndpoints.PREFIX)
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiEndpoints.AUTH_V1 + "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthRes register(@Valid @RequestBody RegisterReq registerReq) {
        return authService.register(registerReq);
    }

    @PostMapping(ApiEndpoints.AUTH_V1 + "/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthRes authenticate(@Valid @RequestBody AuthReq authReq) {
        return authService.authenticate(authReq);
    }

    @PostMapping(ApiEndpoints.AUTH_V1 + "/forget-password/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void generatePasswordRestToken(@RequestParam(name = "email") String email) {
        authService.requestForget(email);
    }

    @PatchMapping(ApiEndpoints.AUTH_V1 + "/forget-password")
    public void confirmPasswordReset(@RequestBody @Valid ForgotReq forgotReq) {
        authService.forgetPassword(forgotReq);
    }

    @PatchMapping(ApiEndpoints.AUTH_V1 + "/change-password")
    public void changePasswordByToken(@RequestBody @Valid ChangePasswordReq changePasswordReq,
                                      @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        authService.changePasswordByToken(token,changePasswordReq);
    }

    @PutMapping(ApiEndpoints.AUTH_V1 + "/update-profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserRes updateProfileByToken(@RequestBody @Valid UpdateUserReq updateUserReq,
                                        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token ){
        return authService.updateProfileByToken(token,updateUserReq);
    }
}
