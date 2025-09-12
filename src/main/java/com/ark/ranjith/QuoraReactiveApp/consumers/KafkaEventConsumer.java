package com.ark.ranjith.QuoraReactiveApp.consumers;

import com.ark.ranjith.QuoraReactiveApp.config.KafkaConfig;
import com.ark.ranjith.QuoraReactiveApp.events.ViewCountEvent;
import com.ark.ranjith.QuoraReactiveApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    @KafkaListener(
            topics = KafkaConfig.TOPIC_NAME,
            groupId = "view-count-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent){
        questionRepository.findById(viewCountEvent.getTargetId())
                .flatMap(question -> {
                    Integer views = question.getViews();
                    int newViews = (views == null ? 0 : views) + 1;  // handle null
                    System.out.println("Incrementing view count for question ID " + question.getId()
                            + " from " + views + " to " + newViews);
                    question.setViews(newViews);
                    return questionRepository.save(question);
                })
                .subscribe(
                        updatedQuestion -> System.out.println("✅ Updated view count for question ID " + updatedQuestion.getId() + ": " + updatedQuestion.getViews()),
                        error -> System.err.println("❌ Error updating view count for question ID " + viewCountEvent.getTargetId() + ": " + error.getMessage())
                );
    }

}
