package View;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Controller.UserController;

public class UserPage extends JFrame {
    private UserController controller;

    private static final long serialVersionUID = 1L;

    public void setController(UserController controller) {
        this.controller = controller;
    }

    public UserPage(String username) {

        setTitle("User Page");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnLogout = new JButton("Logout");

        panel.add(lblWelcome, BorderLayout.CENTER);
        panel.add(btnLogout, BorderLayout.SOUTH);
        add(panel);

        btnLogout.addActionListener(e -> controller.openloginPage());
    }
}