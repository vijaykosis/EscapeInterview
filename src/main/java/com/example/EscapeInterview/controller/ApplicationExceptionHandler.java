package com.example.EscapeInterview.controller;

import com.example.EscapeInterview.model.DistanceServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DistanceServiceException> handleGenericException(Exception e){
        DistanceServiceException studentException = new DistanceServiceException(100, "Records are not found");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(studentException);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DistanceServiceException> handleRunTimeException(RuntimeException e, HttpServletRequest request){
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        DistanceServiceException studentException = new DistanceServiceException(101, String.format("distance  with %s is not found", pathVariables.get("id")));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(studentException);
    }

}