package com.ark.ranjith.QuoraReactiveApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 10, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;

}
