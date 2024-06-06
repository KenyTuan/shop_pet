package com.test.tutipet.dtos.products;

import com.test.tutipet.entity.ProductType;
import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ProductRes(
        Long id,
        String name,
        double price,
        String description,
        String info,
        String image,
        EnableStatus status,
        ProductType type
) implements Serializable {
}
