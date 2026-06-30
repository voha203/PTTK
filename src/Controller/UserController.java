package Controller;

import View.LoginPage;
import View.UserPage;

public class UserController {
	private UserPage view;

	public UserController(UserPage view) {
		this.view = view;
	}

	public void openloginPage() {
		view.dispose();
		LoginPage loginPage = new LoginPage();
		LoginController controller = new LoginController(loginPage);
		loginPage.setController(controller);
		loginPage.setVisible(true);
	}
}