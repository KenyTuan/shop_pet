package com.test.tutipet.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageException {
    public static final String INVALID_EMAIL = "Invalid Email!";
    public static final String INVALID_USERNAME = "Invalid Username!";
    public static final String INVALID_PASSWORD = "Invalid Password!";
    public static final String INVALID_PASSWORD_OLD = "Invalid Password Old!";
    public static final String INVALID_PASSWORD_NEW = "Invalid Password New!";
    public static final String INVALID_NAME = "Invalid Name!";
    public static final String INVALID_PHONE = "Invalid Phone!";
    public static final String INVALID_ADDRESS = "Invalid Address!";
    public static final String REQUIRED_EMAIL = "Email Is Required!";
    public static final String REQUIRED_PASSWORD = "Password Is Required!";
    public static final String REQUIRED_FULL_NAME = "Full Name Length Ranges From 5 To 255 Characters!";
    public static final String REQUIRED_TYPE = "Type Is Required!";
    public static final String REQUIRED_NAME = "Name Is Required!";
    public static final String REQUIRED_PRODUCT = "Product Id Is Required!";
    public static final String REQUIRED_QUANTITY = "Quantity Must Greater Than 0!";
    public static final String REQUIRED_TOTAL = "Total Must Greater Than 0!";
    public static final String POSITIVE_PRICE = "Price Must Greater Than 0!";

    public static final String REQUIRED_ADDRESS = "Address Is Required!";
    public static final String REQUIRED_USER = "User Id Is Required!";
    public static final String REQUIRED_LIST_PRODUCT = "List Product Order Is Required!";
    public static final String ALREADY_EXIST_EMAIL = "Email Already Exists!";
    public static final String NOT_FOUND_USER = "User Not Found!";
    public static final String NOT_MATCH_PASSWORD = "Password Not Match!";
    public static final String TOKEN_EXPIRED = "Token Expired!";
    public static final String REQUIRED_TOKEN = "Token is Required!";
}
