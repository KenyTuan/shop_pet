package com.test.tutipet.dtos.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductReq {

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Price must greater than 0")
    private double price;

    private String description;

    private String info;

    private String img;

    @NotNull(message = "Type is required")
    private Integer type_id;

}
