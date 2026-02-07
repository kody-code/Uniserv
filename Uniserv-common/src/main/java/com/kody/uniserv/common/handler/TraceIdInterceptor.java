package com.kody.uniserv.common.handler;

import com.kody.uniserv.common.utils.TraceIdUtils;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>
 * 链路追踪拦截器
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public class TraceIdInterceptor implements HandlerInterceptor {
    /**
     * 前端/上游服务透传TraceId的请求头key
     */
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    private static final String CLIENT_IP_KEY = "clientIp";

    /**
     * 请求处理之前执行，返回false表示请求中断
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     * @return 是否继续处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // 优先从请求头获取TraceId（跨服务调用时透传），不存在则生成
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null || traceId.isEmpty()) {
            traceId = TraceIdUtils.generateAndSetTraceId();
        } else {
            TraceIdUtils.setTraceId(traceId);
        }

        String clientIp = getClientIpAddress(request);
        MDC.put(CLIENT_IP_KEY, clientIp);

        // 将TraceId写入响应头，方便前端/下游服务获取
        response.setHeader(TRACE_ID_HEADER, traceId);
        return true;
    }

    /**
     * 请求处理完成后执行
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     * @param ex       异常对象
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        // 请求结束后清理MDC中的TraceId，避免线程复用导致脏数据
        MDC.remove(CLIENT_IP_KEY);
        TraceIdUtils.removeTraceId();
    }

    /**
     * 获取客户端IP地址
     *
     * @param request 请求对象
     * @return 客户端IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        // 优先从 X-Forwarded-For 获取
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            // X-Forwarded-For可能包含多个IP，取第一个非unknown的有效IP
            int idx = xForwardedFor.indexOf(",");
            if (idx > 0) {
                return xForwardedFor.substring(0, idx).trim();
            } else {
                return xForwardedFor.trim();
            }
        }

        // 从 X-Real-IP 获取
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp.trim();
        }

        // 最后使用标准方法获取
        return request.getRemoteAddr();
    }
}
