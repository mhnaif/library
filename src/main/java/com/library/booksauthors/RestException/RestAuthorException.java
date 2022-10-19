package com.library.booksauthors.RestException;


import com.library.booksauthors.Excepetion.AuthorNotFoundException;
import com.library.booksauthors.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestAuthorException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorMessage> AuthorNotFoundException(AuthorNotFoundException e){

        ErrorMessage message = new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}
