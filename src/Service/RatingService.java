package Service;

import java.util.List;

import Dao.RatingDAO;
import Model.Rating;

public class RatingService {

    private RatingDAO ratingDAO;

    public RatingService() {
        ratingDAO = new RatingDAO();
    }

    public String addRating(Rating rating) {

        if (!ratingDAO.bookingCompleted(rating.getBookingId()))
            return "BOOKING_NOT_COMPLETED";

        if (ratingDAO.alreadyRated(rating.getBookingId()))
            return "ALREADY_RATED";

        if (rating.getStar() < 1 || rating.getStar() > 5)
            return "INVALID_STAR";

        if (ratingDAO.addRating(rating)) {

            ratingDAO.updateTrainerRating(rating.getTrainerId());

            return "SUCCESS";
        }

        return "ERROR";
    }

    public boolean updateRating(Rating rating) {

        if (rating.getStar() < 1 || rating.getStar() > 5)
            return false;

        if (ratingDAO.updateRating(rating)) {

            ratingDAO.updateTrainerRating(rating.getTrainerId());

            return true;
        }

        return false;
    }

    public boolean deleteRating(int ratingId) {

        Rating rating = ratingDAO.getRatingById(ratingId);

        if (rating == null)
            return false;

        if (ratingDAO.deleteRating(ratingId)) {

            ratingDAO.updateTrainerRating(rating.getTrainerId());

            return true;
        }

        return false;
    }

    public Rating getRatingById(int ratingId) {
        return ratingDAO.getRatingById(ratingId);
    }

    public List<Rating> getAllRating() {
        return ratingDAO.getAllRating();
    }

}