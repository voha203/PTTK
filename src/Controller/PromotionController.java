package Controller;

import java.util.List;
import Model.Promotion;
import Service.PromotionService;

public class PromotionController {
	private PromotionService promotionService;

	public PromotionController() {
		promotionService = new PromotionService();
	}

	public boolean addPromotion(Promotion promotion) {
		return promotionService.addPromotion(promotion);
	}

	public boolean updatePromotion(Promotion promotion) {
		return promotionService.updatePromotion(promotion);
	}

	public boolean deletePromotion(int promotionId) {
		return promotionService.deletePromotion(promotionId);
	}

	public Promotion getPromotionById(int promotionId) {
		return promotionService.getPromotionById(promotionId);
	}

	public List<Promotion> getAllPromotion() {
		return promotionService.getAllPromotion();
	}
}