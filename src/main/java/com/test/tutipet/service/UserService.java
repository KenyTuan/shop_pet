package com.test.tutipet.service;

import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.users.CreateUserReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;

import java.util.List;

public interface UserService {
    List<UserRes> getAllUsers();

    PageRes<UserRes> searchAllUsers(String keySearch, int page, int size, String sortBy, String sortDir);

    UserRes getUserById(long id);

    UserRes updateUser(long id, UpdateUserReq req);

    void deleteUser(long id);

    UserRes createUser(CreateUserReq userReq);
}
