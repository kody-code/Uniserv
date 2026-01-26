package com.uniserv.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * 统一返回结果测试类
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@DisplayName("统一返回结果测试类")
public class ResultUtilsTest {

    @Test
    @DisplayName("测试成功返回结果")
    public void testSuccessWithoutData() {
        ResultUtils<String> result = ResultUtils.success();

        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("测试成功返回结果")
    public void testSuccessWithData() {
        String testData = "test data";
        ResultUtils<String> result = ResultUtils.success(testData);

        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertEquals(testData, result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("测试成功返回结果")
    public void testSuccessWithMessageAndData() {
        String testMessage = "custom message";
        Integer testData = 123;
        ResultUtils<Integer> result = ResultUtils.success(testMessage, testData);

        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertEquals(testData, result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("测试错误返回结果")
    public void testErrorWithCodeAndMessage() {
        int errorCode = 404;
        String errorMessage = "Not Found";
        ResultUtils<String> result = ResultUtils.error(errorCode, errorMessage);

        assertNotNull(result);
        assertEquals(errorCode, result.getCode());
        assertEquals(errorMessage, result.getMessage());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("测试错误返回结果")
    public void testErrorWithCodeMessageAndData() {
        int errorCode = 500;
        String errorMessage = "Server Error";
        String testData = "error data";
        ResultUtils<String> result = ResultUtils.error(errorCode, errorMessage, testData);

        assertNotNull(result);
        assertEquals(errorCode, result.getCode());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(testData, result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("测试泛型")
    public void testGenericTypes() {
        // 测试不同泛型类型
        ResultUtils<String> stringResult = ResultUtils.success("String Data");
        ResultUtils<Integer> intResult = ResultUtils.success(123);
        ResultUtils<Object> objectResult = ResultUtils.error(400, "Bad Request", new Object());

        assertNotNull(stringResult);
        assertNotNull(intResult);
        assertNotNull(objectResult);

        assertEquals("String Data", stringResult.getData());
        assertEquals(Integer.valueOf(123), intResult.getData());
        assertEquals(400, objectResult.getCode());
    }

    @Test
    @DisplayName("测试时间戳")
    public void testTimestampIsCurrent() {
        long beforeCall = System.currentTimeMillis();
        ResultUtils<String> result = ResultUtils.success("test");
        long afterCall = System.currentTimeMillis();

        assertNotNull(result.getTimestamp());
        assertTrue(result.getTimestamp() >= beforeCall && result.getTimestamp() <= afterCall);
    }

    @Test
    @DisplayName("测试相等性")
    public void testEqualityAndHashCode() {
        ResultUtils<String> result1 = new ResultUtils<>(0, "操作成功", "data", 123456789L);
        ResultUtils<String> result2 = new ResultUtils<>(0, "操作成功", "data", 123456789L);
        ResultUtils<String> result3 = new ResultUtils<>(0, "操作成功", "different_data", 123456789L);

        // 相同字段值的对象应该相等
        assertEquals(result1, result2);
        assertEquals(result1.hashCode(), result2.hashCode());

        // 不同字段值的对象不应该相等
        assertNotEquals(result1, result3);
    }
}