package com.ark.ranjith.QuoraReactiveApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDTO {
    private String id;
    private String targetId;
    private String targetType;
    private Boolean isLike; // "question" or "answer"
    private String createdAt;
}
