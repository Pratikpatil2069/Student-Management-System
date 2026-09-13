package StudentsManagementSystem.StudentsController;

import org.springframework.web.bind.annotation.CrossOrigin;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import StudentsManagementSystem.StudentsServices.StudentServices;
import jakarta.validation.Valid;
import StudentsManagementSystem.Response.ResponseApi;
import StudentsManagementSystem.StudentsDTO.StudentRequest;
import StudentsManagementSystem.StudentsDTO.StudentResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/Students")
public class StudentController {
	@Autowired
	private StudentServices studentservices;
	
	@PostMapping
	public ResponseEntity<ResponseApi<StudentResponse>> addStudent(@Valid @RequestBody StudentRequest studentRequest) {
		
		StudentResponse studentResponse= studentservices.addStudent(studentRequest);
		
		ResponseApi<StudentResponse> response = new ResponseApi<>(true,"Student created successfully",studentResponse,LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseApi<Void>> deleteStudent(@PathVariable String id) {
		
		 studentservices.deleteStudent(id);
		 
		 ResponseApi<Void>response=new ResponseApi<>(true,"Student deleted successfully",null,LocalDateTime.now());
		 
		 return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseApi<StudentResponse>> updateStudent(@PathVariable String id,@Valid @RequestBody StudentRequest studentRequest) {
		
		StudentResponse studentResponse= studentservices.updateStudent(id,studentRequest);
		
		ResponseApi<StudentResponse>response =new ResponseApi<>(true, "Student updated successfully",studentResponse,LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<ResponseApi<List<StudentResponse>>> getAllStudent(@RequestParam(required= false) String name,@RequestParam(required=false) Integer minAge, @RequestParam(required=false) Integer maxAge, Pageable pageable) {
		
		List<StudentResponse> list= studentservices.getAllStudent(name,minAge,maxAge, pageable);
		
		ResponseApi<List<StudentResponse>>response=new ResponseApi<>(true,"Students Fetched successfully", list,LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseApi<StudentResponse>> getStudentById(@PathVariable String id) {
		
		StudentResponse studentResponse= studentservices.getStudentById(id);
	    
	    ResponseApi<StudentResponse>response=new ResponseApi<>(true, "Student Fetched successfully", studentResponse, LocalDateTime.now());
	    
	    return ResponseEntity.ok(response);
	    
	}

}
