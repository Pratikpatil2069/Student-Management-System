package StudentsManagementSystem.StudentsController;

import org.springframework.web.bind.annotation.CrossOrigin;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import StudentsManagementSystem.StudentsServices.StudentServices;
import jakarta.validation.Valid;
import StudentsManagementSystem.Response.ResponseApi;
import StudentsManagementSystem.StudentsModel.StudentModel;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/Student")
public class StudentController {
	@Autowired
	private StudentServices studentservices;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseApi<StudentModel>> addStudent(@Valid @RequestBody StudentModel studentmodel) {
		
		StudentModel student= studentservices.addStudent(studentmodel);
		
		ResponseApi<StudentModel> response = new ResponseApi<>(true,"Student created successfully",student,LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseApi<Void>> deleteStudent(@PathVariable String id) {
		
		 studentservices.deleteStudent(id);
		 
		 ResponseApi<Void>response=new ResponseApi<>(true,"Student deleted successfully",null,LocalDateTime.now());
		 
		 return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseApi<StudentModel>> updateStudent(@PathVariable String id,@Valid @RequestBody StudentModel studentmodel) {
		
		StudentModel UpdatedStudent= studentservices.updateStudent(id,studentmodel);
		
		ResponseApi<StudentModel>response =new ResponseApi<>(true, "Student updated successfully",UpdatedStudent,LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseApi<List<StudentModel>>> getAllStudent() {
		
		List<StudentModel> list= studentservices.getAllStudent();
		
		ResponseApi<List<StudentModel>>response=new ResponseApi<>(true,"Students Fetched successfully", list,LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<ResponseApi<StudentModel>> getStudentById(@PathVariable String id) {
		
	    StudentModel student= studentservices.getStudentById(id);
	    
	    ResponseApi<StudentModel>response=new ResponseApi<>(true, "Student Fetched successfully", student, LocalDateTime.now());
	    
	    return ResponseEntity.ok(response);
	    
	}

}
