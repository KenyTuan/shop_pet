package com.test.tutipet.converter;

import com.test.tutipet.dtos.products.CreateProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.dtos.products.UpdateProductReq;
import com.test.tutipet.entity.Product;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.utils.PromotionUtils;

import java.util.List;
import java.util.Set;

public class ProductDtoConverter {

    public static Product toEntity(CreateProductReq req){
        Product product = Product.builder()
                .name(req.getName())
                .brand(req.getBrand())
                .origin(req.getOrigin())
                .price(req.getPrice())
                .description(req.getDescription())
                .info(req.getInfo())
                .timeExp(req.getTimeExp())
                .image(req.getImg())
                .status(EnableStatus.ENABLED)
                .build();
        product.setObjectStatus(ObjectStatus.ACTIVE);
        return product;
    }

    public static ProductRes toResponse(Product product){
        return new ProductRes(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getOrigin(),
                product.getPrice(),
                product.getDescription(),
                product.getInfo(),
                product.getImage(),
                product.getTimeExp(),
                product.getStatus(),
                product.getProductType(),
                PromotionUtils.getCurrentPromotion(product.getPromotions())
        );
    }

    public static Product toEntity(UpdateProductReq req) {
        Product product = Product.builder()
                .name(req.getName())
                .brand(req.getBrand())
                .origin(req.getOrigin())
                .price(req.getPrice())
                .description(req.getDescription())
                .info(req.getInfo())
                .timeExp(req.getTimeExp())
                .image(req.getImg())
                .status(EnableStatus.ENABLED)
                .build();
        product.setObjectStatus(ObjectStatus.ACTIVE);
        return product;
    }

    public static List<ProductRes> toResponseList(Set<Product> products) {
        return products.stream().map(ProductDtoConverter::toResponse).toList();
    }
}
