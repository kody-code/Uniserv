package com.uniserv.ledger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.ledger.entity.ExpenseIncomeTypes;
import com.uniserv.ledger.mapper.ExpenseIncomeTypesMapper;
import com.uniserv.ledger.service.IExpenseIncomeTypesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收入支出类型表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Service
public class ExpenseIncomeTypesServiceImpl extends ServiceImpl<ExpenseIncomeTypesMapper, ExpenseIncomeTypes> implements IExpenseIncomeTypesService {

}
