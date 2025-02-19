package by.innowise.imageservice.exception;
public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String id) {
        super("Image with ID " + id + " not found.");
    }
}

