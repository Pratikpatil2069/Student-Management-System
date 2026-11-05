package StudentsManagementSystem.StudentsDTO;

import java.util.List;

import lombok.Data;

@Data
public class StudentCursorResponse {
	
	private List<StudentResponse> studentsList;
	private String nextId;
	private boolean hasNext;
}
