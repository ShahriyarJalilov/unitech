package az.unibank.cn.unitech.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String fieldName = bindingResult.getFieldErrors().stream()
                .map(data -> data.getDefaultMessage() + " : " + data.getField())
                .collect(Collectors.joining(", "));
        return ErrorResponse.of(BAD_REQUEST.value(), fieldName);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handle(JwtException ex) {
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ErrorResponse.of(UNAUTHORIZED.value(),ex.getMessage()));
    }

    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handle(CustomException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorResponse.of(ex.getErrorMessage()));
    }
}
