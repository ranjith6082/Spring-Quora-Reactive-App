package com.ark.ranjith.QuoraReactiveApp.repositories;

import com.ark.ranjith.QuoraReactiveApp.models.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question,String> {

}
