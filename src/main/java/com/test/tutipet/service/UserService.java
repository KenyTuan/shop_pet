package com.test.tutipet.service;

import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.users.UserReq;
import com.test.tutipet.dtos.users.UserRes;

public interface UserService {

    PageRes<UserRes> getAllUser(String keySearch, int page, int size, String sortBy, String sortDir);

    UserRes getUserById(long id);

    UserRes updateUser(long id, UserReq req);

    void deleteUser(long id);

    UserRes createUser(UserReq userReq);
}
