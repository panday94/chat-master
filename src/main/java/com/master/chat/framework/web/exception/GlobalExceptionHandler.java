package com.master.chat.framework.web.exception;

import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 异常处理
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseInfo handleException(BusinessException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.businessFail(e.getMsg());
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(ValidateException.class)
    public ResponseInfo handleException(ValidateException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.validateFail(e.getMsg());
    }

    /**
     * 账号异常
     */
    @ExceptionHandler(ProhibitVisitException.class)
    public ResponseInfo handleException(ProhibitVisitException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.unauthorized(e.getMsg());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(PermissionException.class)
    public ResponseInfo handleException(PermissionException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.permissionDenied();
    }

    /**
     * 权限不足
     *
     * @param e
     * @return
     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseInfo handleAccessDeniedException(AccessDeniedException e) {
//        return ResponseInfo.permissionDenied();
//    }

    /**
     * 系统异常
     */
    @ExceptionHandler(ErrorException.class)
    public ResponseInfo handleException(ErrorException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.error(e.getMsg());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResponseInfo handleException(CustomException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.customizeError(e.getCode(), e.getMsg());
    }

    /**
     * 数据更新异常
     */
    @ExceptionHandler(UpdateFailedException.class)
    public ResponseInfo handleException(UpdateFailedException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.error(e.getMsg());
    }

    /**
     * 系统预警
     */
    @ExceptionHandler(SystemException.class)
    public ResponseInfo handleException(SystemException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.systemWarning(e.getMsg());
    }

    /**
     * 异常捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseInfo handleException(Exception e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.error();
    }

    /**
     * 重复值异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseInfo handleException(DuplicateKeyException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.validateFail("数据库中已存在该记录");
    }

    /**
     * 请求方式错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseInfo handleException(HttpRequestMethodNotSupportedException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.requestMethodError();
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseInfo handleException(ConstraintViolationException e) {
        log.error(Thread.currentThread().getName(), e);
        String message = e.getConstraintViolations().stream().findFirst().map(ConstraintViolation::getMessage).get();
        return ResponseInfo.validateFail(message);
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseInfo handleException(MethodArgumentNotValidException e) {
        log.error(Thread.currentThread().getName(), e);
        String message = e.getBindingResult().getAllErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).get();
        return ResponseInfo.validateFail(message);
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseInfo handleException(BindException e) {
        log.error(Thread.currentThread().getName(), e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResponseInfo.validateFail(message);
    }

    /**
     * 处理请求参数格式错误，参数转换失败后抛出的异常是HttpMessageNotReadableException异常。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseInfo handleException(HttpMessageNotReadableException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.validateFail();
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseInfo handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    /**
     * 数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseInfo handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(Thread.currentThread().getName(), e);
        return ResponseInfo.error("信息存储失败，请检查提交信息");
    }

}