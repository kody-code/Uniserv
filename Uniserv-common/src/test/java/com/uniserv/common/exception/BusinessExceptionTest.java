package com.uniserv.common.exception;

import com.uniserv.common.enums.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * BusinessException 单元测试
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@DisplayName("BusinessException 单元测试")
public class BusinessExceptionTest {

    @Test
    @DisplayName("通过 ResultCode 构造异常 - 验证 code 和 message")
    public void testConstructorWithResultCode() {
        // 使用 ResultCode 构造异常
        BusinessException exception = new BusinessException(ResultCode.BUSINESS_ERROR);

        // 验证异常消息来自 ResultCode
        assertEquals(ResultCode.BUSINESS_ERROR.getMessage(), exception.getMessage(),
                "异常消息应该与 ResultCode 中的消息一致");

        // 验证异常代码来自 ResultCode
        assertEquals(ResultCode.BUSINESS_ERROR.getCode(), exception.getCode(),
                "异常代码应该与 ResultCode 中的代码一致");
    }

    @Test
    @DisplayName("通过 code 和 message 构造异常 - 验证自定义属性")
    public void testConstructorWithCodeAndMessage() {
        // 使用自定义 code 和 message 构造异常
        int expectedCode = 9999;
        String expectedMessage = "自定义业务异常消息";

        BusinessException exception = new BusinessException(expectedCode, expectedMessage);

        // 验证异常消息
        assertEquals(expectedMessage, exception.getMessage(),
                "异常消息应该与传入的消息一致");

        // 验证异常代码
        assertEquals(expectedCode, exception.getCode(),
                "异常代码应该与传入的代码一致");
    }

    @Test
    @DisplayName("验证继承关系")
    public void testInheritance() {
        // 验证 BusinessException 继承自 RuntimeException
        BusinessException exception = new BusinessException(ResultCode.BUSINESS_ERROR);

        assertInstanceOf(RuntimeException.class, exception, "BusinessException 应该是 RuntimeException 的实例");
    }

    @Test
    @DisplayName("验证不同 ResultCode 的异常创建")
    public void testDifferentResultCodes() {
        // 测试不同 ResultCode 构造异常
        BusinessException authError = new BusinessException(ResultCode.AUTH_ERROR);
        BusinessException paramError = new BusinessException(ResultCode.PARAM_ERROR);

        // 验证每个异常都有正确的 code 和 message
        assertEquals(ResultCode.AUTH_ERROR.getCode(), authError.getCode());
        assertEquals(ResultCode.AUTH_ERROR.getMessage(), authError.getMessage());

        assertEquals(ResultCode.PARAM_ERROR.getCode(), paramError.getCode());
        assertEquals(ResultCode.PARAM_ERROR.getMessage(), paramError.getMessage());

        // 验证两个异常的 code 不同
        assertNotEquals(authError.getCode(), paramError.getCode());
    }

    @Test
    @DisplayName("验证自定义 code 和 ResultCode code 不冲突")
    public void testCustomCodeVsResultCode() {
        // 创建一个具有自定义 code 的异常，该 code 与某个 ResultCode 相同
        int sameCode = ResultCode.SYSTEM_ERROR.getCode(); // 获取已存在的 code
        String customMessage = "自定义消息";

        BusinessException customException = new BusinessException(sameCode, customMessage);
        BusinessException resultCodeException = new BusinessException(ResultCode.SYSTEM_ERROR);

        // 验证即使 code 相同，消息也可能不同
        assertEquals(sameCode, customException.getCode());
        assertEquals(customMessage, customException.getMessage());

        assertEquals(sameCode, resultCodeException.getCode());
        assertNotEquals(customException.getMessage(), resultCodeException.getMessage());
    }
}
