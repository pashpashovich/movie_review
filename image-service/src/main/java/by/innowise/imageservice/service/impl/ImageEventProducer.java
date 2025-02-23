package by.innowise.imageservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendImageSavedEvent(String imageName) {
        kafkaTemplate.send("image.saved", "Image saved: " + imageName);
    }
}
