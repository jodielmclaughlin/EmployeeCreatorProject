package io.nology.project.common.exception;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message) {
        super(message);
    }
}
