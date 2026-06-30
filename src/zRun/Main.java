package zRun;

import Controller.LoginController;
import View.LoginPage;

public class Main {

	public static void main(String[] args) {
		LoginPage loginPage = new LoginPage();
		LoginController controller = new LoginController(loginPage);
		loginPage.setController(controller);
		loginPage.setVisible(true);
	}
}