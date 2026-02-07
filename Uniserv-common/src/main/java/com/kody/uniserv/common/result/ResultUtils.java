package com.kody.uniserv.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 统一返回结果
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtils<T> {

    /**
     * 状态码 0-成功，其他为错误
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;


    /**
     * 成功返回
     *
     * @return Result
     */
    public static <T> ResultUtils<T> success() {
        return new ResultUtils<>(0, "操作成功", null, System.currentTimeMillis());
    }

    /**
     * 成功返回带数据
     *
     * @param data 数据
     * @return Result
     */
    public static <T> ResultUtils<T> success(T data) {
        return new ResultUtils<>(0, "操作成功", data, System.currentTimeMillis());
    }

    /**
     * 成功返回带消息和数据
     *
     * @param message 消息
     * @param data    数据
     * @return Result
     */
    public static <T> ResultUtils<T> success(String message, T data) {
        return new ResultUtils<>(0, message, data, System.currentTimeMillis());
    }

    /**
     * 错误返回
     *
     * @param code    状态码
     * @param message 响应信息
     * @return Result
     */
    public static <T> ResultUtils<T> error(int code, String message) {
        return new ResultUtils<>(code, message, null, System.currentTimeMillis());
    }

    /**
     * 错误返回带数据
     *
     * @param code    状态码
     * @param message 响应信息
     * @param data    数据
     * @return Result
     */
    public static <T> ResultUtils<T> error(int code, String message, T data) {
        return new ResultUtils<>(code, message, data, System.currentTimeMillis());
    }
}