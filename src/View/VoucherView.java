package View;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.PromotionController;
import Controller.VoucherController;
import Model.Promotion;
import Model.Voucher;

public class VoucherView extends JFrame {

	private VoucherController controller;
	private PromotionController promotionController;

	private JComboBox<Promotion> cboPromotion;
	private JTextField txtCode, txtDiscount, txtMinAmount, txtQuantity, txtExpireDate;
	private JComboBox<String> cboStatus;

	private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

	private JTable table;
	private DefaultTableModel model;

	public VoucherView() {
		controller = new VoucherController();
		promotionController = new PromotionController();
		initComponents();
		loadPromotion();
		loadTable();
		addEvents();
		setVisible(true);
	}

	private void initComponents() {

		setTitle("Voucher Management");
		setSize(1000, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p = new JPanel(null);

		JLabel lb = new JLabel("VOUCHER MANAGEMENT");
		lb.setFont(new Font("Arial", Font.BOLD, 22));
		lb.setBounds(320, 20, 350, 30);
		p.add(lb);

		p.add(new JLabel("Promotion")).setBounds(40, 80, 100, 25);
		cboPromotion = new JComboBox<>();
		cboPromotion.setBounds(150, 80, 220, 25);
		p.add(cboPromotion);

		p.add(new JLabel("Code")).setBounds(40, 120, 100, 25);
		txtCode = new JTextField();
		txtCode.setBounds(150, 120, 220, 25);
		p.add(txtCode);

		p.add(new JLabel("Discount")).setBounds(40, 160, 100, 25);
		txtDiscount = new JTextField();
		txtDiscount.setBounds(150, 160, 220, 25);
		p.add(txtDiscount);

		p.add(new JLabel("Min Amount")).setBounds(500, 80, 100, 25);
		txtMinAmount = new JTextField();
		txtMinAmount.setBounds(620, 80, 220, 25);
		p.add(txtMinAmount);

		p.add(new JLabel("Quantity")).setBounds(500, 120, 100, 25);
		txtQuantity = new JTextField();
		txtQuantity.setBounds(620, 120, 220, 25);
		p.add(txtQuantity);

		p.add(new JLabel("Expire Date")).setBounds(500, 160, 100, 25);
		txtExpireDate = new JTextField();
		txtExpireDate.setBounds(620, 160, 220, 25);
		p.add(txtExpireDate);

		p.add(new JLabel("Status")).setBounds(40, 200, 100, 25);
		cboStatus = new JComboBox<>();
		cboStatus.addItem("Active");
		cboStatus.addItem("Inactive");
		cboStatus.setBounds(150, 200, 220, 25);
		p.add(cboStatus);

		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnRefresh = new JButton("Refresh");

		btnAdd.setBounds(120, 270, 100, 35);
		btnUpdate.setBounds(240, 270, 100, 35);
		btnDelete.setBounds(360, 270, 100, 35);
		btnRefresh.setBounds(480, 270, 100, 35);

		p.add(btnAdd);
		p.add(btnUpdate);
		p.add(btnDelete);
		p.add(btnRefresh);

		model = new DefaultTableModel(new String[] { "ID", "Promotion", "Code", "Discount", "Min Amount", "Quantity",
				"Expire Date", "Status" }, 0);

		table = new JTable(model);

		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(30, 330, 920, 250);
		p.add(sp);

		add(p);
	}

	private void loadPromotion() {
		cboPromotion.removeAllItems();
		List<Promotion> list = promotionController.getAllPromotion();
		for (Promotion p : list)
			cboPromotion.addItem(p);
	}

	private void loadTable() {
		model.setRowCount(0);
		List<Voucher> list = controller.getAllVoucher();

		for (Voucher v : list) {

			String promotionName = "";

			for (int i = 0; i < cboPromotion.getItemCount(); i++) {
				Promotion p = cboPromotion.getItemAt(i);
				if (p.getPromotionId() == v.getPromotionId()) {
					promotionName = p.getPromotionName();
					break;
				}
			}

			model.addRow(new Object[] { v.getVoucherId(), promotionName, v.getVoucherCode(), v.getDiscountPercent(),
					v.getMinimumAmount(), v.getQuantity(), v.getExpireDate(), v.getStatus() });
		}
	}

	private void clearForm() {
		txtCode.setText("");
		txtDiscount.setText("");
		txtMinAmount.setText("");
		txtQuantity.setText("");
		txtExpireDate.setText("");
		cboPromotion.setSelectedIndex(0);
		cboStatus.setSelectedIndex(0);
		table.clearSelection();
	}

	private Voucher getVoucherFromForm() {

		Voucher v = new Voucher();

		int row = table.getSelectedRow();
		if (row != -1)
			v.setVoucherId(Integer.parseInt(model.getValueAt(row, 0).toString()));

		Promotion p = (Promotion) cboPromotion.getSelectedItem();

		if (p != null)
			v.setPromotionId(p.getPromotionId());

		v.setVoucherCode(txtCode.getText().trim());

		if (!txtDiscount.getText().trim().isEmpty())
			v.setDiscountPercent(Integer.parseInt(txtDiscount.getText()));

		if (!txtMinAmount.getText().trim().isEmpty())
			v.setMinimumAmount(Double.parseDouble(txtMinAmount.getText()));

		if (!txtQuantity.getText().trim().isEmpty())
			v.setQuantity(Integer.parseInt(txtQuantity.getText()));

		v.setExpireDate(txtExpireDate.getText().trim());

		v.setStatus(cboStatus.getSelectedItem().toString());

		return v;
	}

	private void fillForm() {

		int row = table.getSelectedRow();

		if (row == -1)
			return;

		txtCode.setText(model.getValueAt(row, 2).toString());
		txtDiscount.setText(model.getValueAt(row, 3).toString());
		txtMinAmount.setText(model.getValueAt(row, 4).toString());
		txtQuantity.setText(model.getValueAt(row, 5).toString());
		txtExpireDate.setText(model.getValueAt(row, 6).toString());

		cboStatus.setSelectedItem(model.getValueAt(row, 7).toString());

		String promotion = model.getValueAt(row, 1).toString();

		for (int i = 0; i < cboPromotion.getItemCount(); i++) {
			if (cboPromotion.getItemAt(i).getPromotionName().equals(promotion)) {
				cboPromotion.setSelectedIndex(i);
				break;
			}
		}
	}

	private void addEvents() {

		btnAdd.addActionListener(e -> {
			try {
				if (controller.addVoucher(getVoucherFromForm())) {
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
				JOptionPane.showMessageDialog(this, "Select Voucher");
				return;
			}

			try {
				if (controller.updateVoucher(getVoucherFromForm())) {
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
				JOptionPane.showMessageDialog(this, "Select Voucher");
				return;
			}

			int id = Integer.parseInt(model.getValueAt(row, 0).toString());

			if (JOptionPane.showConfirmDialog(this, "Delete this voucher?", "Confirm",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				if (controller.deleteVoucher(id)) {
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