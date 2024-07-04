package com.test.tutipet.service;

import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.Product;

public interface CartService {

    CartRes getById(long id);

    Cart getCartByToken(String token);

    CartRes getProductCartsByToken(String token);

    CartRes addProductCartByToken(String token, ProductCartReq req);

    CartRes addOrReplaceProductCartByToken(String token, ProductCartReq req);

    void updateCartsByProductId(Product product,long productId);

    void deleteProductCartByToken(String token, long productId);

}
