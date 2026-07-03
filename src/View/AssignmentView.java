package View;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.AssignmentController;
import Controller.MemberController;
import Controller.TrainerController;
import Model.Assignment;
import Model.Member;
import Model.Trainer;

public class AssignmentView extends JFrame {

	private AssignmentController controller = new AssignmentController();
	private MemberController memberController = new MemberController();
	private TrainerController trainerController = new TrainerController();

	private JComboBox<Member> cboMember;
	private JComboBox<Trainer> cboTrainer;
	private JTextField txtDate;
	private JComboBox<String> cboStatus;
	private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;
	private JTable table;
	private DefaultTableModel model;

	public AssignmentView() {
		initComponents();
		loadMember();
		loadTrainer();
		loadTable();
		addEvents();
		setVisible(true);
	}

	private void initComponents() {
		setTitle("Assignment Management");
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p = new JPanel(null);

		JLabel lb = new JLabel("ASSIGNMENT MANAGEMENT");
		lb.setFont(new Font("Arial", Font.BOLD, 22));
		lb.setBounds(250, 20, 400, 30);
		p.add(lb);

		p.add(new JLabel("Member")).setBounds(40, 80, 100, 25);
		cboMember = new JComboBox<>();
		cboMember.setBounds(150, 80, 220, 25);
		p.add(cboMember);

		p.add(new JLabel("Trainer")).setBounds(40, 120, 100, 25);
		cboTrainer = new JComboBox<>();
		cboTrainer.setBounds(150, 120, 220, 25);
		p.add(cboTrainer);

		p.add(new JLabel("Assigned Date")).setBounds(450, 80, 100, 25);
		txtDate = new JTextField();
		txtDate.setBounds(560, 80, 220, 25);
		p.add(txtDate);

		p.add(new JLabel("Status")).setBounds(450, 120, 100, 25);
		cboStatus = new JComboBox<>();
		cboStatus.addItem("Active");
		cboStatus.addItem("Inactive");
		cboStatus.setBounds(560, 120, 220, 25);
		p.add(cboStatus);

		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnRefresh = new JButton("Refresh");

		btnAdd.setBounds(120, 220, 100, 35);
		btnUpdate.setBounds(240, 220, 100, 35);
		btnDelete.setBounds(360, 220, 100, 35);
		btnRefresh.setBounds(480, 220, 100, 35);

		p.add(btnAdd);
		p.add(btnUpdate);
		p.add(btnDelete);
		p.add(btnRefresh);

		model = new DefaultTableModel(new String[]{
				"ID", "Member", "Trainer", "Assigned Date", "Status"
		}, 0);
		table = new JTable(model);

		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(30, 300, 880, 280);
		p.add(sp);

		add(p);
	}

	private void loadMember() {
		cboMember.removeAllItems();
		List<Member> list = memberController.getAllMember();
		for (Member m : list) {
			cboMember.addItem(m);
		}
	}

	private void loadTrainer() {
		cboTrainer.removeAllItems();
		List<Trainer> list = trainerController.getAllTrainer();
		for (Trainer t : list) {
			cboTrainer.addItem(t);
		}
	}

	private void loadTable() {
		model.setRowCount(0);
		List<Assignment> list = controller.getAllAssignment();
		for (Assignment a : list) {
			model.addRow(new Object[]{
					a.getAssignmentId(),
					a.getMemberName(),
					a.getTrainerName(),
					a.getAssignedDate(),
					a.getStatus()
			});
		}
	}

	private void clearForm() {
		cboMember.setSelectedIndex(0);
		cboTrainer.setSelectedIndex(0);
		txtDate.setText("");
		cboStatus.setSelectedIndex(0);
		table.clearSelection();
	}

	private Assignment getAssignmentFromForm() {
		Assignment a = new Assignment();
		int row = table.getSelectedRow();
		if (row != -1) {
			a.setAssignmentId(Integer.parseInt(model.getValueAt(row, 0).toString()));
		}

		Member m = (Member) cboMember.getSelectedItem();
		Trainer t = (Trainer) cboTrainer.getSelectedItem();

		if (m != null) a.setMemberId(m.getMemberId());
		if (t != null) a.setTrainerId(t.getTrainerId());

		a.setAssignedDate(txtDate.getText().trim());
		a.setStatus(cboStatus.getSelectedItem().toString());

		return a;
	}

	private void fillForm() {
		int row = table.getSelectedRow();
		if (row == -1) return;

		txtDate.setText(model.getValueAt(row, 3).toString());
		cboStatus.setSelectedItem(model.getValueAt(row, 4).toString());

		String member = model.getValueAt(row, 1).toString();
		for (int i = 0; i < cboMember.getItemCount(); i++) {
			if (cboMember.getItemAt(i).getFullName().equals(member)) {
				cboMember.setSelectedIndex(i);
				break;
			}
		}

		String trainer = model.getValueAt(row, 2).toString();
		for (int i = 0; i < cboTrainer.getItemCount(); i++) {
			if (cboTrainer.getItemAt(i).getTrainerName().equals(trainer)) {
				cboTrainer.setSelectedIndex(i);
				break;
			}
		}
	}

	private void addEvents() {
		btnAdd.addActionListener(e -> {
			try {
				String result = controller.addAssignment(getAssignmentFromForm());
				switch (result) {
					case "SUCCESS":
						JOptionPane.showMessageDialog(this, "Assignment Success");
						loadTable();
						clearForm();
						break;
					case "MEMBER_HAS_TRAINER":
						JOptionPane.showMessageDialog(this, "Member already has a trainer.");
						break;
					case "TRAINER_INACTIVE":
						JOptionPane.showMessageDialog(this, "Trainer is inactive.");
						break;
					case "MEMBER_INACTIVE":
						JOptionPane.showMessageDialog(this, "Member is inactive.");
						break;
					default:
						JOptionPane.showMessageDialog(this, "Assignment Failed");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Data");
			}
		});

		btnUpdate.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Select Assignment");
				return;
			}
			try {
				if (controller.updateAssignment(getAssignmentFromForm())) {
					JOptionPane.showMessageDialog(this, "Update Success");
					loadTable();
					clearForm();
				} else {
					JOptionPane.showMessageDialog(this, "Update Failed");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Data");
			}
		});

		btnDelete.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Select Assignment");
				return;
			}
			int id = Integer.parseInt(model.getValueAt(row, 0).toString());
			int confirm = JOptionPane.showConfirmDialog(this, "Delete this assignment?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (controller.deleteAssignment(id)) {
					JOptionPane.showMessageDialog(this, "Delete Success");
					loadTable();
					clearForm();
				} else {
					JOptionPane.showMessageDialog(this, "Delete Failed");
				}
			}
		});

		btnRefresh.addActionListener(e -> {
			clearForm();
			loadTable();
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				fillForm();
			}
		});
	}
}