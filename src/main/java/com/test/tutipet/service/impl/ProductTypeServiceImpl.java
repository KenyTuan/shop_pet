package com.test.tutipet.service.impl;

import com.test.tutipet.repository.ProductTypeRepo;
import com.test.tutipet.service.ProductTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepo productTypeRepo;

}
