package com.kody.uniserv.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kody.uniserv.auth.entity.SysUsers;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public interface SysUsersMapper extends BaseMapper<SysUsers> {

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

    List<String> getRolesList(UUID userId);

    /**
     * 保存用户
     *
     * @param users 用户信息
     * @return 保存结果
     */
    boolean saveUser(SysUsers users);

}
