package com.kody.uniserv.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kody.uniserv.auth.dto.request.LoginRequestDto;
import com.kody.uniserv.auth.dto.request.RegisterRequestDto;
import com.kody.uniserv.auth.dto.response.LoginResponseDto;
import com.kody.uniserv.auth.dto.response.RegisterResponseDto;
import com.kody.uniserv.auth.dto.response.TokenInfo;
import com.kody.uniserv.auth.entity.SysUsers;
import com.kody.uniserv.auth.enums.SysUserRoles;
import com.kody.uniserv.auth.mapper.SysUsersMapper;
import com.kody.uniserv.auth.service.ISysUsersService;
import com.kody.uniserv.auth.utils.BCryptPasswordUtils;
import com.kody.uniserv.common.enums.ResultCode;
import com.kody.uniserv.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUsersServiceImpl extends ServiceImpl<SysUsersMapper, SysUsers> implements ISysUsersService {

    /**
     * 登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 参数校验
        if (loginRequestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "账号和密码不可为空");
        }

        SysUsers userInfo = getOne(new LambdaQueryWrapper<SysUsers>()
                .eq(SysUsers::getUsername, loginRequestDto.getAccount())
                .or()
                .eq(SysUsers::getEmail, loginRequestDto.getAccount()));

        if (userInfo == null) {
            log.error("[{}] 登录失败，账号：{}，原因：{}", MDC.get("traceId"), loginRequestDto.getAccount(), "用户不存在");
            throw new BusinessException(ResultCode.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        // 密码校验
        if (!BCryptPasswordUtils.verify(Objects.requireNonNull(loginRequestDto.getPassword()),
                userInfo.getPasswordHash())) {
            log.warn("[{}] 登录失败，用户名：{}，原因：{}", MDC.get("traceId"), loginRequestDto.getAccount(), "账户或密码错误");
            throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 更新登录时间
        baseMapper.updateLastLogin(userInfo.getUserId());

        // 登录
        UUID userId = userInfo.getUserId();
        StpUtil.login(userId.toString());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        log.info("[{}] 登录成功，用户ID：{}，账号：{}，Token：{}",
                MDC.get("traceId"), userId, loginRequestDto.getAccount(), tokenInfo.getTokenValue().substring(0, 8) + "****");

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
            log.error("[{}] 参数不可为空", MDC.get("traceId"));
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "参数不可为空");
        }

        // 查询用户信息
        SysUsers userInfo = getOne(new LambdaQueryWrapper<SysUsers>()
                .eq(SysUsers::getUsername, registerRequestDto.getUsername())
                .or()
                .eq(SysUsers::getEmail, registerRequestDto.getEmail()));

        // 用户已存在
        if (userInfo != null) {
            log.error("[{}] 用户已存在，用户名：{}，邮箱：{}", MDC.get("traceId"), registerRequestDto.getUsername(),
                    registerRequestDto.getEmail());
            throw new BusinessException(ResultCode.USER_ALREADY_EXIST.getCode(), "用户已存在");
        }

        try {
            // 密码加密
            String passwordHash = BCryptPasswordUtils.encrypt(Objects.requireNonNull(registerRequestDto.getPassword()));

            // 创建用户
            SysUsers users = new SysUsers();
            users.setUsername(registerRequestDto.getUsername())
                    .setEmail(registerRequestDto.getEmail())
                    .setPasswordHash(passwordHash)
                    .setRoles(isFirstUser() ? SysUserRoles.ADMIN.getRole() : SysUserRoles.USER.getRole());
            if (baseMapper.saveUser(users)) {
                log.info("[{}] 注册成功，用户名：{}，邮箱：{}，角色：{}",
                        MDC.get("traceId"), users.getUsername(), users.getEmail(), users.getRoles());

                return new RegisterResponseDto()
                        .setUsername(users.getUsername())
                        .setEmail(users.getEmail());
            } else {
                log.error("[{}] 注册失败，用户名：{}，邮箱：{}，原因：数据库保存失败", MDC.get("traceId"), users.getUsername(),
                        users.getEmail());
                throw new BusinessException(ResultCode.SERVER_ERROR.getCode(), "用户注册失败");
            }
        } catch (Exception e) {
            log.error("[{}] 注册异常，用户名：{}，邮箱：{}", MDC.get("traceId"), registerRequestDto.getUsername(),
                    registerRequestDto.getEmail(), e);
            throw new BusinessException(ResultCode.SERVER_ERROR.getCode(), "用户注册失败");
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
        return baseMapper.getRolesList(UUID.fromString(userId));
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        String userId = StpUtil.getLoginIdAsString();
        log.debug("[{}] 登出请求，用户ID：{}", MDC.get("traceId"), userId);

        if (!StpUtil.isLogin()) {
            String msg = "请先登录";
            log.warn("[{}] 登出失败，原因：{}", MDC.get("traceId"), msg);
            throw new BusinessException(ResultCode.SERVER_ERROR.getCode(), msg);
        }

        try {
            StpUtil.logout();
            log.info("[{}] 登出成功，用户ID：{}", MDC.get("traceId"), userId);
        } catch (Exception e) {
            log.error("[{}] 登出异常，用户ID：{}", MDC.get("traceId"), userId, e);
            throw new BusinessException(ResultCode.SERVER_ERROR.getCode(), "登出失败");
        }
    }
}
