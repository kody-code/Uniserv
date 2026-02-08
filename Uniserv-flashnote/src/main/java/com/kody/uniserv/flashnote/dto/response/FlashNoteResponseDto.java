package com.kody.uniserv.flashnote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 闪记请求响应
 * </p>
 *
 * @author kody
 * @since 2026-02-08
 */
@Data
@Accessors(chain = true)
@Schema(description = "闪记请求响应")
public class FlashNoteResponseDto {

    /**
     * 闪记ID
     */
    @Schema(description = "闪记ID")
    private UUID id;

    /**
     * 闪记内容
     */
    @Schema(description = "闪记内容")
    private String content;

    /**
     * 闪记标签
     */
    @Schema(description = "闪记标签")
    private List<String> tags;
}
