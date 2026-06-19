package StudentsManagementSystem.StudentsServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import StudentsManagementSystem.StudentsModel.StudentModel;
import StudentsManagementSystem.StudentsRepository.StudentRepository;

@Service
public class StudentServices {
	
	@Autowired
	private StudentRepository studentrepository;
	
	public StudentModel addStudent(StudentModel studentmodel) {
		return studentrepository.save(studentmodel);
	}
	
	public StudentModel updateStudent(String id, StudentModel studentmodel) {
		StudentModel student=studentrepository.findById(id).orElse(null);
		
		if(student!=null) {
			student.setName(studentmodel.getName());
            student.setEmail(studentmodel.getEmail());
            student.setAge(studentmodel.getAge());
            return studentrepository.save(student);
		}
		return null;
	}

	
	public Optional<StudentModel> deleteStudent(String id) {
		Optional<StudentModel> student=studentrepository.findById(id);
		 studentrepository.deleteById(id);
		 return student;
	}

	public List<StudentModel> getAllStudent() {
		return studentrepository.findAll();
	}
	
	
	public StudentModel getStudentById(String id) {
	    return studentrepository.findById(id).get();
	}


}
