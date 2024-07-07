package com.test.tutipet.service.impl;

import com.test.tutipet.config.VnPayConfig;
import com.test.tutipet.constants.MessageException;
import com.test.tutipet.dtos.payment.PaymentReq;
import com.test.tutipet.entity.*;
import com.test.tutipet.exception.BadRequestException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.OrderRepository;
import com.test.tutipet.repository.ProductCartRepository;
import com.test.tutipet.repository.PromotionRepository;
import com.test.tutipet.security.JwtUtil;
import com.test.tutipet.service.VnPayService;
import com.test.tutipet.utils.VnPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VnPayServiceImpl implements VnPayService {


    private final VnPayConfig vnPayConfig;
    private final JwtUtil jwtUtil;
    private final ProductCartRepository productCartRepository;
    private final PromotionRepository promotionRepository;
    private final OrderRepository orderRepository;

    @SneakyThrows
    @Override
    public String createPayment(HttpServletRequest request, String token, PaymentReq req) {
        final String ipAddress = request.getRemoteAddr();

        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid or missing Authorization header");
        }

        final String email = jwtUtil.extractUsername(token.substring(7));

        final List<Order> orders = orderRepository.findAllByUserEmail(email);

        final Order order = orders.stream()
                .filter((i)-> i.getCode().equals(req.getCode())).findFirst()
                .orElseThrow(()->new NotFoundException(MessageException.NOT_FOUND_ORDER));



        return VnPayUtils.createPaymentUrl(
                vnPayConfig.getVnp_TmnCode(),
                vnPayConfig.getVnp_HashSecret(),
                vnPayConfig.getVnp_Url(),
                vnPayConfig.getVnp_ReturnUrl(),
                order.getTotal().longValueExact(),
                ipAddress,
                order.getCode()
        );
    }

}
