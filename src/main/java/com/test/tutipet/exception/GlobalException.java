package com.test.tutipet.exception;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.Instant;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(
            HttpServletRequest request, Exception ex, Locale locale) {

        return Error
                .builder()
                .message(ErrorCode.GENERIC_ERROR.getErrMessage())
                .errorCode( ErrorCode.GENERIC_ERROR.getErrCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMediaTypeNotSupportedException(
            HttpServletRequest request, HttpMediaTypeNotSupportedException ex, Locale locale) {
        log.info("HttpMediaTypeNotSupportedException :: request.getMethod(): {}", request.getMethod());
        return Error
                .builder()
                .message(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMessage())
                .errorCode( ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMessageNotWritableException(
            HttpServletRequest request, HttpMessageNotWritableException ex, Locale locale) {
        log.info("HttpMessageNotWritableException :: request.getMethod(): {}", request.getMethod());
        return Error
                .builder()
                .message(ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrMessage())
                .errorCode( ErrorCode.HTTP_MESSAGE_NOT_WRITABLE.getErrCode())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMediaTypeNotAcceptableException(
            HttpServletRequest request, HttpMediaTypeNotAcceptableException ex, Locale locale) {
        ex.printStackTrace(); // TODO: Should be kept only for development
        log.info("HttpMediaTypeNotAcceptableException :: request.getMethod(): " + request.getMethod());
        return Error
                .builder()
                .message(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrMessage())
                .errorCode( ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrCode())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMessageNotReadableException(
            HttpServletRequest request, HttpMessageNotReadableException ex, Locale locale) {
        return Error
                .builder()
                .message(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrMessage())
                .errorCode( ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrCode())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleJsonParseException(
            HttpServletRequest request, JsonParseException ex, Locale locale) {
        return Error
                .builder()
                .message(ErrorCode.JSON_PARSE_ERROR.getErrMessage())
                .errorCode( ErrorCode.JSON_PARSE_ERROR.getErrCode())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Error handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request, HttpRequestMethodNotSupportedException ex, Locale locale) {
        return Error
                .builder()
                .message(ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrMessage())
                .errorCode( ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrCode())
                .status(HttpStatus.NOT_IMPLEMENTED.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Error handleMethodNotAllowedException(
            HttpServletRequest request, MethodNotAllowedException ex, Locale locale) {
        return Error
                .builder()
                .message(
                        String.format(
                                "%s. Supported methods: %s", ex.getMessage(), ex.getSupportedMethods()))
                .errorCode( ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrCode())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error handleAuthenticationException(
            HttpServletRequest request, AuthenticationException ex, Locale locale) {
        log.info("exception = " + ex);
        log.info("exception.getCause() = " + ex.getCause());

        return Error
                .builder()
                .message(
                        String.format(
                                "%s %s", ErrorCode.UNAUTHORIZED.getErrMessage(), ex.getMessage()))
                .errorCode( ErrorCode.UNAUTHORIZED.getErrCode())
                .status(HttpStatus.UNAUTHORIZED.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error handleAccessDeniedException(
            HttpServletRequest request, AccessDeniedException ex, Locale locale) {
        log.info("exception = " + ex);
        log.info("exception.getCause() = " + ex.getCause());
        return Error
                .builder()
                .message(
                        String.format(
                                "%s %s", ErrorCode.ACCESS_DENIED.getErrMessage(), ex.getMessage()))
                .errorCode( ErrorCode.ACCESS_DENIED.getErrCode())
                .status(HttpStatus.FORBIDDEN.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleInvalidRefreshTokenException(
            HttpServletRequest request, InvalidRefreshTokenException ex, Locale locale) {
        return Error
                .builder()
                .message(
                        String.format(
                                "%s %s", ErrorCode.RESOURCE_NOT_FOUND.getErrMessage(), ex.getMessage()))
                .errorCode( ErrorCode.RESOURCE_NOT_FOUND.getErrCode())
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleHIllegalArgumentException(
            HttpServletRequest request, IllegalArgumentException ex, Locale locale) {
        return Error
                .builder()
                .message(
                        String.format(
                                "%s %s", ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrMessage(), ex.getMessage()))
                .errorCode( ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrCode())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleResourceNotFoundException(
            HttpServletRequest request, ResourceNotFoundException ex, Locale locale) {

        return Error
                .builder()
                .message(
                        String.format(
                                        "%s %s", ErrorCode.RESOURCE_NOT_FOUND.getErrMessage(), ex.getMessage()))
                .errorCode(ex.getErrCode())
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleCustomerNotFoundException(
            HttpServletRequest request, NotFoundException ex, Locale locale) {
        return Error
                .builder()
                .message(
                        String.format(
                                "%s %s", ErrorCode.CUSTOMER_NOT_FOUND.getErrMessage(), ex.getMessage()))
                .errorCode(ex.getErrCode())
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }


    @ExceptionHandler(GenericAlreadyException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Error handleGenericAlreadyExistsException(
            HttpServletRequest request, GenericAlreadyException ex, Locale locale) {
        return Error
                .builder()
                .message(
                        String.format(
                        "%s %s", ErrorCode.GENERIC_ALREADY_EXISTS.getErrMessage(), ex.getMessage()))
                .errorCode(ex.getErrCode())
                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timestamp(Instant.now())
                .build();
    }

}
