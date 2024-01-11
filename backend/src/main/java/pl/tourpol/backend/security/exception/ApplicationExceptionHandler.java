package pl.tourpol.backend.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<Object> handleRequestException(RequestException requestException) {
        return ResponseEntity.badRequest().body(requestException.getRequestErrorMessage());
    }

}
