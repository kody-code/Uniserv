package com.uniserv.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * TraceIdUtilsTest 链路追踪工具类测试类
 * </p>
 *
 * @author kody
 * @since 2026-01-28
 */
@DisplayName("TraceIdUtilsTest 链路追踪工具类测试类")
public class TraceIdUtilsTest {

    /**
     * 测试生成并设置TraceId
     */
    @Test
    @DisplayName("测试生成并设置TraceId")
    public void testGenerateAndSetTraceId() {
        // 清理MDC
        MDC.clear();
        String traceId = TraceIdUtils.generateAndSetTraceId();
        assertNotNull(traceId);
        assertEquals(traceId, TraceIdUtils.getTraceId());
        assertEquals(traceId, MDC.get(TraceIdUtils.TRACE_ID_KEY));
    }

    /**
     * 测试获取当前MDC中的TraceId
     */
    @Test
    public void testSetAndGetTraceId() {
        MDC.clear();
        String customTraceId = "test-trace-123456";
        TraceIdUtils.setTraceId(customTraceId);
        assertEquals(customTraceId, TraceIdUtils.getTraceId());
    }

    /**
     * 测试移除MDC中的TraceId
     */
    @Test
    public void testRemoveTraceId() {
        MDC.clear();
        TraceIdUtils.generateAndSetTraceId();
        assertFalse(TraceIdUtils.getTraceId().isEmpty());
        TraceIdUtils.removeTraceId();
        assertTrue(TraceIdUtils.getTraceId().isEmpty());
    }
}
