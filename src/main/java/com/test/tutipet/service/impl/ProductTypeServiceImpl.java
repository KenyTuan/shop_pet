package com.test.tutipet.service.impl;

import com.test.tutipet.converter.ProductTypeDtoConverter;
import com.test.tutipet.dtos.productTypes.ProductTypeRes;
import com.test.tutipet.entity.ProductType;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.exception.ResourceNotFoundException;
import com.test.tutipet.repository.ProductTypeRepo;
import com.test.tutipet.service.ProductTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepo productTypeRepo;

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepo.findAll();
    }

    @Override
    public ProductType getProductTypeById(int id) {
        return productTypeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Type Not Found With Id: " + id));
    }
}
