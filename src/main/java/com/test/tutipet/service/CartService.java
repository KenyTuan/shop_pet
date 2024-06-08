package com.test.tutipet.service;

import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.dtos.productCarts.ProductCartRes;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.ProductCart;

import java.util.Set;

public interface CartService {

    CartRes getById(long id);

    Cart getCartByUserId(long userId);

    CartRes getProductCartsByUserId(long userId);

    CartRes addProductCartByUserId(long userId, ProductCartReq req);

    CartRes addOrReplaceProductCartByUserId(long userId, ProductCartReq req);

    void deleteProductCartFromCart(long userId, long productId);

}
