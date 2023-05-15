package com.rnd.springboot3.exception;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BadRequestException extends RuntimeException {

    private int statucCode;
    private String statusMessage;
    private List<Map<String, Object>> errorList;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(int statusCode, String statusMessage, List<Map<String, Object>> errorList) {
        this.statucCode = statusCode;
        this.statusMessage = statusMessage;
        this.errorList = errorList;
    }
}
