package Controller;

import java.util.List;
import Model.Voucher;
import Service.VoucherService;

public class VoucherController {

	private VoucherService voucherService;

	public VoucherController() {
		voucherService = new VoucherService();
	}

	public boolean addVoucher(Voucher voucher) {
		return voucherService.addVoucher(voucher);
	}

	public boolean updateVoucher(Voucher voucher) {
		return voucherService.updateVoucher(voucher);
	}

	public boolean deleteVoucher(int voucherId) {
		return voucherService.deleteVoucher(voucherId);
	}

	public Voucher getVoucherById(int voucherId) {
		return voucherService.getVoucherById(voucherId);
	}
	public Voucher getVoucherByCode(String code) {
        return voucherService.getVoucherByCode(code);
    }
	public List<Voucher> getAllVoucher() {
		return voucherService.getAllVoucher();
	}
	public String applyVoucher(String code, double price) {
        return voucherService.applyVoucher(code, price);
    }

    public double calculateFinalPrice(String code, double price) {
        return voucherService.calculateFinalPrice(code, price);
    }
    public boolean increaseUsedCount(int voucherId) {
        return voucherService.increaseUsedCount(voucherId);
    }
}