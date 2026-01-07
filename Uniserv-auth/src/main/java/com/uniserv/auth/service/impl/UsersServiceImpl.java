package com.uniserv.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.auth.dot.request.LoginRequestDto;
import com.uniserv.auth.dot.response.LoginResponseDto;
import com.uniserv.auth.entity.Users;
import com.uniserv.auth.mapper.UsersMapper;
import com.uniserv.auth.service.IUsersService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.exception.BusinessException;
import com.uniserv.common.utils.BCryptPasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

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
        Users userInfo = baseMapper.selectOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, loginRequestDto.getUsername())
        );
        if (userInfo == null) {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "用户不存在");
        }

        // 密码校验
        if (!BCryptPasswordUtils.verify(loginRequestDto.getPassword(), userInfo.getPasswordHash())) {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "账户或密码错误");
        }

        // 更新登录时间
        baseMapper.updateLastLogin(userInfo.getId());

        // 登录
        UUID userId = userInfo.getId();
        StpUtil.login(userId.toString());
        return new LoginResponseDto()
                .setUserId(userInfo.getId())
                .setUsername(userInfo.getUsername())
                .setEmail(userInfo.getEmail())
                .setTokenInfo(StpUtil.getTokenInfo());
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
}
