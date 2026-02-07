package com.kody.uniserv.app.config;

import cn.dev33.satoken.stp.StpInterface;
import com.kody.uniserv.auth.service.ISysUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 自定义权限验证接口扩展
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    /**
     * 获取用户权限列表
     */
    private final ISysUsersService usersService;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        String userId = String.valueOf(o);
        return usersService.getRoleList(userId);
    }
}
