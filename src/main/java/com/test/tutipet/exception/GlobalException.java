package com.test.tutipet.exception;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
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
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handleException(HttpServletRequest request, Exception ex) {
        log.error("Internal server error", ex);
        return buildResponseException(ErrorCode.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseException handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        log.warn("Unsupported media type", ex);
        return buildResponseException(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, ex);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handleHttpMessageNotWritableException(HttpServletRequest request, HttpMessageNotWritableException ex) {
        log.error("Message not writable", ex);
        return buildResponseException(ErrorCode.HTTP_MESSAGE_NOT_WRITABLE, HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseException handleHttpMediaTypeNotAcceptableException(HttpServletRequest request, HttpMediaTypeNotAcceptableException ex) {
        log.warn("Media type not acceptable", ex);
        return buildResponseException(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE, request, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        log.warn("Message not readable", ex);
        return buildResponseException(ErrorCode.HTTP_MESSAGE_NOT_READABLE, HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleJsonParseException(HttpServletRequest request, JsonParseException ex) {
        log.warn("JSON parse error", ex);
        return buildResponseException(ErrorCode.JSON_PARSE_ERROR, HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseException handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.warn("Request method not supported", ex);
        return buildResponseException(ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED, HttpStatus.METHOD_NOT_ALLOWED, request, ex);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseException handleMethodNotAllowedException(HttpServletRequest request, MethodNotAllowedException ex) {
        log.warn("Method not allowed", ex);
        return new ResponseException(
                String.format("%s. Supported methods: %s", ex.getMessage(), ex.getSupportedMethods()),
                ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrCode(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                Instant.now()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseException handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        log.info("Authentication exception", ex);
        return buildResponseException(ErrorCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, request, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseException handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.info("Access denied", ex);
        return buildResponseException(ErrorCode.ACCESS_DENIED, HttpStatus.FORBIDDEN, request, ex);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handleInvalidRefreshTokenException(HttpServletRequest request, InvalidRefreshTokenException ex) {
        log.warn("Invalid refresh token", ex);
        return buildResponseException(ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        log.warn("Illegal argument exception", ex);
        return buildResponseException(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        return new ResponseException(
                ex.getErrCode(),
                ex.getErrMsg(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                Instant.now()
        );
    }

    @ExceptionHandler(GenericAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseException handleGenericAlreadyExistsException(HttpServletRequest request, GenericAlreadyException ex) {
        log.warn("Generic already exists", ex);
        return new ResponseException(
                ex.getErrCode(),
                ex.getErrMsg(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                Instant.now()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        return new ResponseException(
                ex.getErrCode(),
                ex.getErrMsg(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                Instant.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.warn("Method argument not valid", ex);
        String message = ex.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseException(
                ex.getStatusCode().toString(),
                message,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                Instant.now()
        );
    }

    private ResponseException buildResponseException(ErrorCode errorCode, HttpStatus status,
                                                     HttpServletRequest request, Exception ex) {
        return new ResponseException(
                errorCode.getErrCode(),
                errorCode.getErrMessage() + " " + ex.getMessage(),
                status.value(),
                request.getRequestURL().toString(),
                request.getMethod(),
                Instant.now()
        );
    }
}
