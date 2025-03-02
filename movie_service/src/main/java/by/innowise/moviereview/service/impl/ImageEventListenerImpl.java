package by.innowise.moviereview.service.impl;

import by.innowise.moviereview.service.interfaces.ImageEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageEventListenerImpl implements ImageEventListener {
    @Override
    public void listenImageSavedEvent(String message) {
        log.info("Received Kafka message: {}", message);
    }
}
