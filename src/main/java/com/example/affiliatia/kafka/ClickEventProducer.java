package com.example.affiliatia.kafka;

import com.example.affiliatia.dto.event.ClickEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickEventProducer {

    private static final String TOPIC = "click-events";

    private final KafkaTemplate<String, ClickEventMessage> kafkaTemplate;

    public void sendClickEvent(ClickEventMessage event) {

        kafkaTemplate.send(TOPIC, event);
    }
}