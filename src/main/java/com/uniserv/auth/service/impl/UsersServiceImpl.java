package com.uniserv.auth.service.impl;

import com.uniserv.auth.entity.Users;
import com.uniserv.auth.mapper.UsersMapper;
import com.uniserv.auth.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-04
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
