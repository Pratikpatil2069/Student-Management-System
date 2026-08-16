package StudentsManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentsManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudentsManagementSystemApplication.class, args);
	}

}
/*
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Day1:
- Created project structure from Spring Initializer.
-- Dependencies:-
                 1. Spring Web:- used for REST APIs, Controllers and HTTP requests and uses deafult apache tomcat server.
                 2. Spring Data MongoDB:- used for connecting MongoDB, database operations and Repository layer.
				 3. Lombok:- used for automatically generating getters, setters and constructors.
				 4. Spring Boot DevTools:- used for auto restart and faster development.

- Connected MongoDB:-
		-- application.properties:-
			  spring.mongodb.uri=mongodb://localhost:27017/studentdb

		-- This connects the application to MongoDB.
		-- If studentdb is not present, MongoDB creates it automatically when the first document is inserted.

---------------------------------------------------------------------------------------------------------------------------------------------------

Day2:
- Created Model and Repository.

		-- Model:- Model is a POJO class that represents a MongoDB document.

			Annotations:-
						 1. @Document(collection = "students"):- used to map the class to a MongoDB collection.
						 2. @Data:- provided by Lombok, automatically generates getters, setters, toString(), equals() and hashCode() methods.
						 3. @Id:- used to identify the primary key of the document.

		-- Repository:- Repository provides database operations through Spring Data MongoDB.

			1. MongoRepository<StudentModel, String>:-
			   - StudentModel → Entity/Document class.
			   - String → Data type of the primary key.
			   - Provides built-in CRUD operations such as save(), findAll(), findById(), deleteById() etc.

----------------------------------------------------------------------------------------------------------------------------------------------------

Day3:
- Created Controllers and Services for business logic.

		-- Controller:- Controller handles HTTP requests from the client and returns responses.

			Annotations:-
						 1. @RestController:- used to create REST APIs and return JSON responses.
						 2. @RequestMapping:- used to define the base URL for APIs.
						 3. @PostMapping:- used to handle POST requests (Add Student).
						 4. @GetMapping:- used to handle GET requests (Get Students).
						 5. @PutMapping:- used to handle PUT requests (Update Student).
						 6. @DeleteMapping:- used to handle DELETE requests (Delete Student).
						 7. @PathVariable:- used to read values from the URL.
						 8. @RequestBody:- used to convert JSON request data into a Java object.

		-- Service:- Service layer contains the business logic of the application.

			Annotations:-
						 1. @Service:- used to create a service bean managed by Spring Boot.

			Responsibilities:-
						 1. Receives requests from the Controller.
						 2. Performs business logic.
						 3. Communicates with the Repository layer.
						 4. Returns the result back to the Controller.

		-- Request Flow:-

			Client/Postman
				 ↓
			Controller
				 ↓
			Service
				 ↓
			Repository
				 ↓
			MongoDB
--------------------------------------------------------------------------------------------------------------------------------------
Day 4:
      Implemented Bean Validation for Student API.

Validation:-
      Validation ensures only valid data is accepted before storing it in MongoDB.

Added Dependency:
      spring-boot-starter-validation → Provides validation annotations.

Annotations Used:-

1. @NotBlank
      Field cannot be null, empty, or contain only spaces.

2. @Email
      Checks whether the email follows a valid email format.
      Does not verify if the email actually exists.

3. @Min and @Max
      Restrict numeric values within a specified range.
      Used to validate the student's age.

4. @Valid
      Applied in the Controller before @RequestBody.
      Automatically validates the incoming request.
      If validation fails, the request never reaches the Service layer.
      
-----------------------------------------------------------------------------------------------------------------------------------------

*/
