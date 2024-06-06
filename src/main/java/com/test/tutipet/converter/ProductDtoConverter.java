package com.test.tutipet.converter;

import com.test.tutipet.dtos.products.ProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.entity.Product;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;

public class ProductDtoConverter {

    public static Product toEntity(ProductReq req){
        Product product = Product.builder()
                .name(req.getName())
                .price(req.getPrice())
                .description(req.getDescription())
                .info(req.getInfo())
                .image(req.getImg())
                .build();
        product.setStatus(EnableStatus.ENABLED);
        product.setObjectStatus(ObjectStatus.ACTIVE);
        return product;
    }

    public static ProductRes toResponse(Product product){
        return new ProductRes(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getInfo(),
                product.getImage(),
                product.getStatus(),
                product.getProductType()
        );
    }

}
