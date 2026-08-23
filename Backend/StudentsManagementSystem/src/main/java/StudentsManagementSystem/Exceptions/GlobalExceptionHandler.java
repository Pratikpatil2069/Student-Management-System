package StudentsManagementSystem.Exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import StudentsManagementSystem.Response.ResponseApi;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseApi<Void>> handleResourceNotFound(
            ResourceNotFoundException ex) {

    	ResponseApi<Void> response = new ResponseApi<>(
                false,
                ex.getMessage(),
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // 409 - Duplicate Resource
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ResponseApi<Void>> handleDuplicateResource(
            DuplicateResourceException ex) {

    	ResponseApi<Void> response = new ResponseApi<>(
                false,
                ex.getMessage(),
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // 400 - Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {

        ResponseApi<Void> response = new ResponseApi<>(
                false,
                "Validation failed",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 500 - Unexpected Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<Void>> handleGlobalException(
            Exception ex) {

    	ResponseApi<Void> response = new ResponseApi<>(
                false,
                "Something went wrong",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}