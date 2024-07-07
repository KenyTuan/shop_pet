package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.OrderDtoConverter;
import com.test.tutipet.dtos.orders.CreateOrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.dtos.payment.PaymentReq;
import com.test.tutipet.dtos.productOrders.ProductOrderReq;
import com.test.tutipet.entity.*;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.OrderStatus;
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
        return OrderDtoConverter.toResponseList(orderRepository.findAll());
    }

    public OrderRes getOrderById(long id) {
        return OrderDtoConverter.toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_ORDER)));
    }

    @Override
    public OrderRes getOrderByUserId(long id) {
        final Order order = orderRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_ORDER));
        return OrderDtoConverter.toResponse(order);
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


        final Set<ProductOrder> productOrders = order.getProductOrders()
                .stream()
                .peek(i -> {
                    i.setOrder(order);
                    final Product product = productRepository.findById(i.getProduct().getId())
                            .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));
                    i.setProduct(product);
                })
                .collect(Collectors.toSet());

        final BigDecimal total = productOrders.stream()
                .map(this::calculateDiscountedPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        productOrderRepository
                .saveAll(productOrders);
        order.setCode(generateOrderCode());

        if(req.getCode().isEmpty()){
            order.setUser(user);
            order.setTotal(total);
            orderRepository.save(order);
            return OrderDtoConverter.toResponse(order);
        }

        final Promotion promotion = promotionRepository.findByCodeAndUserEmail(req.getCode(),email)
                        .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PROMOTION));

        if(promotion.getDiscountType() == DiscountType.PERCENTAGE){
            total.subtract(total.multiply(promotion.getValue().divide(BigDecimal.valueOf(100))));
        }else{
            total.subtract(promotion.getValue());
        }

        order.setUser(user);
        order.setTotal(total);
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

        order.setStatus(OrderStatus.PAYMENT);
        orderRepository.save(order);
        return OrderDtoConverter.toResponse(order);
    }

    private BigDecimal calculateDiscountedPrice(ProductOrder productOrder) {
        final Product product = productRepository.findById(productOrder.getProduct().getId())
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));
        Promotion promotion = PromotionUtils.getCurrentPromotion(product.getPromotions());
        BigDecimal discountedPrice = BigDecimal.valueOf(product.getPrice());

        if (promotion != null) {
            if (promotion.getDiscountType() == DiscountType.PERCENTAGE) {
                BigDecimal discountAmount = discountedPrice.multiply(promotion.getValue()).divide(BigDecimal.valueOf(100));
                discountedPrice = discountedPrice.subtract(discountAmount);
            } else {
                discountedPrice = discountedPrice.subtract(promotion.getValue());
            }
        }

        return discountedPrice.multiply(BigDecimal.valueOf(productOrder.getQuantity()));
    }

    private String generateOrderCode() {
        UUID uuid = UUID.randomUUID();

        String uuidString = uuid.toString().replace("-", "").substring(0, 8);

        return "DH" + uuidString;
    }


}
