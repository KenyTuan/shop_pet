package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiEndpoints.PREFIX)
public class UserController {

    private final UserService userService;


}
