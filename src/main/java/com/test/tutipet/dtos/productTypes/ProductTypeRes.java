package com.test.tutipet.dtos.productTypes;

import com.test.tutipet.entity.Product;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.PetType;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductTypeRes(
        Integer id,
        String name,
        PetType petType,
        Long createdBy,
        LocalDateTime createdAt,
        Long updatedBy,
        LocalDateTime updatedAt,
        ObjectStatus objectStatus,
        Set<Product> product
) {
}
