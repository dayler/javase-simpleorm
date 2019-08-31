package com.tekhne.exception;

public class UnsupportedDriverException extends OperationException {

    private static final long serialVersionUID = 20190830L;

    public UnsupportedDriverException(String msg) {
        super(msg);
    }
}
