package com.feedback.exception;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)  
	public Map<String,String>handleValidation(MethodArgumentNotValidException ex) {
    	HashMap<String,String> errors = new HashMap<>();
    	ex.getBindingResult().getFieldErrors().forEach(error->
    	      errors.put(error.getField(),ex.getMessage())
    	);
    	return errors;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String,String>handleNotFound(ResourceNotFoundException ex){
    	HashMap<String,String>error=new HashMap<>();
    	error.put("error",ex.getMessage());
		return error;  	
    }
}
