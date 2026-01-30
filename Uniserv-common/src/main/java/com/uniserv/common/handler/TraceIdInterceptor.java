package com.uniserv.common.handler;

import com.uniserv.common.utils.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>
 * TraceIdInterceptor 拦截器
 * </p>
 *
 * @author kody
 * @since 2026-01-28
 */
public class TraceIdInterceptor implements HandlerInterceptor {
    /**
     * 前端/上游服务透传TraceId的请求头key
     */
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

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
        TraceIdUtils.removeTraceId();
    }
}
