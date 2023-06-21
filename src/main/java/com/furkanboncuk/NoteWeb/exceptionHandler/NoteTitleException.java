package com.furkanboncuk.NoteWeb.exceptionHandler;

import com.furkanboncuk.NoteWeb.entity.Note;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

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