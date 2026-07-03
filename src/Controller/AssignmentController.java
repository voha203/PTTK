package Controller;

import java.util.List;
import Model.Assignment;
import Service.AssignmentService;

public class AssignmentController {
	private AssignmentService assignmentService = new AssignmentService();

	public String addAssignment(Assignment assignment) {
		return assignmentService.addAssignment(assignment);
	}

	public boolean updateAssignment(Assignment assignment) {
		return assignmentService.updateAssignment(assignment);
	}

	public boolean deleteAssignment(int assignmentId) {
		return assignmentService.deleteAssignment(assignmentId);
	}

	public Assignment getAssignmentById(int assignmentId) {
		return assignmentService.getAssignmentById(assignmentId);
	}

	public List<Assignment> getAllAssignment() {
		return assignmentService.getAllAssignment();
	}
}