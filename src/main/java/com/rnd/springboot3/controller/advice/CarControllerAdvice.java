package com.rnd.springboot3.controller.advice;

import com.google.gson.Gson;
import com.rnd.springboot3.exception.BadRequestException;
import com.rnd.springboot3.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CarControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(CarControllerAdvice.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> reponseNotFoundException(NotFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", e.getStatusCode());
        result.put("statusMessage", e.getStatusMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> responseBadRequestException(BadRequestException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", e.getStatucCode());
        result.put("statusMessage", e.getStatusMessage());
        result.put("errorList", e.getErrorList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
