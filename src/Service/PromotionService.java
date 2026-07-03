package Service;

import java.util.List;
import Dao.PromotionDAO;
import Model.Promotion;

public class PromotionService {
	private PromotionDAO promotionDAO;

	public PromotionService() {
		promotionDAO = new PromotionDAO();
	}

	public boolean addPromotion(Promotion promotion) {
		return promotionDAO.addPromotion(promotion);
	}

	public boolean updatePromotion(Promotion promotion) {
		return promotionDAO.updatePromotion(promotion);
	}

	public boolean deletePromotion(int promotionId) {
		return promotionDAO.deletePromotion(promotionId);
	}

	public Promotion getPromotionById(int promotionId) {
		return promotionDAO.getPromotionById(promotionId);
	}

	public List<Promotion> getAllPromotion() {
		return promotionDAO.getAllPromotion();
	}
}