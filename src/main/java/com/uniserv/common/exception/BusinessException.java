package com.uniserv.common.exception;

import com.uniserv.common.enums.ResultCode;
import lombok.Getter;

/**
 * <p>
 * 自定义业务异常
 * </p>
 *
 * @author kody
 * @since 2026-01-04
 */
@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
