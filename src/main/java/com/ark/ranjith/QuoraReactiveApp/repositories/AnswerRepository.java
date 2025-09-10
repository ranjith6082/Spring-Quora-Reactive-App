package com.ark.ranjith.QuoraReactiveApp.repositories;

import com.ark.ranjith.QuoraReactiveApp.models.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {
}
