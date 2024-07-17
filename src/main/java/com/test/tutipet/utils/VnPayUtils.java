package com.test.tutipet.utils;

import com.test.tutipet.constants.VnPayConstants;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.swing.UIManager.put;

public class VnPayUtils {

    public static String hmacSHA512(final String key, final String data) {

        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }

            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {

            return "";
        }

    }

    public static String createPaymentUrl(
            String vnp_TmnCode, String vnp_HashSecret, String vnp_Url,
            String vnp_ReturnUrl, Long amount, HttpServletRequest ipAddress,
             String orderId)
            throws UnsupportedEncodingException {

        Map<String, Object> vnp_Params = new HashMap<>(){{
            put("vnp_Version",VnPayConstants.VNP_VERSION);
            put("vnp_Command", VnPayConstants.VNP_COMMAND_ORDER);
            put("vnp_TmnCode", vnp_TmnCode);
            put("vnp_Amount", String.valueOf(amount * 100000 ));
            put("vnp_CurrCode", VnPayConstants.VNP_CURRENCY_CODE);
            put("vnp_TxnRef", orderId);
            put("vnp_OrderInfo", "Thanh Toan Don Hang: "+ orderId);
            put("vnp_OrderType", VnPayConstants.ORDER_TYPE);
            put("vnp_Locale", VnPayConstants.VNP_LOCALE);
            put("vnp_ReturnUrl", vnp_ReturnUrl);
            put("vnp_IpAddr", getIpAddress(ipAddress));
            put("vnp_CreateDate", generateDate(false));
            put("vnp_ExpireDate", generateDate(true));
        }};

        final String queryUrl = getQueryUrl(vnp_Params).get("queryUrl")
                + "&vnp_SecureHash="
                + hmacSHA512(vnp_HashSecret, getQueryUrl(vnp_Params).get("hashData"));

        return vnp_Url + "?" + queryUrl;
    }

    private static Map<String, String> getQueryUrl(Map<String, Object> payload)
            throws UnsupportedEncodingException {
        final List<String> fieldNames = new ArrayList<>(payload.keySet());
        Collections.sort(fieldNames);
        final StringBuilder hashData = new StringBuilder();
        final StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = (String) payload.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        return new HashMap<>() {{
            put("queryUrl", query.toString());
            put("hashData", hashData.toString());
        }};
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static String generateDate(boolean forExpire) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        if (forExpire) {
            cld.add(Calendar.MINUTE, 15);
        }
        return formatter.format(cld.getTime());
    }
}
