package View;

import Controller.TrainerController;
import Model.Trainer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TrainerView extends JFrame {
	private static final long serialVersionUID = 1L;
	private TrainerController controller = new TrainerController();
	private JTextField txtName, txtPhone, txtEmail, txtSpecialization, txtExperience, txtRating;
	private JComboBox<String> cboStatus;
	private JButton btnAdd, btnUpdate, btnDelete;
	private JTable table;
	private DefaultTableModel model;

	public TrainerView() {
		setTitle("Trainer Management");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(30, 20, 100, 25);
		add(lblName);
		txtName = new JTextField();
		txtName.setBounds(120, 20, 200, 25);
		add(txtName);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(30, 60, 100, 25);
		add(lblPhone);
		txtPhone = new JTextField();
		txtPhone.setBounds(120, 60, 200, 25);
		add(txtPhone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(30, 100, 100, 25);
		add(lblEmail);
		txtEmail = new JTextField();
		txtEmail.setBounds(120, 100, 200, 25);
		add(txtEmail);

		JLabel lblSpecialization = new JLabel("Specialization");
		lblSpecialization.setBounds(400, 20, 100, 25);
		add(lblSpecialization);
		txtSpecialization = new JTextField();
		txtSpecialization.setBounds(520, 20, 200, 25);
		add(txtSpecialization);

		JLabel lblExperience = new JLabel("Experience");
		lblExperience.setBounds(400, 60, 100, 25);
		add(lblExperience);
		txtExperience = new JTextField();
		txtExperience.setBounds(520, 60, 200, 25);
		add(txtExperience);

		JLabel lblRating = new JLabel("Rating");
		lblRating.setBounds(400, 100, 100, 25);
		add(lblRating);
		txtRating = new JTextField();
		txtRating.setBounds(520, 100, 200, 25);
		add(txtRating);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(30, 140, 100, 25);
		add(lblStatus);
		cboStatus = new JComboBox<>(new String[] { "Active", "Inactive" });
		cboStatus.setBounds(120, 140, 200, 25);
		add(cboStatus);

		btnAdd = new JButton("Add");
		btnAdd.setBounds(120, 190, 90, 30);
		add(btnAdd);
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(220, 190, 90, 30);
		add(btnUpdate);
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(320, 190, 90, 30);
		add(btnDelete);

		model = new DefaultTableModel(
				new String[] { "ID", "Name", "Phone", "Email", "Specialization", "Experience", "Rating", "Status" }, 0);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 250, 840, 280);
		add(scrollPane);

		loadTable();
		addEvents();
		setVisible(true);
	}

	private void loadTable() {
		model.setRowCount(0);
		List<Trainer> list = controller.getAllTrainer();
		for (Trainer t : list) {
			model.addRow(new Object[] { t.getTrainerId(), t.getTrainerName(), t.getPhone(), t.getEmail(),
					t.getSpecialization(), t.getExperience(), t.getRating(), t.getStatus() });
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
		table.clearSelection();
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
		if (!txtExperience.getText().isEmpty()) {
			trainer.setExperience(Integer.parseInt(txtExperience.getText()));
		}
		if (!txtRating.getText().isEmpty()) {
			trainer.setRating(Float.parseFloat(txtRating.getText()));
		}
		trainer.setStatus(cboStatus.getSelectedItem().toString());
		return trainer;
	}

	private void addEvents() {
		btnAdd.addActionListener(e -> {
			if (controller.addTrainer(getTrainer())) {
				JOptionPane.showMessageDialog(this, "Add Success");
				loadTable();
				clearForm();
			} else {
				JOptionPane.showMessageDialog(this, "Add Failed");
			}
		});

		btnUpdate.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Select Trainer");
				return;
			}
			if (controller.updateTrainer(getTrainer())) {
				JOptionPane.showMessageDialog(this, "Update Success");
				loadTable();
				clearForm();
			} else {
				JOptionPane.showMessageDialog(this, "Update Failed");
			}
		});

		btnDelete.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Select Trainer");
				return;
			}
			int id = Integer.parseInt(model.getValueAt(row, 0).toString());
			if (JOptionPane.showConfirmDialog(this, "Delete this trainer?", "Confirm",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if (controller.deleteTrainer(id)) {
					JOptionPane.showMessageDialog(this, "Delete Success");
					loadTable();
					clearForm();
				} else {
					JOptionPane.showMessageDialog(this, "Delete Failed");
				}
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			if (e.getValueIsAdjusting()) return;
			int row = table.getSelectedRow();
			if (row == -1) return;
			txtName.setText(model.getValueAt(row, 1).toString());
			txtPhone.setText(model.getValueAt(row, 2).toString());
			txtEmail.setText(model.getValueAt(row, 3).toString());
			txtSpecialization.setText(model.getValueAt(row, 4).toString());
			txtExperience.setText(model.getValueAt(row, 5).toString());
			txtRating.setText(model.getValueAt(row, 6).toString());
			cboStatus.setSelectedItem(model.getValueAt(row, 7).toString());
		});
	}
}