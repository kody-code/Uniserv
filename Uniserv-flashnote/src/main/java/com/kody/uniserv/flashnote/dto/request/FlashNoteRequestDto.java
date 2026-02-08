package com.kody.uniserv.flashnote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 闪记请求参数
 * </p>
 *
 * @author kody
 * @since 2026-02-08
 */
@Data
@Accessors(chain = true)
@Schema(description = "闪记请求参数")
public class FlashNoteRequestDto {

    @Schema(description = "闪记内容")
    @NotBlank(message = "闪记内容不能为空")
    private String content;

    @Schema(description = "标签")
    private List<String> tags;
}
