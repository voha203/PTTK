package Model;

public class Rating {
	private int ratingId;
	private int bookingId;
	private int trainerId;
	private int memberId;
	private String trainerName;
	private String memberName;
	private int star;
	private String comment;
	private String ratingDate;

	public Rating() {
	}

	public Rating(int ratingId, int bookingId, int trainerId, int memberId, String trainerName, String memberName,
			int star, String comment, String ratingDate) {
		super();
		this.ratingId = ratingId;
		this.bookingId = bookingId;
		this.trainerId = trainerId;
		this.memberId = memberId;
		this.trainerName = trainerName;
		this.memberName = memberName;
		this.star = star;
		this.comment = comment;
		this.ratingDate = ratingDate;
	}

	public int getRatingId() {
		return ratingId;
	}

	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
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

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}
	
}