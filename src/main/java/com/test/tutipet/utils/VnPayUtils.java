package com.test.tutipet.utils;

import com.test.tutipet.constants.VnPayConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.swing.UIManager.put;

public class VnPayUtils {

    public static String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac hmacSHA512 = javax.crypto.Mac.getInstance("HmacSHA512");
            javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] dataBytes = hmacSHA512.doFinal(data.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : dataBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            return hash.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static String createPaymentUrl(
            String vnp_TmnCode, String vnp_HashSecret, String vnp_Url,
            String vnp_ReturnUrl, Long amount, String ipAddress,
             String orderId)
            throws UnsupportedEncodingException {

        Map<String, Object> vnp_Params = new HashMap<>(){{
            put("vnp_Version",VnPayConstants.VNP_VERSION);
            put("vnp_Command", VnPayConstants.VNP_COMMAND_ORDER);
            put("vnp_TmnCode", vnp_TmnCode);
            put("vnp_Amount", String.valueOf(amount * 100 * 1000));
            put("vnp_CurrCode", VnPayConstants.VNP_CURRENCY_CODE);
            put("vnp_TxnRef", orderId);
            put("vnp_OrderInfo", "Thanh Toán Đơn Hàng: " + orderId);
            put("vnp_OrderType", VnPayConstants.ORDER_TYPE);
            put("vnp_Locale", VnPayConstants.VNP_LOCALE);
            put("vnp_ReturnUrl", vnp_ReturnUrl);
            put("vnp_IpAddr", ipAddress);
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
        final Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {

            String fieldName = itr.next();
            String fieldValue = (String) payload.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {

                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {

                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        return new HashMap<>(){{
            put("queryUrl", query.toString());
            put("hashData", hashData.toString());
        }};
    }

    public static String generateDate(boolean forExpire) {

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        if (!forExpire) {

            return formatter.format(cld.getTime());
        }

        cld.add(Calendar.MINUTE, 15);
        return formatter.format(cld.getTime());
    }
}
