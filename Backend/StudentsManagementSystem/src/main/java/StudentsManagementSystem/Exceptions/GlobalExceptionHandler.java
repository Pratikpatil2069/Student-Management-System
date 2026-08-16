package StudentsManagementSystem.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> notValidExceptionHandler(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    List<FieldError> list = ex.getBindingResult().getFieldErrors();

	    for (FieldError error : list) {
	        errors.put(error.getField(), error.getDefaultMessage());
	    }

	    ErrorResponse errorResponse = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            "Validation Failed",
	            java.time.LocalDateTime.now().toString(),
	            errors
	    );

	    return new ResponseEntity<>(
	            errorResponse,
	            HttpStatus.BAD_REQUEST
	    );
	}
	
	@ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex) {
		ErrorResponse error=new ErrorResponse(HttpStatus.NOT_FOUND.value(),
	            ex.getMessage(),
	            java.time.LocalDateTime.now().toString());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {

	    ErrorResponse error = new ErrorResponse(
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            "Something went wrong",
	            java.time.LocalDateTime.now().toString()
	    );

	    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEmailException(
	        DuplicateEmailException ex) {

	    ErrorResponse error = new ErrorResponse(
	            HttpStatus.CONFLICT.value(),
	            ex.getMessage(),
	            java.time.LocalDateTime.now().toString()
	    );

	    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
}
