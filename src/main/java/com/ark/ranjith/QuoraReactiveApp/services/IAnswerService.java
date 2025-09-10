package com.ark.ranjith.QuoraReactiveApp.services;

import com.ark.ranjith.QuoraReactiveApp.dto.AnswerRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.AnswerResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.models.Answer;
import reactor.core.publisher.Mono;

public interface IAnswerService {

    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);
    public Mono<AnswerResponseDTO> getAnswerById(String id);
}
