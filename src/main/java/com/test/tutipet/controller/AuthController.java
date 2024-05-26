package com.test.tutipet.controller;


import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.auth.AuthReq;
import com.test.tutipet.dtos.auth.AuthRes;
import com.test.tutipet.dtos.auth.RegisterReq;
import com.test.tutipet.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


}
