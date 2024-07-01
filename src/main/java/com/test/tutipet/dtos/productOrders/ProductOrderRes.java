package com.test.tutipet.dtos.productOrders;

import com.test.tutipet.dtos.products.ProductRes;

public record ProductOrderRes(
        Long id,
        int quantity,
        ProductRes product
) {
}
