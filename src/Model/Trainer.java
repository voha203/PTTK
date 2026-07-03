package Model;

public class Trainer {
	private int trainerId;
	private String trainerName;
	private String phone;
	private String email;
	private String specialization;
	private int experience;
	private float rating;
	private String status;

	public Trainer() {
	}

	public Trainer(int trainerId, String trainerName, String phone, String email, String specialization, int experience,
			float rating, String status) {
		super();
		this.trainerId = trainerId;
		this.trainerName = trainerName;
		this.phone = phone;
		this.email = email;
		this.specialization = specialization;
		this.experience = experience;
		this.rating = rating;
		this.status = status;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
	    return trainerName;
	}
}