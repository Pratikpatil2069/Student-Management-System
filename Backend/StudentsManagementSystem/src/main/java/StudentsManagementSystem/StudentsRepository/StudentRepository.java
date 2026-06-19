package StudentsManagementSystem.StudentsRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import StudentsManagementSystem.StudentsModel.StudentModel;

@Repository
public interface StudentRepository extends MongoRepository<StudentModel,String> {
}
