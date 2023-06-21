package com.furkanboncuk.NoteWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class NoteTitleException extends RuntimeException{

    public static ResponseEntity<Object> generateErrorResponse(String message) {
        Map<String,Object> errorBody = new LinkedHashMap<>();
        errorBody.put("Timestamp",System.currentTimeMillis());
        errorBody.put("HTTP Status",HttpStatus.BAD_REQUEST.value());
        errorBody.put("Error",message);
        return new ResponseEntity<>(errorBody,HttpStatus.BAD_REQUEST);
    }

}