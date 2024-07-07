package com.test.tutipet.service;

import com.test.tutipet.dtos.orders.CreateOrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.dtos.payment.PaymentReq;

import java.util.List;

public interface OrderService {

    List<OrderRes> getOrders();

    OrderRes getOrderById(long id);

    OrderRes getOrderByUserId(long id);

    List<OrderRes> getOrderByToken(String token);

    OrderRes addOrder(CreateOrderReq order, String token);

    OrderRes validateOrderPayment(PaymentReq code, String token);
}
