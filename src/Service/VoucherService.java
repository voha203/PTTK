package Service;

import java.time.LocalDate;
import java.util.List;
import Dao.VoucherDAO;
import Model.Voucher;

public class VoucherService {
	private VoucherDAO voucherDAO;

	public VoucherService() {
		voucherDAO = new VoucherDAO();
	}

	public boolean addVoucher(Voucher voucher) {
		if (voucher.getVoucherCode() == null || voucher.getVoucherCode().trim().isEmpty()) {
			return false;
		}
		if (voucher.getPromotionId() <= 0) {
			return false;
		}
		return voucherDAO.addVoucher(voucher);
	}

	public boolean updateVoucher(Voucher voucher) {
		if (voucher.getPromotionId() <= 0) {
			return false;
		}
		return voucherDAO.updateVoucher(voucher);
	}

	public boolean deleteVoucher(int voucherId) {
		return voucherDAO.deleteVoucher(voucherId);
	}

	public Voucher getVoucherById(int voucherId) {
		return voucherDAO.getVoucherById(voucherId);
	}
	public Voucher getVoucherByCode(String code) {
        return voucherDAO.getVoucherByCode(code);
    }
	public List<Voucher> getAllVoucher() {
		return voucherDAO.getAllVoucher();
	}

	// Áp dụng Voucher 
	public String applyVoucher(String code, double price) {
		Voucher voucher = voucherDAO.getVoucherByCode(code);

		if (voucher == null) {
			return "NOT_FOUND";
		}
		if (!voucher.getStatus().equalsIgnoreCase("Active")) {
			return "INACTIVE";
		}
		if (LocalDate.now().isAfter(LocalDate.parse(voucher.getExpireDate()))) {
			return "EXPIRED";
		}
		if (voucher.getUsedCount() >= voucher.getQuantity()) {
			return "OUT_OF_QUANTITY";
		}
		if (price < voucher.getMinimumAmount()) {
			return "MIN_AMOUNT";
		}

		return "SUCCESS";
	}

	// Tính toán số tiền cuối cùng 
	public double calculateFinalPrice(String code, double price) {
		Voucher voucher = voucherDAO.getVoucherByCode(code);

		if (voucher == null) {
			return price;
		}

		return price - price * voucher.getDiscountPercent() / 100.0;
	}
	public boolean increaseUsedCount(int voucherId) {
	    return voucherDAO.increaseUsedCount(voucherId);
	}
}