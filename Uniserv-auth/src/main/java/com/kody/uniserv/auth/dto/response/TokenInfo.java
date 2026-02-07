package com.kody.uniserv.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 令牌信息
 * </p>
 *
 * @author kody
 * @since 2026-02-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "令牌信息")
public class TokenInfo {

    /**
     * 令牌名称
     */
    @JsonProperty("token_name")
    @Schema(description = "令牌名称", example = "Bearer")
    private String tokenName;

    /**
     * 令牌值
     */
    @JsonProperty("token_value")
    @Schema(description = "令牌值", example = "ccf51222-2e58-4fb9-ac24-c92b1bf730cd")
    private String tokenValue;
}
