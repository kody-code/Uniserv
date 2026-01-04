package com.uniserv.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uniserv.auth.dto.request.LoginRequestDto;
import com.uniserv.auth.dto.response.LoginResponseDto;
import com.uniserv.auth.entity.Users;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kody
 * @since 2026-01-04
 */
public interface IUsersService extends IService<Users> {

    /**
     * 登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
