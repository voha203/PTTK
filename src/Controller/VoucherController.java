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

	public List<Voucher> getAllVoucher() {
		return voucherService.getAllVoucher();
	}
}