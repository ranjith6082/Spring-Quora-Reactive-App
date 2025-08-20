package com.ark.ranjith.QuoraReactiveApp.services;

import com.ark.ranjith.QuoraReactiveApp.adapter.QuestionAdapter;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.models.Question;
import com.ark.ranjith.QuoraReactiveApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    public final QuestionRepository questionRepository;

//    public QuestionService(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }


    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = Question.builder()
                .title(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return questionRepository.save(question)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.err.println("Error creating question: " + error.getMessage()));

    }
}
