package StudentsManagementSystem.StudentsRepository;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import StudentsManagementSystem.StudentsModel.StudentModel;

@Repository
public interface StudentRepository extends MongoRepository<StudentModel,String> {
	
	  boolean existsByEmail(String email);
	  
	  Page<StudentModel> findByNameContainingIgnoreCase(String name, Pageable pageable);
	  
	  Page<StudentModel> findByAgeGreaterThanEqual(Integer minAge, Pageable pageable);
	  
	  Page<StudentModel> findByAgeLessThanEqual(Integer maxAge, Pageable pageable);
	  
	  Page<StudentModel> findByAgeBetween(Integer minAge, Integer maxAge, Pageable pageable);
}
