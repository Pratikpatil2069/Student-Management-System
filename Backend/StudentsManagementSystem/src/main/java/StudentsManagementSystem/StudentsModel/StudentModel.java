package StudentsManagementSystem.StudentsModel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "students")
@Data
public class StudentModel {
    @Id
    private String id;
    
    @NotBlank(message="Name can not be empty")
    private String name;
    
    @NotBlank(message="Email can not be empty")
    @Email(message="Invalid Email format")
    private String email;
    
    @Min(value=1, message="age must be greater than 0")
    @Max(value=100, message="age must be less or equal to 100")
    private int age;
	
}
