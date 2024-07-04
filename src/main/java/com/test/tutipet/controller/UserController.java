package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.users.CreateUserReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiEndpoints.PREFIX)
public class UserController {

    private final UserService userService;

    @GetMapping(ApiEndpoints.USER_V1)
    public List<UserRes> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(ApiEndpoints.USER_V1 + "/search")
    public PageRes<UserRes> searchAllUsers(
            @RequestParam(value = "keySearch", defaultValue = "") String keySearch,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "fullName") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return userService.searchAllUsers(keySearch,page,size,sortBy,sortDir);
    }

    @GetMapping(ApiEndpoints.USER_V1 + "/{id}")
    public UserRes getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping(ApiEndpoints.USER_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public UserRes createUser(@RequestBody @Valid CreateUserReq req){
        return userService.createUser(req);
    }

    @PutMapping(ApiEndpoints.USER_V1 + "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserRes putUser(@PathVariable long id, @RequestBody @Valid UpdateUserReq req){
        return userService.updateUser(id,req);
    }

    @DeleteMapping(ApiEndpoints.USER_V1 + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

}
