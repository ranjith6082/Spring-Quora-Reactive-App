package com.ark.ranjith.QuoraReactiveApp.services;

import com.ark.ranjith.QuoraReactiveApp.adapter.QuestionAdapter;
import com.ark.ranjith.QuoraReactiveApp.dto.PaginatedResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.PaginationDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.QuestionResponseDTO;
import com.ark.ranjith.QuoraReactiveApp.enums.TargetType;
import com.ark.ranjith.QuoraReactiveApp.events.ViewCountEvent;
import com.ark.ranjith.QuoraReactiveApp.models.Question;
import com.ark.ranjith.QuoraReactiveApp.producers.KafkaEventProducer;
import com.ark.ranjith.QuoraReactiveApp.repositories.QuestionRepository;
import com.ark.ranjith.QuoraReactiveApp.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;

    private final KafkaEventProducer kafkaEventProducer;

//    public QuestionService(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }


    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = Question.builder()
                .title(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .tags(questionRequestDTO.getTags()) // ✅ Save tags
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return questionRepository.save(question)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.err.println("Error creating question: " + error.getMessage()));

    }



    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id) {
        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .flatMap(response -> {
                    // Only execute when record exists
                    ViewCountEvent viewCountEvent = new ViewCountEvent(
                            id, TargetType.QUESTION, LocalDateTime.now()
                    );
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                    System.out.println("✅ Successfully retrieved question with id " + id + ": " + response);
                    return Mono.just(response);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Question not found with id: " + id)))
                .doOnError(error -> System.err.println("❌ Error retrieving id " + id + ": " + error.getMessage()));
    }





    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);


        if(!CursorUtils.isValidCursor(cursor)){
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.err.println("Error fetching questions: " + error.getMessage()))
                    .doOnComplete(() -> System.out.println("Fetched top 10 questions successfully."));
        }else{
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.err.println("Error fetching questions: " + error.getMessage()))
                    .doOnComplete(() -> System.out.println("Fetched questions successfully."));
        }
    }


    @Override
    public Mono<Void> deleteQuestionById(String id) {
        return questionRepository.findById(id)
                .flatMap(question -> questionRepository.deleteById(id))
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "❌ Question not found with id: " + id)))
                .doOnSuccess(unused -> System.out.println("Question deleted successfully with id: " + id))
                .doOnError(error -> System.out.println("Error deleting question with id " + id + ": " + error.getMessage()));
    }


    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page) {
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset,page))
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.err.println("Error searching questions: " + error.getMessage()))
                .doOnComplete(() -> System.out.println("Question search completed successfully."));
    }


    @Override
    public Mono<PaginatedResponseDTO<QuestionResponseDTO>> getAllQuestionsWithMetadata(int page, int size) {
        int skip = page * size;

        Mono<Long> totalRecordsMono = questionRepository.count();
        Flux<QuestionResponseDTO> questionsFlux = questionRepository.findAll()
                .skip(skip)
                .take(size)
                .map(QuestionAdapter::toQuestionResponseDTO);

        return totalRecordsMono.flatMap(totalRecords ->
                questionsFlux.collectList().map(questions -> {
                    int totalPages = (int) Math.ceil((double) totalRecords / size);
                    Integer nextPage = (page + 1 < totalPages) ? page + 1 : null;
                    Integer prevPage = (page > 0) ? page - 1 : null;

                    PaginationDTO pagination = new PaginationDTO(totalRecords, page, totalPages, nextPage, prevPage);

                    return new PaginatedResponseDTO<>(questions, pagination);
                })
        );
    }


    @Override
    public Flux<QuestionResponseDTO> getQuestionsByTag(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return questionRepository.findByTagsContainingIgnoreCase(tag, pageable)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "❌ No questions found with tag: " + tag)))
                .doOnNext(q -> System.out.println("✅ Found question with tag [" + tag + "]: " + q))
                .doOnError(error -> System.err.println("❌ Error fetching questions by tag [" + tag + "]: " + error.getMessage()))
                .doOnComplete(() -> System.out.println("✅ Completed fetching questions by tag [" + tag + "]"));
    }
}
