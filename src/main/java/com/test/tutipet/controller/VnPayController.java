package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.orders.CreateOrderReq;
import com.test.tutipet.dtos.payment.PaymentReq;
import com.test.tutipet.service.OrderService;
import com.test.tutipet.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class VnPayController {

    private final VnPayService vnPayService;
    private final OrderService orderService;

    @PostMapping(ApiEndpoints.PAY_V1 + "/create-transaction-vnp")
    @ResponseStatus(HttpStatus.CREATED)
    public String createPayment(@RequestBody @Valid PaymentReq code,
                                             @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
                                             HttpServletRequest request){
        return vnPayService.createPayment(request,token,code);
    }

}
