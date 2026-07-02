package Service;

import java.util.List;

import Dao.TrainerDAO;
import Model.Trainer;

public class TrainerService {

	private TrainerDAO trainerDAO = new TrainerDAO();

	public boolean addTrainer(Trainer trainer) {
		return trainerDAO.addTrainer(trainer);
	}

	public boolean updateTrainer(Trainer trainer) {
		return trainerDAO.updateTrainer(trainer);
	}

	public boolean deleteTrainer(int trainerId) {
		return trainerDAO.deleteTrainer(trainerId);
	}

	public Trainer getTrainerById(int trainerId) {
		return trainerDAO.getTrainerById(trainerId);
	}

	public List<Trainer> getAllTrainer() {
		return trainerDAO.getAllTrainer();
	}

}