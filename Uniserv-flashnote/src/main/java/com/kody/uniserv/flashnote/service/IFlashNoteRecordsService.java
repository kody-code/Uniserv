package com.kody.uniserv.flashnote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kody.uniserv.flashnote.dto.request.FlashNoteRequestDto;
import com.kody.uniserv.flashnote.dto.response.FlashNoteResponseDto;
import com.kody.uniserv.flashnote.entity.FlashNoteRecords;

import java.util.List;

/**
 * <p>
 * 闪记表 服务类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public interface IFlashNoteRecordsService extends IService<FlashNoteRecords> {

    /**
     * 添加闪记
     *
     * @param requestDto 请求参数
     * @return 响应结果
     */
    boolean addNote(FlashNoteRequestDto requestDto);

    /**
     * 获取所有闪记
     *
     * @return 响应结果
     */
    List<FlashNoteResponseDto> getAllNotes();
}
