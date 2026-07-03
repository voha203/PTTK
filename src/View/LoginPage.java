package View;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.LoginController;

public class LoginPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnGoogleLogin, btnRegister, btnForgotPassword;
    private LoginController controller;

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public LoginPage() {
        setTitle("Login");
        setSize(400, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("  LOGIN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(150, 20, 120, 30);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 70, 120, 25);

        txtEmail = new JTextField();
        txtEmail.setBounds(160, 70, 180, 25);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40, 110, 120, 25);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 110, 180, 25);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 160, 180, 30);

        JLabel lblOr = new JLabel("        ----- OR -----");
        lblOr.setBounds(140, 195, 120, 20);

        btnGoogleLogin = new JButton("Login with Google");
        btnGoogleLogin.setBounds(40, 220, 310, 30); 
		
        btnRegister = new JButton("Register");
        btnRegister.setBounds(40, 260, 140, 30);

        btnForgotPassword = new JButton("Forgot Password");
        btnForgotPassword.setBounds(210, 260, 140, 30);

        panel.add(lblTitle);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(lblOr);
        panel.add(btnGoogleLogin);
        panel.add(btnRegister);
        panel.add(btnForgotPassword);

        add(panel);
        setVisible(true);

        // Event listeners
        btnLogin.addActionListener(e -> {
            if (getEmail().isEmpty() || getPassword().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all required information.");
                return;
            }

            controller.login(getEmail(), getPassword());
        });

        btnGoogleLogin.addActionListener(e -> {
			try {
				controller.SocialAccount("Google");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        btnRegister.addActionListener(e -> controller.openRegisterPage());
        btnForgotPassword.addActionListener(e -> controller.openForgotPasswordPage());
    }

    private String getEmail() {
        return txtEmail.getText().trim();
    }

    private String getPassword() {
        return new String(txtPassword.getPassword());
    }
}