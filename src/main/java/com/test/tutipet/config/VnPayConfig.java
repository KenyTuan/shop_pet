package com.test.tutipet.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class VnPayConfig {
    @Value("${application.vnpay.vnp_TmnCode}")
    private String vnp_TmnCode;

    @Value("${application.vnpay.vnp_HashSecret}")
    private String vnp_HashSecret;

    @Value("${application.vnpay.vnp_Url}")
    private String vnp_Url;

    @Value("${application.vnpay.vnp_Returnurl}")
    private String vnp_ReturnUrl;
}
