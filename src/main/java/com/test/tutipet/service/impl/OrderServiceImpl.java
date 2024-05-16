package com.test.tutipet.service.impl;

import com.test.tutipet.repository.OrderRepo;
import com.test.tutipet.repository.ProductRepo;
import com.test.tutipet.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    private final ProductRepo productRepo;

}
