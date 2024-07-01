package com.test.tutipet.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiEndpoints {

    public static final String PREFIX = "/api";
    public static final String USER_V1 = "/v1/users";
    public static final String ORDER_V1 = "/v1/orders";
    public static final String PRO_TYPE_V1 = "/v1/types";
    public static final String PRODUCT_V1 = "/v1/products";
    public static final String ADDRESS_V1 = "/v1/address";
    public static final String PROMOTION_V1 = "/v1/promotions";
    public static final String CART_V1 = "/v1/carts";
    public static final String AUTH_V1 = "/v1/auth";
    public static final String PAY_V1 = "/v1/create_payment";
}
