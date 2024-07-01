package com.test.tutipet.dtos.products;

import com.test.tutipet.constants.MessageException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductReq {

    @NotBlank(message = MessageException.REQUIRED_NAME)
    private String name;

    @Positive(message = MessageException.POSITIVE_PRICE)
    private double price;

    private String description;

    private String info;

    private String img;

    @NotNull(message = MessageException.REQUIRED_TYPE)
    private Integer type_id;

}
