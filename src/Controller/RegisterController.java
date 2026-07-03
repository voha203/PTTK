package Controller;

import javax.swing.JOptionPane;

import Model.User;
import Service.RegisterService;
import Service.SocialAuthService;
import View.LoginPage;
import View.RegisterPage;
import View.UserPage;

public class RegisterController {

	private RegisterPage view;
	private RegisterService registerService;
	private SocialAuthService socialAuthService;

	public RegisterController(RegisterPage view) {
		this.view = view;
		this.registerService = new RegisterService();
		this.socialAuthService = new SocialAuthService();
	}

	public static boolean isValidEmail(String email) {
		return email != null && email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$");
	}

	public void register(String username, String email, String password, String confirmPassword) {
		if ("".equals(username) || "".equals(email) || "".equals(password) || "".equals(confirmPassword)) {
			JOptionPane.showMessageDialog(view, "Please fill in all required fields.");
			return;
		}
		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(view, "Passwords do not match.");
			return;
		}
		if (!isValidEmail(email)) {
			JOptionPane.showMessageDialog(view, "Invalid email format!");
			return;
		}
		User user = new User();
		
		String[] parts = username.trim().split("\\s+");
		String name = parts[parts.length - 1];
		
		user.setUsername(name);
		user.setFullName(username);
		user.setEmail(email);
		user.setProvider("LOCAL");
		user.setStatus(true);
		user.setPasswordHash(password);
		String mess = registerService.register(user);
		JOptionPane.showMessageDialog(view, mess);
		if ("Registration successful.".equals(mess)) {
			openUserPage(user);
		}
	}

	public void openUserPage(User user) {
		view.dispose();
		UserPage userPage = new UserPage(user.getUsername());
		UserController userController = new UserController(userPage);
		userPage.setController(userController);
		userPage.setVisible(true);
	}

	public void openloginPage() {
		view.dispose();
		LoginPage loginPage = new LoginPage();
		LoginController controller = new LoginController(loginPage);
		loginPage.setController(controller);
		loginPage.setVisible(true);
	}

	public void registerWithGoogle() throws Exception {
		User googleUser = socialAuthService.loginWithGoogle();
		String mess = registerService.register(googleUser);
		JOptionPane.showMessageDialog(view, mess);
		if ("Registration successful.".equals(mess)) {
			openUserPage(googleUser);
		}
	}
}