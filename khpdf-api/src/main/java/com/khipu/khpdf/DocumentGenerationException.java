package com.khipu.khpdf;

public class DocumentGenerationException extends Exception {

    public DocumentGenerationException() {
        super();
    }

    public DocumentGenerationException(String message) {
        super(message);
    }

    public DocumentGenerationException(Throwable cause) {
        super(cause);
    }

    public DocumentGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
