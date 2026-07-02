package Service;

import Model.User;

public class OTPService {

	public boolean sendOTP(User user) {
		if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
			// TODO
			return true;
		}
		return false;
	}

	public boolean verifyOTP(User user, String otp) {
		if (user.getEmail() != null && user.getEmail().trim() != "") {
			// TODO
			return true;
		}
		return false;
	}
}
