package StudentsManagementSystem.StudentsController;

import org.springframework.web.bind.annotation.CrossOrigin;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import StudentsManagementSystem.StudentsServices.StudentServices;
import jakarta.validation.Valid;
import StudentsManagementSystem.StudentsModel.StudentModel;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/Student")
public class StudentController {
	@Autowired
	private StudentServices studentservices;
	
	@PostMapping("/add")
	public ResponseEntity<StudentModel> addStudent(@Valid @RequestBody StudentModel studentmodel) {
		StudentModel student= studentservices.addStudent(studentmodel);
		return ResponseEntity.status(HttpStatus.CREATED).body(student);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
		 studentservices.deleteStudent(id);
		 return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<StudentModel> updateStudent(@PathVariable String id,@Valid @RequestBody StudentModel studentmodel) {
		StudentModel UpdatedStudent= studentservices.updateStudent(id,studentmodel);
		return ResponseEntity.ok(UpdatedStudent);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<StudentModel>> getAllStudent() {
		List<StudentModel> list= studentservices.getAllStudent();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<StudentModel> getStudentById(@PathVariable String id) {
	    StudentModel student= studentservices.getStudentById(id);
	    return ResponseEntity.ok(student);
	    
	}

}
