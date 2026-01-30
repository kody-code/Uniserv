package com.uniserv.common.exception;

import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.utils.ResultUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * 全局异常处理测试类
 * </p>
 *
 * @author kody
 * @since 2026-01-30
 */
@DisplayName("全局异常处理测试类")
public class GlobalExceptionHandlerTest {

    /**
     * 测试处理自定义业务异常
     */
    @Test
    @DisplayName("处理自定义业务异常")
    public void testHandleBusinessException() {
        BusinessException exception = new BusinessException(1000, "业务异常");
        ResultUtils<?> result = GlobalExceptionHandler.handleBusinessException(exception);

        assertNotNull(result);
        assertEquals(1000, result.getCode());
        assertEquals("业务异常", result.getMessage());
        assertNotNull(result.getTimestamp());
    }

    /**
     * 测试处理不同类型的业务异常
     */
    @Test
    @DisplayName("测试处理不同类型的业务异常")
    void testHandleBusinessExceptionWithResultCode() {
        // 使用 ResultCode 创建异常
        BusinessException businessException = new BusinessException(ResultCode.BUSINESS_ERROR);

        // 调用处理方法
        ResultUtils<?> result = GlobalExceptionHandler.handleBusinessException(businessException);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResultCode.BUSINESS_ERROR.getCode(), result.getCode()); // 1000
        assertEquals(ResultCode.BUSINESS_ERROR.getMessage(), result.getMessage()); // "业务异常"
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    /**
     * 测试异常消息包含堆栈跟踪的情况
     */
    @Test
    @DisplayName("异常消息包含堆栈跟踪的情况")
    void testHandleBusinessExceptionWithStackTrace() {
        BusinessException businessException = new BusinessException(500, "内部服务器错误");

        // 设置异常的堆栈跟踪
        businessException.setStackTrace(new StackTraceElement[]{
                new StackTraceElement("TestClass", "testMethod", "TestClass.java", 10)
        });

        // 调用处理方法
        ResultUtils<?> result = GlobalExceptionHandler.handleBusinessException(businessException);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertEquals("内部服务器错误", result.getMessage());
    }

    /**
     * 测试空消息异常情况
     */
    @Test
    @DisplayName("测试空消息异常情况")
    void testHandleBusinessExceptionWithEmptyMessage() {
        BusinessException businessException = new BusinessException(400, "");

        ResultUtils<?> result = GlobalExceptionHandler.handleBusinessException(businessException);

        assertNotNull(result);
        assertEquals(400, result.getCode());
        assertEquals("", result.getMessage());
        assertNull(result.getData());
    }


    /**
     * 测试日志记录功能（通过反射验证）
     */
    @Test
    @DisplayName("测试日志记录功能")
    void testLoggingIsConfigured() {
        // 检查是否添加了 @Slf4j 注解的效果
        Field[] fields = GlobalExceptionHandler.class.getDeclaredFields();
        boolean hasLogField = false;

        for (Field field : fields) {
            if (field.getType() == Logger.class) {
                hasLogField = true;
                break;
            }
        }

        assertTrue(hasLogField, "GlobalExceptionHandler should have a logger field");
    }
}
