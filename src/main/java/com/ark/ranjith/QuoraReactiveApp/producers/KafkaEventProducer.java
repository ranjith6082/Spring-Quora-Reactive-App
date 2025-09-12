package com.ark.ranjith.QuoraReactiveApp.producers;

import com.ark.ranjith.QuoraReactiveApp.config.KafkaConfig;
import com.ark.ranjith.QuoraReactiveApp.events.ViewCountEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publishViewCountEvent(ViewCountEvent viewCountEvent){
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME,viewCountEvent.getTargetId(),viewCountEvent)
                .whenComplete((result,err)->{
                    if(err!=null){
                        System.err.println("Error publishing view count event: "+err.getMessage());
                    }
                });
    }
}
