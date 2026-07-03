package zRun;

import Controller.LoginController;
import View.LoginPage;
import View.RatingView;

public class Main {

	public static void main(String[] args) {
		LoginPage loginPage = new LoginPage();
		LoginController controller = new LoginController(loginPage);
		loginPage.setController(controller);
		loginPage.setVisible(true);
	}
}

//public class Main {
//	public static void main(String[] args) {
//		new RatingView();
//	}
//}