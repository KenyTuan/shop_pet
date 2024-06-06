package com.test.tutipet.service;

import com.test.tutipet.dtos.productTypes.ProductTypeRes;
import com.test.tutipet.entity.ProductType;

import java.util.List;

public interface ProductTypeService {
    List<ProductType> getAllProductTypes();

    ProductType getProductTypeById(int id);
}
