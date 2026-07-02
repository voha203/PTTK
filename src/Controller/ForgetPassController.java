package Controller;

import Model.User;
import Service.EmailService;
import Service.OTPService;
import Service.UserService;
import View.ForgetPassPage;
import View.LoginPage;

public class ForgetPassController {

	private User user;
	private ForgetPassPage view;
	private UserService us = new UserService();
	private OTPService os = new OTPService();
	private EmailService es = new EmailService();

	public ForgetPassController() {
		super();
		this.view = new ForgetPassPage(this);
	}

	public void verifyAccount(String email) {
		User user = us.verifyAccount(email);
		if (user != null) {
			this.user = user;
			if (os.sendOTP(user)) {
				view.showTypeOTP();
			} else {
				view.showErrorOTPNotSent();
			}
		} else {
			view.showErrorUserNotFound();
		}
	}

	public void showLogin() {
		this.view.dispose();
		LoginPage loginPage = new LoginPage();
		LoginController controller = new LoginController(loginPage);
		loginPage.setController(controller);
	}

	public void resendOTP() {
		os.sendOTP(user);
	}

	public void verifyOTP(String otp) {
		if (os.verifyOTP(user, otp)) {
			view.showNewPassword();
		} else {
			view.showErrorIncorrectOTP();
		}
	}

	public static void main(String[] args) {
		LoginPage loginPage = new LoginPage();
		LoginController controller = new LoginController(loginPage);
		loginPage.setController(controller);
		loginPage.setVisible(true);
	}

	public void verifyPassword(String pass, String rePass) {
		if (pass.equals(rePass)) {
			this.us.changePassword(user, pass);
			this.es.notifyPasswordChanged(user);
			view.showPassChangedSuccess();
		} else {
			view.showErrorWrongPassword();
		}
	}
}
