package com.ark.ranjith.QuoraReactiveApp.adapter;

import com.ark.ranjith.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.models.Question;

import java.time.LocalDateTime;

public class QuestionAdapter {

    public static QuestionResponseDTO toQuestionResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .build();
    }
}
