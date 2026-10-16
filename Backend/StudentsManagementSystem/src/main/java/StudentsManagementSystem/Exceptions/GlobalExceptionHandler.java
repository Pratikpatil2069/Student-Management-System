package StudentsManagementSystem.Exceptions;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import StudentsManagementSystem.Response.ResponseApi;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseApi<Void>> handleResourceNotFound(
            ResourceNotFoundException exception) {

        logger.warn("Resource not found: {}", exception.getMessage());

        ResponseApi<Void> response = new ResponseApi<>(
                false,
                exception.getMessage(),
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ResponseApi<Void>> handleDuplicateResource(
            DuplicateResourceException exception) {

        logger.warn("Duplicate resource: {}", exception.getMessage());

        ResponseApi<Void> response = new ResponseApi<>(
                false,
                exception.getMessage(),
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseApi<Void>> handleInvalidCredentials(InvalidCredentialsException exception) {

        logger.warn("Authentication failed: {}",exception.getMessage());

        ResponseApi<Void> response = new ResponseApi<>(false,exception.getMessage(),null,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi<Void>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        logger.warn("Validation failed: {}", message);

        ResponseApi<Void> response = new ResponseApi<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<Void>> handleGenericException(
            Exception exception) {

        logger.error("Unexpected error occurred",
                exception);

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