package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PutMapping(ApiEndpoints.CART_V1)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartRes updateProductCartByToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestBody @Valid ProductCartReq productCartReq) {
        return cartService.addOrReplaceProductCartByToken(token, productCartReq);
    }

    @PostMapping(ApiEndpoints.CART_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public CartRes addProductCartByToken(
            @RequestBody @Valid ProductCartReq req,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        return cartService.addProductCartByToken(token, req);
    }

    @GetMapping(ApiEndpoints.CART_V1)
    public CartRes getProductCartsByToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        return cartService.getProductCartsByToken(token);
    }

    @DeleteMapping(ApiEndpoints.CART_V1)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductCartFromCart(
            @RequestParam(value = "productId") long productId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        cartService.deleteProductCartByToken(token, productId);
    }

}
