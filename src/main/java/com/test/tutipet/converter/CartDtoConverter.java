package com.test.tutipet.converter;

import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartRes;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.ProductCart;

import java.util.stream.Collectors;

public class CartDtoConverter {

    public static CartRes toResponse(Cart cart) {
        return new CartRes(
                cart.getId(),
                ProductCartDtoConverter.toModelList(cart.getProductCarts())
        );
    }

}
