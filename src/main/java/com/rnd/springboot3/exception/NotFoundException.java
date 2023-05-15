package com.rnd.springboot3.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NotFoundException extends RuntimeException {

    private int statusCode;
    private String statusMessage;

    public NotFoundException(int statusCode , String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public NotFoundException(String message) {
        super(message);
    }
}
