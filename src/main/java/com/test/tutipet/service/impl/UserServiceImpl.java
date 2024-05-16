package com.test.tutipet.service.impl;

import com.test.tutipet.repository.UserRepo;
import com.test.tutipet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

}
