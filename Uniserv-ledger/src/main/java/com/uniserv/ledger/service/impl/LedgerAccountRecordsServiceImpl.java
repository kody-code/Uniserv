package com.uniserv.ledger.service.impl;

import com.uniserv.ledger.entity.LedgerAccountRecords;
import com.uniserv.ledger.mapper.LedgerAccountRecordsMapper;
import com.uniserv.ledger.service.ILedgerAccountRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户记录表（收支明细） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class LedgerAccountRecordsServiceImpl extends ServiceImpl<LedgerAccountRecordsMapper, LedgerAccountRecords> implements ILedgerAccountRecordsService {

}
