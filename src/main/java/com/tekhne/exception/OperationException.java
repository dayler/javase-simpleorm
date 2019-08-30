package com.tekhne.exception;

public class OperationException extends RuntimeException {

    private static final long serialVersionUID = 20190829L;
    
    public OperationException(String msg, Throwable thwbl) {
        super(msg, thwbl);
    }
    
    public OperationException(String msg) {
        super(msg);
    } 

}
