package com.test.tutipet.dtos.carts;

import com.test.tutipet.dtos.productCarts.ProductCartRes;
import com.test.tutipet.dtos.users.UserRes;

import java.util.Set;

public record CartRes(
        Long id,
        Set<ProductCartRes> productCart
) {
}
