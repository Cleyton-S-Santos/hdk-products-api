package com.hidq.products.exception;

public class CustomException extends Exception{
    private final int status;

    public CustomException(String mensagem, int status) {
        super(mensagem);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
