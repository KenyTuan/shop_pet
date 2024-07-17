package com.test.tutipet.dtos.products;

import com.test.tutipet.entity.ProductType;
import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.EnableStatus;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductRes(
        Long id,
        String name,
        String brand,
        String origin,
        double price,
        BigDecimal discount,
        String description,
        String image,
        EnableStatus status,
        ProductType type,
        Promotion promotions
) implements Serializable {
}
