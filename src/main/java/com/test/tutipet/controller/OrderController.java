package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.orders.CreateOrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.dtos.payment.PaymentReq;
import com.test.tutipet.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping(ApiEndpoints.ORDER_V1)
    public List<OrderRes> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping(ApiEndpoints.ORDER_V1 + "/{id}")
    public OrderRes getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping(ApiEndpoints.ORDER_V1 + "/user/{userId}")
    public OrderRes getOrderByUserId(@PathVariable long userId) {
        return orderService.getOrderByUserId(userId);
    }

    @GetMapping(ApiEndpoints.ORDER_V1 + "/user")
    public List<OrderRes> getAllOrdersByToken(
                             @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return orderService.getOrderByToken(token);
    }

    @PostMapping(ApiEndpoints.ORDER_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderRes addOrder(@RequestBody @Valid CreateOrderReq req,
                             @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return orderService.addOrder(req, token);
    }

    @PostMapping(ApiEndpoints.ORDER_V1 + "/validate-payment")
    public OrderRes validateOrderPayment(@RequestBody @Valid PaymentReq code,
                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
        return orderService.validateOrderPayment(code,token);
    }
}
