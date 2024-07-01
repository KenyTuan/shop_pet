package com.test.tutipet.service;

import com.test.tutipet.dtos.auth.*;

public interface AuthService {


    AuthRes register(RegisterReq registerReq);

    AuthRes authenticate(AuthReq authReq);

    void requestForget(String email);

    void forgetPassword(RequestForgot requestForgot);

    void changePasswordByToken(String token, ChangePasswordReq changePasswordReq);
}
