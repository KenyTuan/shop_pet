package com.test.tutipet.service;

import com.test.tutipet.dtos.auth.*;
import com.test.tutipet.dtos.users.ChangePasswordReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;

public interface AuthService {


    AuthRes register(RegisterReq registerReq);

    AuthRes authenticate(AuthReq authReq);

    void requestForget(String email);

    void forgetPassword(ForgotReq forgotReq);

    void changePasswordByToken(String token, ChangePasswordReq changePasswordReq);

    UserRes updateProfileByToken(String token, UpdateUserReq updateUserReq);
}
