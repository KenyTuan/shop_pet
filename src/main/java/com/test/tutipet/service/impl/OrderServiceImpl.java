package com.test.tutipet.service.impl;

import com.test.tutipet.converter.OrderDtoConverter;
import com.test.tutipet.dtos.orders.OrderReq;
import com.test.tutipet.dtos.orders.OrderRes;
import com.test.tutipet.entity.*;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.*;
import com.test.tutipet.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductCartRepository productCartRepository;
    private final ProductOrderRepository productOrderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderRes> getOrders() {
        return orderRepository.findAll().stream()
                .map(OrderDtoConverter::toResponse)
                .collect(Collectors.toList());
    }

    public OrderRes getOrderById(long id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order Not Found With Order Id: " + id));
        return OrderDtoConverter.toResponse(order);
    }

    @Override
    public OrderRes getOrderByUserId(long id) {
        final Order order = orderRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Order Not Found With Order Id: " + id));
        return OrderDtoConverter.toResponse(order);
    }


    @SneakyThrows
    @Override
    @Transactional
    public OrderRes addOrder(OrderReq req) {

        final Order order = OrderDtoConverter.toEntity(req);

        Iterable<ProductCart> carts = productCartRepository.findByUserId(req.getUserId());

        Set<ProductCart> cartSet = StreamSupport.stream(carts.spliterator(),false)
                .collect(Collectors.toSet());

        if (cartSet.isEmpty()) {
            throw new NotFoundException(
                    String.format("There is no item found in customer's (ID: %s) cart.", req.getUserId())
            );
        }

        order.setUser(userRepository.findById(req.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found with user ID: " + req.getUserId())
        ));

        order.setTotal(getTotal(cartSet));
        orderRepository.save(order);

        Thread.sleep(100L);

        productCartRepository.deleteAll(cartSet);


        Set<ProductOrder> productOrders = order.getProductOrders()
                .stream()
                .peek(i -> {
                    i.setOrder(order);
                    i.setProduct(productRepository.findById(i.getProduct().getId())
                            .orElseThrow(
                                    () -> new NotFoundException("Product not found with ID: " + i.getProduct().getId())
                            ));
                })
                .collect(Collectors.toSet());

        productOrderRepository
                .saveAll(productOrders);
        return OrderDtoConverter.toResponse(order);
    }

    private BigDecimal getTotal(Set<ProductCart> cartSet) {
        return BigDecimal.valueOf(cartSet.stream()
                .mapToDouble(productCart ->
                        productCart.getProduct().getPrice() * productCart.getQuantity()
                )
                .sum());
    }


}
