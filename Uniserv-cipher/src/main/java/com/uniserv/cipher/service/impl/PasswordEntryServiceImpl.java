package com.uniserv.cipher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.entity.PasswordEntry;
import com.uniserv.cipher.mapper.PasswordEntryMapper;
import com.uniserv.cipher.service.IPasswordEntryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 密码条目表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Service
public class PasswordEntryServiceImpl extends ServiceImpl<PasswordEntryMapper, PasswordEntry> implements IPasswordEntryService {

}
