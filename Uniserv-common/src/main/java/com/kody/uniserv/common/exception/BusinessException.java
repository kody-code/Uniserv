package com.kody.uniserv.common.exception;

import com.kody.uniserv.common.enums.ResultCode;
import lombok.Getter;

/**
 * <p>
 * 业务异常
 * </p>
 *
 * @author kody
 * @since 2026-02-07
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
