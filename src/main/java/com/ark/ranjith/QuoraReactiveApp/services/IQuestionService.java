package com.ark.ranjith.QuoraReactiveApp.services;

import com.ark.ranjith.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.models.Question;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);
}
