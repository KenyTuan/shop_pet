package com.test.tutipet.converter;

import com.test.tutipet.dtos.orders.OrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.entity.Order;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.OrderStatus;

import java.sql.Timestamp;
import java.time.Instant;

public class OrderDtoConverter {
    public static Order toEntity(OrderReq req) {
        final Order order = Order.builder()
                .orderDate(Timestamp.from(Instant.now()))
                .address(req.getAddress())
                .phone(req.getPhone())
                .status(OrderStatus.OPEN)
                .productOrders(ProductOrderDtoConverter.toSetEntity(req.getProductOrders()))
                .build();

        order.setObjectStatus(ObjectStatus.ACTIVE);
        return order;
    }

    public static OrderRes toResponse(Order order) {
        return new OrderRes(
                order.getId(),
                order.getTotal(),
                order.getStatus(),
                order.getPhone(),
                order.getAddress(),
                order.getOrderDate(),
                UserDtoConverter.toResponse(order.getUser()),
                ProductOrderDtoConverter.toModelSet(order.getProductOrders())
        );

    }
}
