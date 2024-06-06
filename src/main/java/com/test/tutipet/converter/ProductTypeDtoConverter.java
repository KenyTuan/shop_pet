package com.test.tutipet.converter;

import com.test.tutipet.dtos.productTypes.ProductTypeRes;
import com.test.tutipet.entity.ProductType;

public class ProductTypeDtoConverter {

    public static ProductTypeRes toResponse(ProductType type){
        return new ProductTypeRes(
                type.getId(),
                type.getName(),
                type.getPetTypes(),
                type.getCreatedBy(),
                type.getCreatedAt(),
                type.getUpdatedBy(),
                type.getUpdatedAt(),
                type.getObjectStatus(),
                type.getProducts()
        );
    }


}
