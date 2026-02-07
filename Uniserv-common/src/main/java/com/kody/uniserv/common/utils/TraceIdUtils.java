package com.kody.uniserv.common.utils;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * <p>
 * TraceId工具类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public class TraceIdUtils {
    /**
     * TraceId在MDC中的key
     */
    public static final String TRACE_ID_KEY = "traceId";

    /**
     * 生成并设置TraceId到MDC
     *
     * @return 生成的TraceId
     */
    public static String generateAndSetTraceId() {
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(TRACE_ID_KEY, traceId);
        return traceId;
    }

    /**
     * 获取当前MDC中的TraceId
     *
     * @return TraceId
     */
    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID_KEY);
        return traceId == null ? "" : traceId;
    }

    /**
     * 设置指定的TraceId到MDC（用于跨服务调用透传）
     *
     * @param traceId 外部传入的TraceId
     */
    public static void setTraceId(String traceId) {
        if (traceId != null && !traceId.isEmpty()) {
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }

    /**
     * 移除MDC中的TraceId（请求结束时清理）
     */
    public static void removeTraceId() {
        if (MDC.get(TRACE_ID_KEY) != null) {
            MDC.remove(TRACE_ID_KEY);
        }
    }
}
