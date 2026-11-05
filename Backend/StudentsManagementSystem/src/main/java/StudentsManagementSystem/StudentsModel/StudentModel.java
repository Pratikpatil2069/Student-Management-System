package StudentsManagementSystem.StudentsModel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@CompoundIndex(def = "{'name': 1, 'age': -1}")
@Document(collection = "students")
@Data
public class StudentModel {
    @Id
    private String id;
    
    @NotBlank(message="Please Enter Your Name")
    private String name;
    
    @Indexed(unique = true)
    @NotBlank(message="Please Enter Your Email")
    @Email(message="Invalid Email format")
    private String email;
    
    @Indexed
    @NotNull(message="Please Enter your Age")
    @Min(value=1, message="age must be greater than 0")
    @Max(value=100, message="age must be less or equal to 100")
    private Integer age;
    
	
}
