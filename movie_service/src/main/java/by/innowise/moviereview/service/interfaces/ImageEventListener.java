package by.innowise.moviereview.service.interfaces;

import org.springframework.kafka.annotation.KafkaListener;

public interface ImageEventListener {
    @KafkaListener(topics = "image.saved", groupId = "movie-service-group")
    void listenImageSavedEvent(String message);
}
