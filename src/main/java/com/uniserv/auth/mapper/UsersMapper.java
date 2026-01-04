package com.uniserv.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uniserv.auth.entity.Users;

import java.util.UUID;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author kody
 * @since 2026-01-04
 */
public interface UsersMapper extends BaseMapper<Users> {

    int updateLastLogin(UUID userId);
}
