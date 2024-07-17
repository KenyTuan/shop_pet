package com.test.tutipet.utils;

import com.test.tutipet.entity.Order;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.DiscountType;
import com.test.tutipet.enums.ObjectStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PromotionUtils {

    public static BigDecimal calculateDiscountOrder(Order order, BigDecimal totalPrice) {
        final Promotion orderPromotion = getCurrentPromotion(order.getPromotions());

        if (orderPromotion != null) {
            if (orderPromotion.getDiscountType().equals(DiscountType.PERCENTAGE)) {
                totalPrice = totalPrice.subtract(totalPrice.multiply(orderPromotion.getValue().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)));
            } else if (orderPromotion.getDiscountType().equals(DiscountType.SPECIFIC)) {
                totalPrice = totalPrice.subtract(orderPromotion.getValue());
            }
        }
        return totalPrice;
    }

    public static BigDecimal calculateDiscountProduct(Product product) {
        final Promotion productPromotion = getCurrentPromotion(product.getPromotions());

        final BigDecimal discount = BigDecimal.valueOf(product.getPrice());
        if (Objects.isNull(productPromotion)) {
            return discount;
        }else{
            if (productPromotion.getDiscountType().equals(DiscountType.PERCENTAGE)) {
                return discount.subtract(discount.multiply(productPromotion.getValue().divide(BigDecimal.valueOf(100),RoundingMode.HALF_UP)));
            } else {
                return discount.subtract(productPromotion.getValue());
            }
        }

    }


    public static Promotion getCurrentPromotion(Set<Promotion> promotions) {
        if (promotions == null) {
            return null;
        }

        return promotions.stream()
                .filter(isCurrentAvailable())
                .findFirst()
                .orElse(null);
    }

    public static Set<Promotion> getCurrentListPromotion(Set<Promotion> promotions) {
        return promotions.stream()
                .filter(isCurrentAvailable())
                .collect(Collectors.toSet());
    }

    private static Predicate<Promotion> isCurrentAvailable() {
        ZonedDateTime now = ZonedDateTime.now();
        return i -> i.getFromTime().isBefore(now) && i.getToTime().isAfter(now) && i.getObjectStatus().equals(ObjectStatus.ACTIVE);
    }
}
