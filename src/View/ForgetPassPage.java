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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.ForgetPassController;

public class ForgetPassPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfEmail, jtfOTP;
	private JPasswordField jpfPass, jpfRePass;
	private JButton jbtConfirmEmail, jbtToLogin, jbtResendOTP, jbtConfirmOTP, jbtConfirmPass, jbtToLogin2;
	private ForgetPassController controller;
	private CardLayout cardLayout;

	private JPanel forgetPassView, typeOTPView, newPasswordView, passChangedSuccessView;

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
		
		newPasswordView.add(new JLabel("New password"));
		
		JPanel row1NewPass = new JPanel();
		newPasswordView.add(row1NewPass);
		row1NewPass.add(new JLabel("Password: "));
		jpfPass = new JPasswordField(20);
		row1NewPass.add(jpfPass);
		
		JPanel row2NewPass = new JPanel();
		newPasswordView.add(row2NewPass);
		row2NewPass.add(new JLabel("Confirm password: "));
		jpfRePass = new JPasswordField(20);
		row2NewPass.add(jpfRePass);
		
		jbtConfirmPass = new JButton("Confirm");
		jbtConfirmPass.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.verifyPassword(new String(jpfPass.getPassword()), new String(jpfRePass.getPassword()));
			}
		});
		newPasswordView.add(jbtConfirmPass);

		/**
		 * ================= Password changed success =================
		 */
		passChangedSuccessView = new JPanel();
		passChangedSuccessView.setLayout(new BoxLayout(passChangedSuccessView, BoxLayout.Y_AXIS));
		getContentPane().add(passChangedSuccessView, "passChangedSuccess");
		
		passChangedSuccessView.add(new JLabel("You changed password successfully"));
		jbtToLogin2 = new JButton("Return to login");
		jbtToLogin2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showLogin();
			}
		});
		passChangedSuccessView.add(jbtToLogin2);
		setVisible(true);
	}

	public void showTypeOTP() {
		cardLayout.show(getContentPane(), "typeOTP");
	}

	public void showNewPassword() {
		cardLayout.show(getContentPane(), "newPassword");
	}
	
	public void showPassChangedSuccess() {
		cardLayout.show(getContentPane(), "passChangedSuccess");
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

	public void showErrorWrongPassword() {
		JOptionPane.showMessageDialog(this, "Password is wrong", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
