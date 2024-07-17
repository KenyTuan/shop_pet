package com.test.tutipet.converter;

import com.test.tutipet.dtos.orders.CreateOrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.dtos.promotions.PromotionRes;
import com.test.tutipet.entity.Order;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.OrderStatus;
import com.test.tutipet.utils.PromotionUtils;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderDtoConverter {
    public static Order toEntity(CreateOrderReq req) {
        final Order order = Order.builder()
                .orderDate(ZonedDateTime.now())
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
                order.getCode(),
                order.getStatus(),
                order.getPaymentType(),
                order.getPhone(),
                order.getAddress(),
                order.getOrderDate(),
                UserDtoConverter.toResponse(order.getUser()),
                ProductOrderDtoConverter.toModelSet(order.getProductOrders()),
                PromotionUtils.getCurrentPromotion(order.getPromotions())
        );
    }

    public static List<OrderRes> toResponseList(List<Order> orders) {
        return orders.stream()
                .map(OrderDtoConverter::toResponse)
                .toList();
    }
}
