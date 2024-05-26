package com.test.tutipet.service;

import com.test.tutipet.dtos.auth.AuthReq;
import com.test.tutipet.dtos.auth.AuthRes;
import com.test.tutipet.dtos.auth.RegisterReq;

public interface AuthService {


    AuthRes register(RegisterReq registerReq);

    AuthRes authenticate(AuthReq authReq);
}
