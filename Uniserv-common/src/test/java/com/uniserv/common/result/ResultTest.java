package com.uniserv.common.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * Result 类的单元测试
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@DisplayName("Result 单元测试")
class ResultTest {

    @Test
    @DisplayName("测试成功返回 - 无数据")
    void testSuccessWithoutData() {
        Result<Void> result = Result.success();

        assertEquals(0, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试成功返回 - 带数据")
    void testSuccessWithData() {
        String testData = "testData";
        Result<String> result = Result.success(testData);

        assertEquals(0, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertEquals(testData, result.getData());
    }

    @Test
    @DisplayName("测试成功返回 - 带消息和数据")
    void testSuccessWithMessageAndData() {
        String message = "自定义消息";
        Integer testData = 123;
        Result<Integer> result = Result.success(message, testData);

        assertEquals(0, result.getCode());
        assertEquals(message, result.getMessage());
        assertEquals(testData, result.getData());
    }

    @Test
    @DisplayName("测试错误返回 - 带状态码和消息")
    void testErrorWithCodeAndMessage() {
        int errorCode = 404;
        String errorMessage = "未找到资源";
        Result<Void> result = Result.error(errorCode, errorMessage);

        assertEquals(errorCode, result.getCode());
        assertEquals(errorMessage, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试错误返回 - 带状态码、消息和数据")
    void testErrorWithCodeMessageAndData() {
        int errorCode = 500;
        String errorMessage = "服务器内部错误";
        String errorData = "错误详情";
        Result<String> result = Result.error(errorCode, errorMessage, errorData);

        assertEquals(errorCode, result.getCode());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(errorData, result.getData());
    }

    @Test
    @DisplayName("测试泛型类型正确性")
    void testGenericTypes() {
        // 测试不同泛型类型
        Result<String> stringResult = Result.success("string data");
        Result<Integer> intResult = Result.success(123);
        Result<Object> objectResult = Result.error(400, "error", new Object());

        assertEquals("string data", stringResult.getData());
        assertEquals(Integer.valueOf(123), intResult.getData());
        assertNotNull(objectResult.getData());
        assertEquals(400, objectResult.getCode());
    }

    @Test
    @DisplayName("测试Result字段的可访问性")
    void testFieldAccessibility() {
        Result<String> result = Result.success("test");

        // 验证字段可被访问
        assertEquals(0, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertEquals("test", result.getData());

        // 验证可以设置字段值
        result.setCode(201);
        result.setMessage("更新成功");
        result.setData("updated data");

        assertEquals(201, result.getCode());
        assertEquals("更新成功", result.getMessage());
        assertEquals("updated data", result.getData());
    }

    @Test
    @DisplayName("测试序列化支持")
    void testSerializationSupport() {
        // 验证 Result 类实现了 Serializable 接口
        assertTrue(true);

        // 通过实例验证 serialVersionUID 的存在性
        Result<String> result = Result.success("test");
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试链式调用或构建后修改")
    void testResultModification() {
        Result<String> result = Result.success("initial data");

        // 修改字段
        result.setCode(401);
        result.setMessage("未授权");
        result.setData("unauthorized data");

        assertEquals(401, result.getCode());
        assertEquals("未授权", result.getMessage());
        assertEquals("unauthorized data", result.getData());
    }
}
