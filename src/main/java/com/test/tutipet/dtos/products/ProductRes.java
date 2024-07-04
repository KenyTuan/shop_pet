package com.test.tutipet.dtos.products;

import com.test.tutipet.entity.ProductType;
import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

public record ProductRes(
        Long id,
        String name,
        String brand,
        String origin,
        double price,
        String description,
        String info,
        String image,
        ZonedDateTime timeExp,
        EnableStatus status,
        ProductType type,
        Promotion promotions
) implements Serializable {
}
