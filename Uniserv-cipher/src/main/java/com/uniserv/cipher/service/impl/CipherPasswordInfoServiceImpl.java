package com.uniserv.cipher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.entity.CipherPasswordInfo;
import com.uniserv.cipher.mapper.CipherPasswordInfoMapper;
import com.uniserv.cipher.service.ICipherPasswordInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 密码管理信息表（存储主密码相关） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Service
public class CipherPasswordInfoServiceImpl extends ServiceImpl<CipherPasswordInfoMapper, CipherPasswordInfo> implements ICipherPasswordInfoService {

}
