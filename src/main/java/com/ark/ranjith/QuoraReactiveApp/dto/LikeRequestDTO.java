package com.ark.ranjith.QuoraReactiveApp.dto;

import com.ark.ranjith.QuoraReactiveApp.enums.TargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequestDTO {

    @NotBlank(message = "Target ID is required")
    private String targetId;

    @NotBlank(message = "User ID is required")
    private TargetType targetType; // QUESTION or ANSWER or COMMENT

    @NotNull(message = "Like/Dislike action is required")
    private Boolean isLike; // "like" or "dislike"

}
