package com.ark.ranjith.QuoraReactiveApp.services;

import com.ark.ranjith.QuoraReactiveApp.dto.PaginatedResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.models.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);

    public Mono<QuestionResponseDTO> getQuestionById(String id);

    public Flux<QuestionResponseDTO> getAllQuestions(String cursor,int size);

    public Mono<Void> deleteQuestionById(String id);

    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page);

    // ✅ new paginated method
    Mono<PaginatedResponseDTO<QuestionResponseDTO>> getAllQuestionsWithMetadata(int page, int size);
}
