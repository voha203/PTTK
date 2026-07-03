package Controller;

import java.util.List;

import Model.Rating;
import Service.RatingService;

public class RatingController {

    private RatingService ratingService;

    public RatingController() {
        ratingService = new RatingService();
    }

    public String addRating(Rating rating) {
        return ratingService.addRating(rating);
    }

    public boolean updateRating(Rating rating) {
        return ratingService.updateRating(rating);
    }

    public boolean deleteRating(int ratingId) {
        return ratingService.deleteRating(ratingId);
    }

    public Rating getRatingById(int ratingId) {
        return ratingService.getRatingById(ratingId);
    }

    public List<Rating> getAllRating() {
        return ratingService.getAllRating();
    }

}