package com.uniserv.cipher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.entity.PasswordInfo;
import com.uniserv.cipher.mapper.PasswordInfoMapper;
import com.uniserv.cipher.service.IPasswordInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 密码管理信息 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Service
public class PasswordInfoServiceImpl extends ServiceImpl<PasswordInfoMapper, PasswordInfo> implements IPasswordInfoService {

}
