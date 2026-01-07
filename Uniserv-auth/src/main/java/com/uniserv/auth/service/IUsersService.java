package com.uniserv.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uniserv.auth.dto.request.LoginRequestDto;
import com.uniserv.auth.dto.request.RegisterRequestDto;
import com.uniserv.auth.dto.response.LoginResponseDto;
import com.uniserv.auth.entity.Users;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
public interface IUsersService extends IService<Users> {

    /**
     * 登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    /**
     * 获取角色列表
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    List<String> getRoleList(String userId);

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
    boolean register(RegisterRequestDto registerRequestDto);
}
