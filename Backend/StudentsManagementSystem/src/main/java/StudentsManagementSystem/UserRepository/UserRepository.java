package StudentsManagementSystem.UserRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import StudentsManagementSystem.UserModel.UserModel;

@Repository
public interface UserRepository
        extends MongoRepository<UserModel, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel findByUsername(String username);
}