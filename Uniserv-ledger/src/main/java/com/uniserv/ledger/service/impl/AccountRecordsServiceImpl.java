package com.uniserv.ledger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.ledger.entity.AccountRecords;
import com.uniserv.ledger.mapper.AccountRecordsMapper;
import com.uniserv.ledger.service.IAccountRecordsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户记录表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Service
public class AccountRecordsServiceImpl extends ServiceImpl<AccountRecordsMapper, AccountRecords> implements IAccountRecordsService {

}
