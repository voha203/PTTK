package Controller;

import javax.swing.JOptionPane;

import Model.User;
import Service.LoginService;
import Service.SocialAuthService;
import View.LoginPage;
import View.RegisterPage;
import View.UserPage;

public class LoginController {

	private LoginPage view;
	private LoginService logService;
	private SocialAuthService socialAuthService;

	public LoginController(LoginPage view) {
		this.view = view;
		this.logService = new LoginService();
		this.socialAuthService = new SocialAuthService();
	}

	public static boolean isValidEmail(String email) {
		return email != null && email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$");
	}

	public void login(String email, String password) {
		if (!isValidEmail(email)) {
			JOptionPane.showMessageDialog(view, "Invalid email format!");
			return;
		}

		String mess = logService.checkLogin(email, password, "LOCAL", null);

		if ("Login successful.".equals(mess)) {
			JOptionPane.showMessageDialog(view, mess);
			view.dispose();

			User user = logService.getUserLogin(email);
			UserPage userPage = new UserPage(user.getUsername());

			UserController userController = new UserController(userPage);
			userPage.setController(userController);
			userPage.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(view, mess);
		}
	}

	public void SocialAccount(String pro) throws Exception {
		User socialUser = null;

		if ("Google".equals(pro)) {
			socialUser = socialAuthService.loginWithGoogle();
		} 

		if (socialUser == null) {
			JOptionPane.showMessageDialog(view, "Unable to retrieve user information from social account.");
			return;
		}

		String email = socialUser.getEmail();
		String provider = socialUser.getProvider();
		String providerId = socialUser.getProviderId();

		String mess = logService.checkLogin(email, "", provider, providerId);

		if ("Login successful.".equals(mess)) {
			JOptionPane.showMessageDialog(view, mess);
			view.dispose();

			User user = logService.getUserLogin(email);
			openUserPage(user);
		} else {
			JOptionPane.showMessageDialog(view, mess);
		}
	}

	public void openUserPage(User user) {
		view.dispose();
		UserPage userPage = new UserPage(user.getUsername());
		UserController userController = new UserController(userPage);
		userPage.setController(userController);
		userPage.setVisible(true);
	}

	public void openRegisterPage() {
		view.dispose();
		RegisterPage registerPage = new RegisterPage();
		RegisterController registerController = new RegisterController(registerPage);
		registerPage.setController(registerController);
		registerPage.setVisible(true);
	}

	public void openForgotPasswordPage() {
		view.dispose();
		new ForgetPassController();
	}
}