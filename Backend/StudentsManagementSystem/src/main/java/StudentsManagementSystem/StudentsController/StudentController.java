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

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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
	public StudentModel addStudent(@Valid @RequestBody StudentModel studentmodel) {
		return studentservices.addStudent(studentmodel);
	}
	
	@DeleteMapping("/delete/{id}")
	public Optional<StudentModel> deleteStudent(@PathVariable String id) {
		return studentservices.deleteStudent(id);
	}
	
	@PutMapping("/update/{id}")
	public StudentModel updateStudent(@PathVariable String id,@Valid @RequestBody StudentModel studentmodel) {
		return studentservices.updateStudent(id,studentmodel);
	}
	
	@GetMapping("/getAll")
	public List<StudentModel> getAllStudent() {
		return studentservices.getAllStudent();
	}
	
	@GetMapping("/getById/{id}")
	public StudentModel getStudentById(@PathVariable String id) {
	    return studentservices.getStudentById(id);
	}

}
