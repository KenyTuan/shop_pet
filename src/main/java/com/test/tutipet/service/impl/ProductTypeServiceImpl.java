package com.test.tutipet.service.impl;

import com.test.tutipet.entity.ProductType;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.ProductTypeRepository;
import com.test.tutipet.service.ProductTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType getProductTypeById(int id) {
        return productTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Type Not Found With Id: " + id));
    }
}
