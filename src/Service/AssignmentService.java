package Service;

import java.util.List;
import Dao.AssignmentDAO;
import Model.Assignment;

public class AssignmentService {
	private AssignmentDAO assignmentDAO = new AssignmentDAO();

	public String addAssignment(Assignment assignment) {
		if (!assignmentDAO.memberActive(assignment.getMemberId())) {
			return "MEMBER_INACTIVE";
		}
		if (!assignmentDAO.trainerActive(assignment.getTrainerId())) {
			return "TRAINER_INACTIVE";
		}
		if (assignmentDAO.hasTrainer(assignment.getMemberId())) {
			return "MEMBER_HAS_TRAINER";
		}
		if (assignmentDAO.addAssignment(assignment)) {
			return "SUCCESS";
		}
		return "ERROR";
	}

	public boolean updateAssignment(Assignment assignment) {
		return assignmentDAO.updateAssignment(assignment);
	}

	public boolean deleteAssignment(int assignmentId) {
		return assignmentDAO.deleteAssignment(assignmentId);
	}

	public Assignment getAssignmentById(int assignmentId) {
		return assignmentDAO.getAssignmentById(assignmentId);
	}

	public List<Assignment> getAllAssignment() {
		return assignmentDAO.getAllAssignment();
	}
}