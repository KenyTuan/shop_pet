package com.test.tutipet.service;

import com.test.tutipet.dtos.payment.PaymentReq;
import jakarta.servlet.http.HttpServletRequest;

public interface VnPayService {

    String createPayment(HttpServletRequest request, String token, PaymentReq code);

}
