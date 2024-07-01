package com.test.tutipet.dtos.orders;

import com.test.tutipet.dtos.productOrders.ProductOrderRes;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

public record OrderRes(
        Long id,
        BigDecimal total,
        OrderStatus status,
        String phone,
        String address,
        Timestamp orderDate,
        UserRes user,
        Set<ProductOrderRes> productOrder
) {
}
