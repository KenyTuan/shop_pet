package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.OrderDtoConverter;
import com.test.tutipet.dtos.orders.CreateOrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.dtos.payment.PaymentReq;
import com.test.tutipet.dtos.productOrders.ProductOrderReq;
import com.test.tutipet.entity.*;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.OrderStatus;
import com.test.tutipet.enums.PaymentType;
import com.test.tutipet.exception.BadRequestException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.*;
import com.test.tutipet.security.JwtUtil;
import com.test.tutipet.service.OrderService;
import com.test.tutipet.utils.PromotionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductCartRepository productCartRepository;
    private final ProductOrderRepository productOrderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;
    private final PromotionRepository promotionRepository;

    @Override
    public List<OrderRes> getOrders() {
        return OrderDtoConverter.toResponseList(orderRepository.findAllActive());
    }

    public OrderRes getOrderById(long id) {
        return OrderDtoConverter.toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_ORDER)));
    }

    @Override
    public List<OrderRes> getOrderByUserId(long id) {
        return OrderDtoConverter.toResponseList(orderRepository.findByUserId(id));
    }

    @Override
    public List<OrderRes> getOrderByToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid or missing Authorization header");
        }

        final String email = jwtUtil.extractUsername(token.substring(7));

        return OrderDtoConverter.toResponseList(orderRepository.findAllByUserEmail(email));
    }

    @Override
    @Transactional
    public OrderRes addOrder(CreateOrderReq req, String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid or missing Authorization header");
        }

        final String email = jwtUtil.extractUsername(token.substring(7));

        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_USER));

        final Order order = OrderDtoConverter.toEntity(req);

        final List<Long> productId = req.getProductOrders()
                .stream()
                .map(ProductOrderReq::getProduct_id)
                .toList();

        final Set<ProductCart> carts =
                new HashSet<>(productCartRepository.findAllByIdInAndUserEmail(email, productId));

        productCartRepository.deleteAll(carts);

        final Set<ProductOrder> productOrders = req.getProductOrders()
                .stream()
                .map(i -> converProductOrder(order, i)
                )
                .collect(Collectors.toSet());

        productOrderRepository
                .saveAll(productOrders);

        order.setProductOrders(productOrders);
        order.setCode(generateOrderCode());
        order.setPaymentType(PaymentType.NON_PAYMENT);
        order.setUser(user);
        if(req.getPromotionId()!= 0){
            final Promotion promotion = promotionRepository.findById(req.getPromotionId())
                    .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PROMOTION));
            promotion.getOrders().add(order);

            promotionRepository.save(promotion);
        }

        orderRepository.save(order);

        return OrderDtoConverter.toResponse(order);
    }

    @Override
    public OrderRes validateOrderPayment(PaymentReq req, String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid or missing Authorization header");
        }

        final String email = jwtUtil.extractUsername(token.substring(7));

        final Order order = orderRepository.findByUserEmailAndCode(email, req.getCode())
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_ORDER));

        order.setPaymentType(PaymentType.PAYMENT_VNP);
        orderRepository.save(order);
        return OrderDtoConverter.toResponse(order);
    }

    @Override
    public OrderRes changeStatusOrder(OrderStatus status, Long id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_ORDER));

        order.setStatus(status);
        orderRepository.save(order);
        return OrderDtoConverter.toResponse(order);
    }

    @Override
    public void deletedOrder(Long id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_ORDER));
        order.setObjectStatus(ObjectStatus.DELETED);
        orderRepository.save(order);
    }

    private String generateOrderCode() {
        UUID uuid = UUID.randomUUID();

        String uuidString = uuid.toString().replace("-", "").substring(0, 8);

        return "DH" + uuidString;
    }

    private ProductOrder converProductOrder(Order order, ProductOrderReq productOrderReq) {
        final Product product = productRepository.findById(productOrderReq.getProduct_id())
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));
        return ProductOrder.builder()
                .order(order)
                .product(product)
                .quantity(productOrderReq.getQuantity())
                .build();
    }
}
