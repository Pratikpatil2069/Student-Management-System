package StudentsManagementSystem.UserServices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import StudentsManagementSystem.Exceptions.DuplicateResourceException;
import StudentsManagementSystem.Exceptions.InvalidCredentialsException;
import StudentsManagementSystem.UserDTO.LoginRequest;
import StudentsManagementSystem.UserDTO.LoginResponse;
import StudentsManagementSystem.UserDTO.UserRequest;
import StudentsManagementSystem.UserDTO.UserResponse;
import StudentsManagementSystem.UserModel.UserModel;
import StudentsManagementSystem.UserRepository.UserRepository;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserResponse registerUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {

            throw new DuplicateResourceException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        UserModel userModel = new UserModel();

        userModel.setUsername(userRequest.getUsername());
        userModel.setEmail(userRequest.getEmail());
        
        userModel.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userModel.setRole("USER");

        UserModel response = userRepository.save(userModel);

        return convertToUserResponse(response);
    }

    public UserResponse registerAdmin(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {

            throw new DuplicateResourceException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        UserModel userModel = new UserModel();

        userModel.setUsername(userRequest.getUsername());
        userModel.setEmail(userRequest.getEmail());
        userModel.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userModel.setRole("ADMIN");

        UserModel response = userRepository.save(userModel);

        return convertToUserResponse(response);
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {

        UserModel user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null) {

            throw new InvalidCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {

            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername(),user.getRole());

        LoginResponse response = new LoginResponse();

        response.setToken(token);
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
    }

    private UserResponse convertToUserResponse(UserModel user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }
}