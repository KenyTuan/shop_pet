package com.test.tutipet.utils;

import com.test.tutipet.entity.Promotion;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PromotionUtils {
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
        return i -> i.getFromTime().isBefore(now) && i.getToTime().isAfter(now);
    }
}
