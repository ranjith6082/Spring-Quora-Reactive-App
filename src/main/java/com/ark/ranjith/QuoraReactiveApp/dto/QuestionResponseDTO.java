package com.ark.ranjith.QuoraReactiveApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public QuestionResponseDTO(String id, @NotBlank(message = "Title is required") @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters") String title, @NotBlank(message = "Content is required") @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters") String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
    }
}
