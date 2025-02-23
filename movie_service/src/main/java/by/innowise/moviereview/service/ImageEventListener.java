package by.innowise.moviereview.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageEventListener {

    @KafkaListener(topics = "image.saved", groupId = "movie-service-group")
    public void listenImageSavedEvent(String message) {
        log.info("Received Kafka message: {}", message);
    }
}
