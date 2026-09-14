package StudentsManagementSystem.Exceptions;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import StudentsManagementSystem.Response.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
    // 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseApi<Void>> handleResourceNotFound(
            ResourceNotFoundException ex) {
    	logger.warn(ex.getMessage());
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
    	logger.warn(ex.getMessage());
    	
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
    public ResponseEntity<ResponseApi<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {
    	
    	logger.error("Validation Failed");
    	
    	 Map<String, String> errors = new HashMap<>();

    	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
    	        errors.put(error.getField(), error.getDefaultMessage());
    	    }

        ResponseApi<Map<String, String>> response = new ResponseApi<>(
                false,
                "Validation failed",
                errors,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 500 - Unexpected Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<Void>> handleGlobalException( Exception ex) {
    		
    	logger.error("Unexpected error occurred", ex);
    	
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