package StudentsManagementSystem.StudentsServices;



import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;



import StudentsManagementSystem.Exceptions.ResourceNotFoundException;
import StudentsManagementSystem.StudentsDTO.StudentRequest;
import StudentsManagementSystem.StudentsDTO.StudentResponse;
import StudentsManagementSystem.Exceptions.DuplicateResourceException;
import StudentsManagementSystem.StudentsModel.StudentModel;
import StudentsManagementSystem.StudentsRepository.StudentRepository;

@Service
public class StudentServices {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public StudentResponse addStudent(StudentRequest studentRequest) {
		  if (studentRepository.existsByEmail(studentRequest.getEmail())) {
			  throw new DuplicateResourceException(
		                "Student already exists with email: " + studentRequest.getEmail()
		        );		    
			  }
		    StudentModel studentModel = new StudentModel();

		    studentModel.setName(studentRequest.getName());
		    studentModel.setEmail(studentRequest.getEmail());
		    studentModel.setAge(studentRequest.getAge());
		    
		    StudentModel response=studentRepository.save(studentModel);
		    
		    StudentResponse studentResponse = new StudentResponse();

		    studentResponse.setId(response.getId());
		    studentResponse.setName(response.getName());
		    studentResponse.setEmail(response.getEmail());
		    studentResponse.setAge(response.getAge());
		return studentResponse;
	}
	
	public StudentResponse updateStudent(String id, StudentRequest studentRequest) {
		StudentModel studentModel=studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student Not Found with id: "+id));
		
			if (studentRepository.existsByEmail(studentRequest.getEmail())) {
				  throw new DuplicateResourceException(
			                "Student already exists with email: " + studentRequest.getEmail()
			        );
			}

			 studentModel.setName(studentRequest.getName());
			 studentModel.setEmail(studentRequest.getEmail());
			 studentModel.setAge(studentRequest.getAge());
			 
			StudentModel response=studentRepository.save(studentModel);
			
			StudentResponse studentResponse=new StudentResponse();
			
			studentResponse.setId(response.getId());
			studentResponse.setName(response.getName());
			studentResponse.setEmail(response.getEmail());
			studentResponse.setAge(response.getAge());
			
	        return studentResponse;
		
	}

	
	public void deleteStudent(String id) {
		if(studentRepository.existsById(id)) {
			
			studentRepository.deleteById(id);
			
		}else {
			
			 throw new ResourceNotFoundException("Student Not Found with id: "+id);
			 
		}
		
		 
	}

	public List<StudentResponse> getAllStudent(String name , Pageable pageable) {
		
			List<StudentModel>list;
			if (name == null || name.isBlank()) {
				
					list = studentRepository.findAll(pageable).getContent();
		    } else {
		    	
		        	list = studentRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		    }

	    

	    List<StudentResponse> response = new ArrayList<>();

	    for (StudentModel student : list) {

	        StudentResponse studentResponse = new StudentResponse();

	        studentResponse.setId(student.getId());
	        studentResponse.setName(student.getName());
	        studentResponse.setEmail(student.getEmail());
	        studentResponse.setAge(student.getAge());

	        response.add(studentResponse);
	    }

	    return response;
	}
	
	
	public StudentResponse getStudentById(String id) {
		
	    StudentModel response= studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student Not Found with id: "+id));
	    
	    StudentResponse studentResponse=new StudentResponse();
		
		studentResponse.setId(response.getId());
		studentResponse.setName(response.getName());
		studentResponse.setEmail(response.getEmail());
		studentResponse.setAge(response.getAge());
	    
	    return studentResponse;
	}


}
