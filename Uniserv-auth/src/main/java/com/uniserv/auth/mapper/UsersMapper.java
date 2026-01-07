package com.uniserv.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uniserv.auth.entity.Users;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 更新登录时间
     *
     * @param userId 用户 ID
     * @return 更新结果
     */
    int updateLastLogin(UUID userId);

    /**
     * 获取角色列表
     *
     * @param userId 用户 ID
     * @return 角色列表
     */

    List<String> getRoles(UUID userId);
}
