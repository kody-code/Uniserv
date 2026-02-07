package com.kody.uniserv.app.exception;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.kody.uniserv.common.enums.ResultCode;
import com.kody.uniserv.common.exception.BusinessException;
import com.kody.uniserv.common.result.ResultUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author kody
 * @since 2026-02-06
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(BusinessException.class)
    public static ResultUtils<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        return ResultUtils.<Void>error(e.getCode(), e.getMessage());
    }

    /**
     * 处理未登录异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(NotLoginException.class)
    public ResultUtils<?> handleNotLoginException(NotLoginException e) {
        log.error("未登录异常: {}", e.getMessage(), e);
        return ResultUtils.<Void>error(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage());
    }

    /**
     * 处理账号被封禁异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(DisableServiceException.class)
    public ResultUtils<?> handleDisableServiceException(DisableServiceException e) {
        log.error("账号已被封禁: {}", e.getMessage(), e);
        return ResultUtils.<Void>error(ResultCode.USER_DISABLE.getCode(), ResultCode.USER_DISABLE.getMessage());
    }

    /**
     * 处理角色异常
     *
     * @param e 角色异常
     * @return 错误信息
     */
    @ExceptionHandler(NotRoleException.class)
    public ResultUtils<?> handleNotRoleException(NotRoleException e) {
        log.error("角色异常: {}", e.getMessage(), e);
        return ResultUtils.<Void>error(ResultCode.USER_NOT_AUTHORIZED.getCode(), "无权访问当前页面");
    }

    /**
     * 处理RequestBody参数校验异常（POST/PUT请求，@Valid/@Validated + 实体类注解）
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultUtils<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        // 遍历字段错误，拼接提示
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
        }
        // 去除末尾多余的分号
        String finalMsg = !errorMsg.isEmpty() ? errorMsg.substring(0, errorMsg.length() - 1) : "参数校验失败";
        log.error("RequestBody参数校验异常: {}", finalMsg, e);
        return ResultUtils.<Void>error(ResultCode.PARAM_ERROR.getCode(), finalMsg);
    }

    /**
     * 处理RequestParam/PathVariable参数校验异常（GET请求，@Validated + 方法参数注解）
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultUtils<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder errorMsg = new StringBuilder();
        Iterator<ConstraintViolation<?>> iterator = violations.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<?> violation = iterator.next();
            // 获取参数名和提示信息
            String field = violation.getPropertyPath().toString();
            // 截取方法名后的参数名（例如：addUser.username -> username）
            if (field.contains(".")) {
                field = field.substring(field.lastIndexOf(".") + 1);
            }
            errorMsg.append(field).append(":").append(violation.getMessage());
            if (iterator.hasNext()) {
                errorMsg.append(";");
            }
        }
        String finalMsg = !errorMsg.isEmpty() ? errorMsg.toString() : "参数校验失败";
        log.error("RequestBody参数校验异常: {}", finalMsg, e);
        return ResultUtils.<Void>error(ResultCode.PARAM_ERROR.getCode(), finalMsg);
    }

    /**
     * 处理系统异常（兜底）
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    public ResultUtils<?> handleException(Exception e) {
        log.error("系统异常", e);
        String msg = ResultCode.SERVER_ERROR.getMessage();
        return ResultUtils.<Void>error(ResultCode.SERVER_ERROR.getCode(), msg);
    }
}
