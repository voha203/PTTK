package View;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.PromotionController;
import Model.Promotion;

public class PromotionView extends JFrame {

	private PromotionController controller;
	private JTextField txtName, txtDescription, txtStartDate, txtEndDate;
	private JComboBox<String> cboStatus;
	private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;
	private JTable table;
	private DefaultTableModel model;

	public PromotionView() {
		controller = new PromotionController();
		initComponents();
		loadTable();
		addEvents();
		setVisible(true);
	}

	private void initComponents() {

		setTitle("Promotion Management");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p = new JPanel(null);

		JLabel lbTitle = new JLabel("PROMOTION MANAGEMENT");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lbTitle.setBounds(250, 20, 400, 30);
		p.add(lbTitle);

		p.add(new JLabel("Name")).setBounds(40, 80, 100, 25);
		txtName = new JTextField();
		txtName.setBounds(150, 80, 220, 25);
		p.add(txtName);

		p.add(new JLabel("Description")).setBounds(40, 120, 100, 25);
		txtDescription = new JTextField();
		txtDescription.setBounds(150, 120, 220, 25);
		p.add(txtDescription);

		p.add(new JLabel("Start Date")).setBounds(450, 80, 100, 25);
		txtStartDate = new JTextField();
		txtStartDate.setBounds(560, 80, 220, 25);
		p.add(txtStartDate);

		p.add(new JLabel("End Date")).setBounds(450, 120, 100, 25);
		txtEndDate = new JTextField();
		txtEndDate.setBounds(560, 120, 220, 25);
		p.add(txtEndDate);

		p.add(new JLabel("Status")).setBounds(40, 160, 100, 25);
		cboStatus = new JComboBox<>();
		cboStatus.addItem("Active");
		cboStatus.addItem("Inactive");
		cboStatus.setBounds(150, 160, 220, 25);
		p.add(cboStatus);

		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnRefresh = new JButton("Refresh");

		btnAdd.setBounds(120, 230, 100, 35);
		btnUpdate.setBounds(240, 230, 100, 35);
		btnDelete.setBounds(360, 230, 100, 35);
		btnRefresh.setBounds(480, 230, 100, 35);

		p.add(btnAdd);
		p.add(btnUpdate);
		p.add(btnDelete);
		p.add(btnRefresh);

		model = new DefaultTableModel(new String[] { "ID", "Name", "Description", "Start Date", "End Date", "Status" },
				0);

		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(30, 300, 830, 230);
		p.add(sp);

		add(p);
	}

	private void loadTable() {
		model.setRowCount(0);
		List<Promotion> list = controller.getAllPromotion();
		for (Promotion p : list) {
			model.addRow(new Object[] { p.getPromotionId(), p.getPromotionName(), p.getDescription(), p.getStartDate(),
					p.getEndDate(), p.getStatus() });
		}
	}

	private void clearForm() {
		txtName.setText("");
		txtDescription.setText("");
		txtStartDate.setText("");
		txtEndDate.setText("");
		cboStatus.setSelectedIndex(0);
		table.clearSelection();
	}

	private Promotion getPromotionFromForm() {
		Promotion p = new Promotion();

		int row = table.getSelectedRow();
		if (row != -1)
			p.setPromotionId(Integer.parseInt(model.getValueAt(row, 0).toString()));

		p.setPromotionName(txtName.getText().trim());
		p.setDescription(txtDescription.getText().trim());
		p.setStartDate(txtStartDate.getText().trim());
		p.setEndDate(txtEndDate.getText().trim());
		p.setStatus(cboStatus.getSelectedItem().toString());

		return p;
	}

	private void fillForm() {
		int row = table.getSelectedRow();
		if (row == -1)
			return;

		txtName.setText(model.getValueAt(row, 1).toString());
		txtDescription.setText(model.getValueAt(row, 2).toString());
		txtStartDate.setText(model.getValueAt(row, 3).toString());
		txtEndDate.setText(model.getValueAt(row, 4).toString());
		cboStatus.setSelectedItem(model.getValueAt(row, 5).toString());
	}

	private void addEvents() {

		btnAdd.addActionListener(e -> {
			try {
				if (controller.addPromotion(getPromotionFromForm())) {
					JOptionPane.showMessageDialog(this, "Add Success");
					loadTable();
					clearForm();
				} else {
					JOptionPane.showMessageDialog(this, "Add Failed");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Data");
			}
		});

		btnUpdate.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Select Promotion");
				return;
			}

			try {
				if (controller.updatePromotion(getPromotionFromForm())) {
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
				JOptionPane.showMessageDialog(this, "Select Promotion");
				return;
			}

			int id = Integer.parseInt(model.getValueAt(row, 0).toString());

			if (JOptionPane.showConfirmDialog(this, "Delete this promotion?", "Confirm",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				if (controller.deletePromotion(id)) {
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
			if (!e.getValueIsAdjusting())
				fillForm();
		});
	}

}