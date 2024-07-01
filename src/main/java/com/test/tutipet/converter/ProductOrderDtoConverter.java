package com.test.tutipet.converter;

import com.test.tutipet.dtos.productOrders.ProductOrderReq;
import com.test.tutipet.dtos.productOrders.ProductOrderRes;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.ProductOrder;
import com.test.tutipet.enums.ObjectStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductOrderDtoConverter {
    public static ProductOrder toEntity(ProductOrderReq req) {
        ProductOrder productOrder = ProductOrder.builder()
                .quantity(req.getQuantity())
                .product(Product.builder().id(req.getProduct_id()).build())
                .build();

        productOrder.setObjectStatus(ObjectStatus.ACTIVE);
        return productOrder;
    }

    public static Set<ProductOrder> toSetEntity(Set<ProductOrderReq> productOrderReqs) {
        return productOrderReqs.stream()
                .map(ProductOrderDtoConverter::toEntity)
                .collect(Collectors.toSet());
    }



    public static Set<ProductOrderRes> toModelSet(Set<ProductOrder> productOrders) {
      return productOrders.stream()
              .map(ProductOrderDtoConverter::toResponse)
              .collect(Collectors.toSet());
    }

    private static ProductOrderRes toResponse(ProductOrder productOrder) {
        return new ProductOrderRes(
                productOrder.getId(),
                productOrder.getQuantity(),
                ProductDtoConverter.toResponse(productOrder.getProduct())
        );
    }
}
