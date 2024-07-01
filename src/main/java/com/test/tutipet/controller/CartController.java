package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PutMapping(ApiEndpoints.CART_V1)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartRes updateProductCartByUserId(
            @RequestParam(value = "userId") long userId,
            @RequestBody @Valid ProductCartReq productCartReq) {
        return cartService.addOrReplaceProductCartByUserId(userId, productCartReq);
    }

    @PostMapping(ApiEndpoints.CART_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public CartRes addProductCartByUserId(
            @RequestParam(value = "userId") long userId,
            @RequestBody @Valid ProductCartReq req
    ) {
        return cartService.addProductCartByUserId(userId, req);
    }

    @GetMapping(ApiEndpoints.CART_V1)
    public CartRes getProductCartsByUserId(
            @RequestParam(value = "userId") long userId
    ) {
        return cartService.getProductCartsByUserId(userId);
    }

    @DeleteMapping(ApiEndpoints.CART_V1)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductCartFromCart(
            @RequestParam(value = "userId") long userId,
            @RequestParam(value = "productId") long productId
    ) {
        cartService.deleteProductCartFromCart(userId, productId);
    }

}
