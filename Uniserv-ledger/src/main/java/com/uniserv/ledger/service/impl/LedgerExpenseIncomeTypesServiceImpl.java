package com.uniserv.ledger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.ledger.entity.LedgerExpenseIncomeTypes;
import com.uniserv.ledger.mapper.LedgerExpenseIncomeTypesMapper;
import com.uniserv.ledger.service.ILedgerExpenseIncomeTypesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收入支出类型表（基础分类） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Service
public class LedgerExpenseIncomeTypesServiceImpl extends ServiceImpl<LedgerExpenseIncomeTypesMapper, LedgerExpenseIncomeTypes> implements ILedgerExpenseIncomeTypesService {

}
