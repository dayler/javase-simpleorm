package com.tekhne.exception;

public class NullValueException extends OperationException {

    private static final long serialVersionUID = 1L;

    public NullValueException(String msg) {
        super(msg);
    }

}
