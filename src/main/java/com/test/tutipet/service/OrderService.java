package com.test.tutipet.service;

import com.test.tutipet.dtos.orders.OrderReq;
import com.test.tutipet.dtos.orders.OrderRes;

import java.util.List;

public interface OrderService {

    List<OrderRes> getOrders();

    OrderRes getOrderById(long id);

    OrderRes getOrderByUserId(long id);

    OrderRes addOrder(OrderReq order);



}
