package StudentsManagementSystem.StudentsModel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Data
public class StudentModel {
    @Id
    private String id;
    private String name;
    private String email;
    private int age;
	
}
