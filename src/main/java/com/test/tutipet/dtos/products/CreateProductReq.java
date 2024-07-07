package com.test.tutipet.dtos.products;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;


@Getter @Builder
public class CreateProductReq {

    @NotBlank(message = MessageException.REQUIRED_NAME)
    private String name;

    @Positive(message = MessageException.POSITIVE_PRICE)
    private double price;

    @NotBlank(message = MessageException.REQUIRED_BRAND)
    private String brand;

    @NotBlank(message = MessageException.REQUIRED_BRAND)
    private String origin;

    private String description;

    private String info;

    @NotBlank(message = MessageException.REQUIRED_IMAGE)
    private String img;

    @NotNull(message = MessageException.REQUIRED_TYPE)
    private Integer typeId;

}
