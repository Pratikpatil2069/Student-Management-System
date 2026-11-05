package StudentsManagementSystem.StudentsServices;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import StudentsManagementSystem.Exceptions.DuplicateResourceException;
import StudentsManagementSystem.Exceptions.ResourceNotFoundException;
import StudentsManagementSystem.StudentsDTO.StudentCursorResponse;
import StudentsManagementSystem.StudentsDTO.StudentRequest;
import StudentsManagementSystem.StudentsDTO.StudentResponse;
import StudentsManagementSystem.StudentsModel.StudentModel;
import StudentsManagementSystem.StudentsProjection.StudentNameAgeProjection;
import StudentsManagementSystem.StudentsRepository.StudentRepository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentServices {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentServices.class);

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponse addStudent(StudentRequest studentRequest) {

        logger.info("Adding new student with email: {}",
                studentRequest.getEmail());

        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            logger.warn("Student already exists with email: {}",
                    studentRequest.getEmail());

            throw new DuplicateResourceException(
                    "Student already exists with email: "
                    + studentRequest.getEmail());
        }

        StudentModel studentModel = new StudentModel();

        studentModel.setName(studentRequest.getName());
        studentModel.setEmail(studentRequest.getEmail());
        studentModel.setAge(studentRequest.getAge());

        StudentModel response = studentRepository.save(studentModel);

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setId(response.getId());
        studentResponse.setName(response.getName());
        studentResponse.setEmail(response.getEmail());
        studentResponse.setAge(response.getAge());

        logger.info("Student created successfully with id: {}",
                studentResponse.getId());

        return studentResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponse updateStudent(
            String id,
            StudentRequest studentRequest) {

        logger.info("Student updating of id: {}", id);

        StudentModel studentModel = studentRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Student Not Found with id: " + id));

        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            logger.warn("Student Already exists with this email: {}",
                    studentRequest.getEmail());

            throw new DuplicateResourceException(
                    "Student already exists with email: "
                    + studentRequest.getEmail());
        }

        studentModel.setName(studentRequest.getName());
        studentModel.setEmail(studentRequest.getEmail());
        studentModel.setAge(studentRequest.getAge());

        StudentModel response = studentRepository.save(studentModel);

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setId(response.getId());
        studentResponse.setName(response.getName());
        studentResponse.setEmail(response.getEmail());
        studentResponse.setAge(response.getAge());

        logger.info("Student updated successfully with id: {}", id);

        return studentResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(String id) {

        if (studentRepository.existsById(id)) {

            studentRepository.deleteById(id);

            logger.info("Students deleted successfully of id: {}", id);

        } else {

            logger.warn("Student does not exists with id: {}", id);

            throw new ResourceNotFoundException(
                    "Student Not Found with id: " + id);
        }
    }

    public List<StudentResponse> getAllStudent(
            String name,
            Integer minAge,
            Integer maxAge,
            Pageable pageable) {

        logger.info(
            "Fetching All Students with name:{} minAge:{} maxAge:{} page:{} size:{}",
            name,
            minAge,
            maxAge,
            pageable.getPageNumber(),
            pageable.getPageSize()
        );

        List<StudentModel> list;

        if (name == null || name.isBlank()) {

            if (minAge != null && maxAge != null) {

                list = studentRepository
                        .findByAgeBetween(minAge, maxAge, pageable)
                        .getContent();

            } else if (minAge != null) {

                list = studentRepository
                        .findByAgeGreaterThanEqual(minAge, pageable)
                        .getContent();

            } else if (maxAge != null) {

                list = studentRepository
                        .findByAgeLessThanEqual(maxAge, pageable)
                        .getContent();

            } else {

                list = studentRepository
                        .findAll(pageable)
                        .getContent();
            }

        } else {

            list = studentRepository
                    .findByNameContainingIgnoreCase(name, pageable)
                    .getContent();
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

        logger.info(
            "Fetched All students successfully. the total Students are :{}",
            response.size()
        );

        return response;
    }

    public StudentResponse getStudentById(String id) {

        logger.info("Fetching student with id {}", id);

        StudentModel response = studentRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Student Not Found with id: " + id));

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setId(response.getId());
        studentResponse.setName(response.getName());
        studentResponse.setEmail(response.getEmail());
        studentResponse.setAge(response.getAge());

        logger.info("Fetched Student with id {}", id);

        return studentResponse;
    }
    
    public List<StudentNameAgeProjection> getStudentNamesAndAges() {
        return studentRepository.findAllProjectedBy();
    }
    
    public List<StudentModel> getStudentsByCursor(String cursor, int size) {

        Query query = new Query();

        // Apply cursor condition only when cursor is provided
        if (cursor != null && !cursor.isEmpty()) {

            if (!ObjectId.isValid(cursor)) {
                throw new IllegalArgumentException("Invalid cursor ID");
            }

            query.addCriteria(
                Criteria.where("_id").gt(new ObjectId(cursor))
            );
        }

        // Sort by ID in ascending order
        query.with(Sort.by(Sort.Direction.ASC, "_id"));

        // Fetch one extra record to check whether another page exists
        query.limit(size + 1);

        return mongoTemplate.find(query, StudentModel.class);
    }
    
    public StudentCursorResponse getStudentCursorPage(String cursor, int size) {

        List<StudentModel> students = getStudentsByCursor(cursor, size);

        boolean hasNext = students.size() > size;

        if (hasNext) {
            students.remove(size);
        }

        List<StudentResponse> responseList = new ArrayList<>();

        for (StudentModel student : students) {

            StudentResponse response = new StudentResponse();

            response.setId(student.getId());
            response.setName(student.getName());
            response.setEmail(student.getEmail());
            response.setAge(student.getAge());

            responseList.add(response);
        }

        String nextCursor = null;

        if (!students.isEmpty()) {
            nextCursor = students.get(students.size() - 1).getId();
        }

        StudentCursorResponse result = new StudentCursorResponse();

        result.setStudentsList(responseList);
        result.setNextId(nextCursor);
        result.setHasNext(hasNext);

        return result;
    }
}
