package Controller;

import java.util.List;

import Model.Trainer;
import Service.TrainerService;

public class TrainerController {

	private TrainerService trainerService;

	public TrainerController() {

		trainerService = new TrainerService();
	}

	public boolean addTrainer(Trainer trainer) {

		return trainerService.addTrainer(trainer);
	}

	public boolean updateTrainer(Trainer trainer) {
		return trainerService.updateTrainer(trainer);
	}

	public boolean deleteTrainer(int trainerId) {
		return trainerService.deleteTrainer(trainerId);
	}

	public Trainer getTrainerById(int trainerId) {
		return trainerService.getTrainerById(trainerId);
	}

	public List<Trainer> getAllTrainer() {
		return trainerService.getAllTrainer();
	}
}