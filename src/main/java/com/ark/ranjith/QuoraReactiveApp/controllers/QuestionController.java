package com.ark.ranjith.QuoraReactiveApp.controllers;

import com.ark.ranjith.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.services.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;

//    public QuestionController(IQuestionService questionService) {
//        this.questionService = questionService;
//    }

    @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {

        return questionService.createQuestion(questionRequestDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id)
                .doOnSuccess(response -> System.out.println("Question retrieved successfully: " + response))
                .doOnError(error -> System.out.println("Error retrieving question: " + error.getMessage()));
    }

    @GetMapping
    public Flux<QuestionResponseDTO> getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return questionService.getAllQuestions(cursor,size)
                .doOnComplete(() -> System.out.println("All questions retrieved successfully"))
                .doOnError(error -> System.out.println("Error retrieving questions: " + error.getMessage()));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id) {
        return questionService.deleteQuestionById(id)
                .doOnSuccess(aVoid -> System.out.println("Question deleted successfully"))
                .doOnError(error -> System.out.println("Error deleting question: " + error.getMessage()));
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return questionService.searchQuestions(query,page,size);
    }

    @GetMapping("/tag/{tag}")
    public Flux<QuestionResponseDTO> getQuestionsByTag(
            @PathVariable String tag,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        throw new UnsupportedOperationException("Tag-based question retrieval is not implemented yet.");
//        return questionService.getQuestionsByTag(tag, page, size)
//                .doOnComplete(() -> System.out.println("Questions by tag retrieved successfully"))
//                .doOnError(error -> System.out.println("Error retrieving questions by tag: " + error.getMessage()));
    }



}
