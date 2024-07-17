package com.test.tutipet.utils;

import com.test.tutipet.entity.Order;
import com.test.tutipet.entity.ProductOrder;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class OrderUtils {
    public static BigDecimal calculateTotalPrice(Order order) {

        BigDecimal totalPrice = order.getProductOrders()
                .stream()
                .reduce(ZERO,
                        (total, productOrder)-> total.add(calculateProductPrice(productOrder)),
                        BigDecimal::add);

        return PromotionUtils.calculateDiscountOrder(order, totalPrice);
    }

    private static BigDecimal calculateProductPrice(ProductOrder productOrder) {
        return PromotionUtils.calculateDiscountProduct(productOrder.getProduct());
    }


}
