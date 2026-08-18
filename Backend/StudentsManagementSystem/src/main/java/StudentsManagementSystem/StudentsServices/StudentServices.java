package StudentsManagementSystem.StudentsServices;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import StudentsManagementSystem.Exceptions.ResourceException;
import StudentsManagementSystem.StudentsModel.StudentModel;
import StudentsManagementSystem.StudentsRepository.StudentRepository;

@Service
public class StudentServices {
	
	@Autowired
	private StudentRepository studentrepository;
	
	public StudentModel addStudent(StudentModel studentmodel) {
		  if (studentrepository.existsByEmail(studentmodel.getEmail())) {
			  throw new ResourceException(
		                "Student already exists with email: " + studentmodel.getEmail()
		        );		    
			  }
		return studentrepository.save(studentmodel);
	}
	
	public StudentModel updateStudent(String id, StudentModel studentmodel) {
		StudentModel student=studentrepository.findById(id).orElseThrow(()->new ResourceException("Student Not Found with id: "+id));
		
		if(student!=null) {
			if (studentrepository.existsByEmail(studentmodel.getEmail())) {
				  throw new ResourceException(
			                "Student already exists with email: " + studentmodel.getEmail()
			        );
			}
			student.setName(studentmodel.getName());
            student.setEmail(studentmodel.getEmail());
            student.setAge(studentmodel.getAge());
            return studentrepository.save(student);
		}
		return null ;
	}

	
	public StudentModel deleteStudent(String id) {
		StudentModel student=studentrepository.findById(id).orElseThrow(()->new ResourceException("Student Not Found with id: "+id));
		 studentrepository.deleteById(id);
		 return student;
	}

	public List<StudentModel> getAllStudent() {
		return studentrepository.findAll();
	}
	
	
	public StudentModel getStudentById(String id) {
	    return studentrepository.findById(id).orElseThrow(()->new ResourceException("Student Not Found with id: "+id));
	}


}
