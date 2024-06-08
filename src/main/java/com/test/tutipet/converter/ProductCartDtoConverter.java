package com.test.tutipet.converter;

import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.dtos.productCarts.ProductCartRes;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.ProductCart;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductCartDtoConverter {


    public static ProductCart toEntity(ProductCartReq req) {
        return ProductCart
                .builder()
                .product(Product.builder().id(req.getProductId()).build())
                .quantity(req.getQuantity())
                .totalProduct(req.getTotalProduct())
                .build();
    }

    public static ProductCartRes toResponse(ProductCart productCart) {
        return new ProductCartRes(
                productCart.getId(),
                productCart.getQuantity(),
                productCart.getTotalProduct(),
                ProductDtoConverter.toResponse(productCart.getProduct())
        );
    }

    public static Set<ProductCartRes> toModelList(Set<ProductCart> productCarts) {
        return productCarts
                .stream()
                .map(ProductCartDtoConverter::toResponse)
                .collect(Collectors.toSet());
    }
}
