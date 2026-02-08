package com.kody.uniserv.app.controller.flashnote;

import com.kody.uniserv.common.enums.ResultCode;
import com.kody.uniserv.common.result.ResultUtils;
import com.kody.uniserv.flashnote.dto.request.FlashNoteRequestDto;
import com.kody.uniserv.flashnote.dto.response.FlashNoteResponseDto;
import com.kody.uniserv.flashnote.service.IFlashNoteRecordsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 闪记控制器
 * </p>
 *
 * @author kody
 * @since 2026-02-08
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
@Tag(name = "闪记", description = "闪记接口")
public class FlashNoteController {

    /**
     * 闪记服务
     */
    private final IFlashNoteRecordsService flashNoteRecordsService;

    /**
     * 添加闪记
     *
     * @param requestDto 请求参数
     * @return 响应结果
     */
    @PostMapping("/add")
    public ResultUtils<String> addNote(@Valid @RequestBody FlashNoteRequestDto requestDto) {
        log.info("[{}] 添加闪记开始", MDC.get("traceId"));
        if (flashNoteRecordsService.addNote(requestDto)) {
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMessage());
    }

    /**
     * 获取所有闪记
     *
     * @return 响应结果
     */
    @PostMapping("/list")
    public ResultUtils<List<FlashNoteResponseDto>> getAllNotes() {
        return ResultUtils.success(flashNoteRecordsService.getAllNotes());
    }
}
