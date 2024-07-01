package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.orders.OrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

//    @GetMapping(ApiEndpoints.ORDER_V1)
//    public List<OrderRes> getOrders() {
//        return orderService.getOrders();
//    }

    @GetMapping(ApiEndpoints.ORDER_V1 + "/{id}")
    public OrderRes getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping(ApiEndpoints.ORDER_V1)
    public OrderRes getOrderByUserId(@RequestParam(name = "userId") long userId) {
        return orderService.getOrderByUserId(userId);
    }

    @PostMapping(ApiEndpoints.ORDER_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderRes addOrder(@RequestBody @Valid OrderReq req) {
        return orderService.addOrder(req);
    }
}
