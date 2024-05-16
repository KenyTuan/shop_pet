package com.test.tutipet.service.impl;

import com.test.tutipet.repository.CartRepo;
import com.test.tutipet.repository.ProductCartRepo;
import com.test.tutipet.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    private final ProductCartRepo productCartRepo;

}
