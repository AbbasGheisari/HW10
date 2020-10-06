package com.test.base.controller.utilities;

public final class NoValidDataException extends Exception {

    public NoValidDataException(String message) {
        super(message);
    }

    public NoValidDataException(String message, Exception e) {
        super(message, e);
    }
}
