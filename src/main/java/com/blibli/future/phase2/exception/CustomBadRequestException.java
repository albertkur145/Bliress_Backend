package com.blibli.future.phase2.exception;

public class CustomBadRequestException extends Exception {
    private String messageOnly;

    public CustomBadRequestException(String message) {
        super(message);
        this.messageOnly = message;
    }

    @Override
    public String getMessage() {
        return messageOnly;
    }
}
