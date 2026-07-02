package View;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.TrainerController;
import Model.Trainer;

public class TrainerView extends JFrame {
    private static final long serialVersionUID = 1L;

    private TrainerController controller;
    private JTextField txtName, txtPhone, txtEmail, txtSpecialization, txtExperience, txtRating;
    private JComboBox<String> cboStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;
    private JTable table;
    private DefaultTableModel model;

    public TrainerView() {
        controller = new TrainerController();
        initComponents();
        loadTable();
        event();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Trainer Management");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("TRAINER MANAGEMENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(300, 20, 350, 30);
        panel.add(lblTitle);

        JLabel lblName = new JLabel("Trainer Name:");
        lblName.setBounds(40, 80, 120, 25);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(170, 80, 220, 25);
        panel.add(txtName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(40, 120, 120, 25);
        panel.add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(170, 120, 220, 25);
        panel.add(txtPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 160, 120, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(170, 160, 220, 25);
        panel.add(txtEmail);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(40, 200, 120, 25);
        panel.add(lblStatus);

        cboStatus = new JComboBox<>();
        cboStatus.addItem("Active");
        cboStatus.addItem("Inactive");
        cboStatus.setBounds(170, 200, 220, 25);
        panel.add(cboStatus);

        JLabel lblSpecialization = new JLabel("Specialization:");
        lblSpecialization.setBounds(470, 80, 120, 25);
        panel.add(lblSpecialization);

        txtSpecialization = new JTextField();
        txtSpecialization.setBounds(600, 80, 220, 25);
        panel.add(txtSpecialization);

        JLabel lblExperience = new JLabel("Experience:");
        lblExperience.setBounds(470, 120, 120, 25);
        panel.add(lblExperience);

        txtExperience = new JTextField();
        txtExperience.setBounds(600, 120, 220, 25);
        panel.add(txtExperience);

        JLabel lblRating = new JLabel("Rating:");
        lblRating.setBounds(470, 160, 120, 25);
        panel.add(lblRating);

        txtRating = new JTextField();
        txtRating.setBounds(600, 160, 220, 25);
        panel.add(txtRating);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(120, 270, 100, 35);
        panel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(250, 270, 100, 35);
        panel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(380, 270, 100, 35);
        panel.add(btnDelete);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(510, 270, 100, 35);
        panel.add(btnRefresh);

        String[] columns = {"ID", "Trainer Name", "Phone", "Email", "Specialization", "Experience", "Rating", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 330, 880, 240);
        panel.add(scrollPane);

        add(panel);
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Trainer> list = controller.getAllTrainer();
        for (Trainer t : list) {
            model.addRow(new Object[] {
                t.getTrainerId(),
                t.getTrainerName(),
                t.getPhone(),
                t.getEmail(),
                t.getSpecialization(),
                t.getExperience(),
                t.getRating(),
                t.getStatus()
            });
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSpecialization.setText("");
        txtExperience.setText("");
        txtRating.setText("");
        cboStatus.setSelectedIndex(0);
    }

    private Trainer getTrainer() {
        Trainer trainer = new Trainer();
        int row = table.getSelectedRow();
        if (row != -1) {
            trainer.setTrainerId(Integer.parseInt(model.getValueAt(row, 0).toString()));
        }
        trainer.setTrainerName(txtName.getText());
        trainer.setPhone(txtPhone.getText());
        trainer.setEmail(txtEmail.getText());
        trainer.setSpecialization(txtSpecialization.getText());
        trainer.setExperience(Integer.parseInt(txtExperience.getText()));
        trainer.setRating(Float.parseFloat(txtRating.getText()));
        trainer.setStatus(cboStatus.getSelectedItem().toString());
        return trainer;
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        txtName.setText(model.getValueAt(row, 1).toString());
        txtPhone.setText(model.getValueAt(row, 2).toString());
        txtEmail.setText(model.getValueAt(row, 3).toString());
        txtSpecialization.setText(model.getValueAt(row, 4).toString());
        txtExperience.setText(model.getValueAt(row, 5).toString());
        txtRating.setText(model.getValueAt(row, 6).toString());
        cboStatus.setSelectedItem(model.getValueAt(row, 7));
    }

    private void event() {
        btnAdd.addActionListener(e -> {
            if (controller.addTrainer(getTrainer())) {
                JOptionPane.showMessageDialog(null, "Add Success");
                loadTable();
                clearForm();
            }
        });

        btnUpdate.addActionListener(e -> {
            if (controller.updateTrainer(getTrainer())) {
                JOptionPane.showMessageDialog(null, "Update Success");
                loadTable();
                clearForm();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            if (controller.deleteTrainer(id)) {
                JOptionPane.showMessageDialog(null, "Delete Success");
                loadTable();
                clearForm();
            }
        });

        btnRefresh.addActionListener(e -> {
            clearForm();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                fillForm();
            }
        });
    }
}