package com.test.tutipet.dtos.orders;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.dtos.productOrders.ProductOrderReq;
import com.test.tutipet.dtos.productOrders.ProductOrderRes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderReq {

    @Pattern(regexp = "(03|05|07|08|09|01[2|689])+([0-9]{8})\\b",message = MessageException.INVALID_PHONE)
    private String phone;

    @NotBlank(message = MessageException.REQUIRED_ADDRESS)
    private String address;

    @NotNull(message = MessageException.REQUIRED_USER)
    private Long userId;

    @NotNull(message = MessageException.REQUIRED_LIST_PRODUCT_ORDER)
    private Set<ProductOrderReq> productOrders;


}
