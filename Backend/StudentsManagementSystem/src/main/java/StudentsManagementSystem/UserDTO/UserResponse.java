package StudentsManagementSystem.UserDTO;

import lombok.Data;

@Data
public class UserResponse {

    private String id;

    private String username;

    private String email;

    private String role;
}