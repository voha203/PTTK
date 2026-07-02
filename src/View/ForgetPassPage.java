package View;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.ForgetPassController;

public class ForgetPassPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfEmail, jtfOTP;
	private JButton jbtConfirmEmail, jbtToLogin, jbtResendOTP, jbtConfirmOTP;
	private ForgetPassController controller;
	private CardLayout cardLayout;

	private JPanel forgetPassView, typeOTPView, newPasswordView, returnToLoginView;

	public ForgetPassPage(ForgetPassController ctrl) {
		this.controller = ctrl;
		setTitle("ForgetPass");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		cardLayout = new CardLayout();
		setLayout(cardLayout);

		/**
		 * ================= Forget password =================
		 */
		forgetPassView = new JPanel();
		forgetPassView.setLayout(new BoxLayout(forgetPassView, BoxLayout.Y_AXIS));
		getContentPane().add(forgetPassView, "forgetPass");

		forgetPassView.add(new JLabel("Forget pass"));

		JPanel row1ForgetPass = new JPanel();
		forgetPassView.add(row1ForgetPass);
		row1ForgetPass.add(new JLabel("Email: "));
		jtfEmail = new JTextField(20);
		row1ForgetPass.add(jtfEmail);

		JPanel row2ForgetPass = new JPanel();
		forgetPassView.add(row2ForgetPass);
		jbtConfirmEmail = new JButton("Confirm");
		jbtConfirmEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.verifyAccount(jtfEmail.getText());
			}
		});
		row2ForgetPass.add(jbtConfirmEmail);
		jbtToLogin = new JButton("Return to login");
		jbtToLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showLogin();
			}
		});
		row2ForgetPass.add(jbtToLogin);

		/**
		 * ================= Type OTP =================
		 */
		typeOTPView = new JPanel();
		typeOTPView.setLayout(new BoxLayout(typeOTPView, BoxLayout.Y_AXIS));
		getContentPane().add(typeOTPView, "typeOTP");

		typeOTPView.add(new JLabel("Type OTP"));

		JPanel row1TypeOTP = new JPanel();
		typeOTPView.add(row1TypeOTP);
		row1TypeOTP.add(new JLabel("OTP: "));
		jtfOTP = new JTextField(20);
		row1TypeOTP.add(jtfOTP);
		jbtResendOTP = new JButton("Resend OTP");
		jbtResendOTP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.resendOTP();
			}
		});
		row1TypeOTP.add(jbtResendOTP);

		jbtConfirmOTP = new JButton("Confirm");
		jbtConfirmOTP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.verifyOTP(jtfEmail.getText());
			}
		});
		typeOTPView.add(jbtConfirmOTP);

		/**
		 * ================= New Password =================
		 */
		newPasswordView = new JPanel();
		newPasswordView.setLayout(new BoxLayout(newPasswordView, BoxLayout.Y_AXIS));
		getContentPane().add(newPasswordView, "newPassword");

		/**
		 * ================= Return to login =================
		 */
		returnToLoginView = new JPanel();
		returnToLoginView.setLayout(new BoxLayout(returnToLoginView, BoxLayout.Y_AXIS));
		getContentPane().add(returnToLoginView, "returnToLogin");

		setVisible(true);
	}

	public void showTypeOTP() {
		cardLayout.show(getContentPane(), "typeOTP");
	}

	public void showNewPassword() {
		cardLayout.show(getContentPane(), "newPassword");
	}

	public void showErrorUserNotFound() {
		JOptionPane.showMessageDialog(this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void showErrorOTPNotSent() {
		JOptionPane.showMessageDialog(this, "OTP can't be sent", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void showErrorIncorrectOTP() {
		JOptionPane.showMessageDialog(this, "OTP is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
	}

}
