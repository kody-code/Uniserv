package com.kody.uniserv.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kody.uniserv.auth.dto.request.LoginRequestDto;
import com.kody.uniserv.auth.dto.request.RegisterRequestDto;
import com.kody.uniserv.auth.dto.response.LoginResponseDto;
import com.kody.uniserv.auth.dto.response.RegisterResponseDto;
import com.kody.uniserv.auth.entity.SysUsers;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public interface ISysUsersService extends IService<SysUsers> {

    /**
     * 登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    /**
     * 判断是否是第一个用户
     *
     * @return 是否是第一个用户
     */
    boolean isFirstUser();

    /**
     * 注册
     *
     * @param registerRequestDto 注册参数
     * @return 注册结果
     */
    RegisterResponseDto register(RegisterRequestDto registerRequestDto);

    /**
     * 获取角色列表
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    List<String> getRoleList(String userId);

    /**
     * 登出
     */
    void logout();
}
