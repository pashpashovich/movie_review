package by.innowise.imageservice.exception;

public class InvalidImageFormatException extends RuntimeException {
    public InvalidImageFormatException(String contentType) {
        super("Invalid file type: " + (contentType != null ? contentType : "unknown") + ". Allowed types: JPEG, PNG, GIF, WEBP.");
    }
}
