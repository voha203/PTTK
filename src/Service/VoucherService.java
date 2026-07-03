package Service;

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

	public List<Voucher> getAllVoucher() {
		return voucherDAO.getAllVoucher();
	}
}