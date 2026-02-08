package com.kody.uniserv.flashnote.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kody.uniserv.common.enums.ResultCode;
import com.kody.uniserv.common.exception.BusinessException;
import com.kody.uniserv.flashnote.dto.request.FlashNoteRequestDto;
import com.kody.uniserv.flashnote.dto.response.FlashNoteResponseDto;
import com.kody.uniserv.flashnote.entity.FlashNoteRecords;
import com.kody.uniserv.flashnote.mapper.FlashNoteRecordsMapper;
import com.kody.uniserv.flashnote.service.IFlashNoteRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 闪记表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Slf4j
@Service
public class FlashNoteRecordsServiceImpl extends ServiceImpl<FlashNoteRecordsMapper, FlashNoteRecords> implements IFlashNoteRecordsService {

    /**
     * 添加闪记
     *
     * @param requestDto 请求参数
     * @return 响应结果
     */
    @Override
    public boolean addNote(FlashNoteRequestDto requestDto) {
        if (requestDto == null) {
            log.error("[{}] 添加闪记失败，参数为空", MDC.get("traceId"));
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        String userId = StpUtil.getLoginIdAsString();
        log.debug("[{}] 添加闪记开始，用户ID：{}", MDC.get("traceId"), userId);
        FlashNoteRecords flashNoteRecords = new FlashNoteRecords()
                .setUserId(UUID.fromString(userId))
                .setContent(requestDto.getContent())
                .setTags(requestDto.getTags());
        return save(flashNoteRecords);
    }

    /**
     * 获取所有闪记
     *
     * @return 响应结果
     */
    @Override
    public List<FlashNoteResponseDto> getAllNotes() {
        String userId = StpUtil.getLoginIdAsString();
        log.debug("[{}] 获取所有闪记开始，用户ID：{}", MDC.get("traceId"), userId);
        List<FlashNoteRecords> list = list(new LambdaQueryWrapper<FlashNoteRecords>().eq(FlashNoteRecords::getUserId, UUID.fromString(userId)));
        return list.stream().map(item -> new FlashNoteResponseDto()
                .setId(item.getNoteId())
                .setContent(item.getContent())
                .setTags(item.getTags())).toList();
    }
}
