package dev.henriqueabreu.SchedulingSystem.infra;

import dev.henriqueabreu.SchedulingSystem.exceptions.IntervalInvalidException;
import dev.henriqueabreu.SchedulingSystem.exceptions.SchedulingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SchedulingNotFoundException.class)
    private ResponseEntity<RestErrorMessage> schedulingNotFoundHandler(SchedulingNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(IntervalInvalidException.class)
    private ResponseEntity<RestErrorMessage> intervalInvalidException(IntervalInvalidException exception) {
        RestErrorMessage threatErrorMessage = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatErrorMessage);
    }

}
