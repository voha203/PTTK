package View;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.RegisterController;

public class RegisterPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsername, txtEmail;
	private JPasswordField txtPassword, txtConfirmPassword;
	private JButton btnRegister, btnBack, btnGoogleRegister;
	private RegisterController controller;

	public void setController(RegisterController controller) {
		this.controller = controller;
	}

	public RegisterPage() {
		setTitle("Register");
		setSize(450, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel lblTitle = new JLabel("REGISTER");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setBounds(150, 20, 150, 30);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(40, 80, 120, 25);

		txtUsername = new JTextField();
		txtUsername.setBounds(170, 80, 200, 25);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(40, 120, 120, 25);

		txtEmail = new JTextField();
		txtEmail.setBounds(170, 120, 200, 25);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(40, 160, 120, 25);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(170, 160, 200, 25);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(40, 200, 120, 25);

		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setBounds(170, 200, 200, 25);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(80, 270, 120, 35);

		btnBack = new JButton("Back");
		btnBack.setBounds(240, 270, 120, 35);
	
		btnGoogleRegister = new JButton("Register with Google");
		btnGoogleRegister.setBounds(80, 320, 280, 35);

		panel.add(lblTitle);
		panel.add(lblUsername);
		panel.add(txtUsername);
		panel.add(lblEmail);
		panel.add(txtEmail);
		panel.add(lblPassword);
		panel.add(txtPassword);
		panel.add(lblConfirmPassword);
		panel.add(txtConfirmPassword);
		panel.add(btnRegister);
		panel.add(btnBack);
		panel.add(btnGoogleRegister);
		add(panel);

		setVisible(true);

		btnRegister.addActionListener(e -> {
			controller.register(getUsername(), getEmail(), getPassword(), getConfirmPassword());
		});
		btnBack.addActionListener(e -> controller.openloginPage());
		btnGoogleRegister.addActionListener(e -> {
		    try {
				controller.registerWithGoogle();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	private String getUsername() {
		return txtUsername.getText().trim();
	}

	private String getEmail() {
		return txtEmail.getText().trim();
	}

	private String getPassword() {
		return new String(txtPassword.getPassword());
	}

	private String getConfirmPassword() {
		return new String(txtConfirmPassword.getPassword());
	}
}