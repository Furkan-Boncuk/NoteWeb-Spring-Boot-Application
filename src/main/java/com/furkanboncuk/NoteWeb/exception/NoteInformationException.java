package com.furkanboncuk.NoteWeb.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
public class NoteInformationException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,Object> errorBody = new LinkedHashMap<>();
        errorBody.put("Timestamp",System.currentTimeMillis());
        errorBody.put("HTTP Status",status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());

        errorBody.put("Errors",errors);

        return new ResponseEntity<Object>(errorBody,status);
    }

    @ExceptionHandler({NoteTitleException.class})
    public ResponseEntity<Object> noteException() {
        return NoteTitleException.generateErrorResponse("Note title must not be the same as the title of another note");
    }
}
