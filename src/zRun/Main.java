//package zRun;
//
//import Controller.LoginController;
//import View.LoginPage;
//
//public class Main {
//
//	public static void main(String[] args) {
//		LoginPage loginPage = new LoginPage();
//		LoginController controller = new LoginController(loginPage);
//		loginPage.setController(controller);
//		loginPage.setVisible(true);
//	}
//}
//package zRun;
//
//import javax.swing.SwingUtilities;
//import View.TrainerView;
//
//public class Main {
//
//    public static void main(String[] args) {
//        System.out.println("[DEBUG] Ứng dụng bắt đầu khởi chạy...");
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TrainerView trainerView = new TrainerView();
//                    trainerView.setVisible(true);
//                    System.out.println("[DEBUG] Đã hiển thị giao diện TrainerView thành công!");
//                } catch (Exception e) {
//                    System.out.println("[DEBUG] LỖI khi khởi tạo giao diện TrainerView:");
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
package zRun;

import View.BookingView;
import javax.swing.SwingUtilities;
public class Main {

    public static void main(String[] args) {

    	SwingUtilities.invokeLater(() -> new BookingView());
    }

}