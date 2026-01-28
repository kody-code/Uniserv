package com.uniserv.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.auth.dto.request.LoginRequestDto;
import com.uniserv.auth.dto.request.RegisterRequestDto;
import com.uniserv.auth.dto.response.LoginResponseDto;
import com.uniserv.auth.dto.response.RegisterResponseDto;
import com.uniserv.auth.dto.response.TokenInfo;
import com.uniserv.auth.entity.SysUsers;
import com.uniserv.auth.enums.SysUserRoles;
import com.uniserv.auth.mapper.SysUsersMapper;
import com.uniserv.auth.service.ISysUsersService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.exception.BusinessException;
import com.uniserv.common.utils.BCryptPasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@Slf4j
@Service
public class SysUsersServiceImpl extends ServiceImpl<SysUsersMapper, SysUsers> implements ISysUsersService {

    /**
     * 登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 参数校验
        if (loginRequestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "账号和密码不可为空");
        }

        // 查询用户信息
        SysUsers userInfo = baseMapper.selectOne(new LambdaQueryWrapper<SysUsers>()
                .eq(SysUsers::getUsername, loginRequestDto.getUsername())
        );
        if (userInfo == null) {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "用户不存在");
        }

        // 密码校验
        if (!BCryptPasswordUtils.verify(Objects.requireNonNull(loginRequestDto.getPassword()), userInfo.getPasswordHash())) {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "账户或密码错误");
        }

        // 更新登录时间
        baseMapper.updateLastLogin(userInfo.getUserId());

        // 登录
        UUID userId = userInfo.getUserId();
        StpUtil.login(userId.toString());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return new LoginResponseDto()
                .setUserId(userId)
                .setUsername(userInfo.getUsername())
                .setEmail(userInfo.getEmail())
                .setTokenInfo(new TokenInfo()
                        .setTokenName(tokenInfo.getTokenName())
                        .setTokenValue(tokenInfo.getTokenValue()));
    }

    /**
     * 判断是否是第一个用户
     *
     * @return 是否是第一个用户
     */
    @Override
    public boolean isFirstUser() {
        return count() == 0;
    }

    /**
     * 注册
     *
     * @param registerRequestDto 注册参数
     * @return 注册结果
     */
    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        // 参数校验
        if (registerRequestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "参数不可为空");
        }

        // 查询用户信息
        SysUsers userInfo = getOne(new LambdaQueryWrapper<SysUsers>()
                .eq(SysUsers::getUsername, registerRequestDto.getUsername())
                .or()
                .eq(SysUsers::getEmail, registerRequestDto.getEmail()));

        // 用户已存在
        if (userInfo != null) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "用户已存在");
        }

        // 密码加密
        String passwordHash = BCryptPasswordUtils.encrypt(Objects.requireNonNull(registerRequestDto.getPassword()));

        // 创建用户
        SysUsers users = new SysUsers();
        users.setUsername(registerRequestDto.getUsername())
                .setEmail(registerRequestDto.getEmail())
                .setPasswordHash(passwordHash)
                .setRoles(isFirstUser() ? SysUserRoles.ADMIN : SysUserRoles.USER);
        if (baseMapper.saveUser(users)) {
            return new RegisterResponseDto()
                    .setUsername(users.getUsername())
                    .setEmail(users.getEmail());
        } else {
            log.error("用户注册失败: {}", users);
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "用户注册失败");
        }
    }

    /**
     * 获取角色列表
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(String userId) {
        if (StpUtil.isLogin()) {
            return baseMapper.getRoles(UUID.fromString(userId));
        } else {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "请先登录");
        }
    }

    /**
     * 登出
     *
     * @return 登出结果
     */
    @Override
    public boolean logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return true;
        } else {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "请先登录");
        }
    }
}
