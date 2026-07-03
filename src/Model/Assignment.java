package Model;

public class Assignment {
	private int assignmentId;
	private int trainerId;
	private int memberId;
	private String trainerName;
	private String memberName;
	private String assignedDate;
	private String status;

	public Assignment() {
	}

	public Assignment(int assignmentId, int trainerId, int memberId, String trainerName, String memberName,
			String assignedDate, String status) {
		super();
		this.assignmentId = assignmentId;
		this.trainerId = trainerId;
		this.memberId = memberId;
		this.trainerName = trainerName;
		this.memberName = memberName;
		this.assignedDate = assignedDate;
		this.status = status;
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}