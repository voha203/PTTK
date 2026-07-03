package View;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.BookingController;
import Controller.MemberController;
import Controller.TrainerController;
import Controller.VoucherController;
import Controller.BookingVoucherController;
import Model.Booking;
import Model.Member;
import Model.Trainer;
import Model.BookingVoucher;
import Model.Voucher; 

public class BookingView extends JFrame {

	private BookingController controller = new BookingController();
	private MemberController memberController = new MemberController();
	private TrainerController trainerController = new TrainerController();
	private VoucherController voucherController = new VoucherController();
	private BookingVoucherController bookingVoucherController = new BookingVoucherController(); // Khai báo controller
																								// mới

	private JComboBox<Member> cboMember;
	private JComboBox<Trainer> cboTrainer;
	private JTextField txtDate, txtTime;
	private JTextField txtPrice, txtFinalPrice, txtVoucher;
	private JComboBox<String> cboStatus;
	private JButton btnAdd, btnUpdate, btnDelete, btnRefresh, btnApplyVoucher;
	private JTable table;
	private DefaultTableModel model;

	public BookingView() {
		initComponents();
		loadMember();
		loadTrainer();
		loadTable();
		addEvents();
		setVisible(true);
	}

	private void initComponents() {
		setTitle("Booking Management");
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p = new JPanel(null);

		JLabel lb = new JLabel("BOOKING MANAGEMENT");
		lb.setFont(new Font("Arial", Font.BOLD, 22));
		lb.setBounds(270, 20, 350, 30);
		p.add(lb);

		p.add(new JLabel("Member")).setBounds(40, 80, 100, 25);
		cboMember = new JComboBox<>();
		cboMember.setBounds(150, 80, 220, 25);
		p.add(cboMember);

		p.add(new JLabel("Trainer")).setBounds(40, 120, 100, 25);
		cboTrainer = new JComboBox<>();
		cboTrainer.setBounds(150, 120, 220, 25);
		p.add(cboTrainer);

		p.add(new JLabel("Status")).setBounds(40, 160, 100, 25);
		cboStatus = new JComboBox<>();
		cboStatus.addItem("Pending");
		cboStatus.addItem("Completed");
		cboStatus.addItem("Cancelled");
		cboStatus.setBounds(150, 160, 220, 25);
		p.add(cboStatus);

		p.add(new JLabel("Date")).setBounds(450, 80, 100, 25);
		txtDate = new JTextField();
		txtDate.setBounds(560, 80, 220, 25);
		p.add(txtDate);

		p.add(new JLabel("Time")).setBounds(450, 120, 100, 25);
		txtTime = new JTextField();
		txtTime.setBounds(560, 120, 220, 25);
		p.add(txtTime);

		p.add(new JLabel("Price")).setBounds(450, 160, 100, 25);
		txtPrice = new JTextField("0");
		txtPrice.setBounds(560, 160, 220, 25);
		p.add(txtPrice);

		p.add(new JLabel("Voucher")).setBounds(450, 200, 100, 25);
		txtVoucher = new JTextField();
		txtVoucher.setBounds(560, 200, 140, 25);
		p.add(txtVoucher);

		btnApplyVoucher = new JButton("Apply");
		btnApplyVoucher.setBounds(710, 200, 70, 25);
		p.add(btnApplyVoucher);

		p.add(new JLabel("Final Price")).setBounds(450, 240, 100, 25);
		txtFinalPrice = new JTextField("0");
		txtFinalPrice.setEditable(false);
		txtFinalPrice.setBounds(560, 240, 220, 25);
		p.add(txtFinalPrice);

		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnRefresh = new JButton("Refresh");

		btnAdd.setBounds(120, 290, 100, 35);
		btnUpdate.setBounds(240, 290, 100, 35);
		btnDelete.setBounds(360, 290, 100, 35);
		btnRefresh.setBounds(480, 290, 100, 35);

		p.add(btnAdd);
		p.add(btnUpdate);
		p.add(btnDelete);
		p.add(btnRefresh);

		model = new DefaultTableModel(
				new String[] { "ID", "Member", "Trainer", "Date", "Time", "Price", "Final Price", "Status" }, 0);
		table = new JTable(model);

		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(30, 350, 880, 230);
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
		List<Booking> list = controller.getAllBooking();
		for (Booking b : list) {
			model.addRow(new Object[] { b.getBookingId(), b.getMemberName(), b.getTrainerName(), b.getBookingDate(),
					b.getBookingTime(), b.getPrice(), b.getFinalPrice(), b.getStatus() });
		}
	}

	private void clearForm() {
		if (cboMember.getItemCount() > 0)
			cboMember.setSelectedIndex(0);
		if (cboTrainer.getItemCount() > 0)
			cboTrainer.setSelectedIndex(0);
		txtDate.setText("");
		txtTime.setText("");
		txtPrice.setText("0");
		txtVoucher.setText("");
		txtFinalPrice.setText("0");
		cboStatus.setSelectedIndex(0);
		table.clearSelection();
	}

	private Booking getBookingFromForm() {
		Booking b = new Booking();
		int row = table.getSelectedRow();
		if (row != -1) {
			b.setBookingId(Integer.parseInt(model.getValueAt(row, 0).toString()));
		}

		Member m = (Member) cboMember.getSelectedItem();
		Trainer t = (Trainer) cboTrainer.getSelectedItem();

		if (m != null)
			b.setMemberId(m.getMemberId());
		if (t != null)
			b.setTrainerId(t.getTrainerId());

		b.setBookingDate(txtDate.getText().trim());
		b.setBookingTime(txtTime.getText().trim());

		try {
			b.setPrice(Double.parseDouble(txtPrice.getText().trim()));
			b.setFinalPrice(Double.parseDouble(txtFinalPrice.getText().trim()));
		} catch (Exception e) {
			b.setPrice(0);
			b.setFinalPrice(0);
		}

		b.setStatus(cboStatus.getSelectedItem().toString());
		return b;
	}

	private void fillForm() {
		int row = table.getSelectedRow();
		if (row == -1)
			return;

		txtDate.setText(model.getValueAt(row, 3).toString());
		txtTime.setText(model.getValueAt(row, 4).toString());
		txtPrice.setText(model.getValueAt(row, 5).toString());
		txtFinalPrice.setText(model.getValueAt(row, 6).toString());
		cboStatus.setSelectedItem(model.getValueAt(row, 7).toString());

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
		btnApplyVoucher.addActionListener(e -> {
			try {
				double price = Double.parseDouble(txtPrice.getText().trim());
				String code = txtVoucher.getText().trim();
				String result = voucherController.applyVoucher(code, price);

				switch (result) {
				case "SUCCESS":
					txtFinalPrice.setText(String.valueOf(voucherController.calculateFinalPrice(code, price)));
					JOptionPane.showMessageDialog(this, "Voucher Applied");
					break;
				case "NOT_FOUND":
					JOptionPane.showMessageDialog(this, "Voucher not found");
					txtFinalPrice.setText(txtPrice.getText());
					break;
				case "INACTIVE":
					JOptionPane.showMessageDialog(this, "Voucher inactive");
					txtFinalPrice.setText(txtPrice.getText());
					break;
				case "EXPIRED":
					JOptionPane.showMessageDialog(this, "Voucher expired");
					txtFinalPrice.setText(txtPrice.getText());
					break;
				case "OUT_OF_QUANTITY":
					JOptionPane.showMessageDialog(this, "Voucher out of quantity");
					txtFinalPrice.setText(txtPrice.getText());
					break;
				case "MIN_AMOUNT":
					JOptionPane.showMessageDialog(this, "Minimum amount not reached");
					txtFinalPrice.setText(txtPrice.getText());
					break;
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Price");
			}
		});

		btnAdd.addActionListener(e -> {
			try {
				String result = controller.addBooking(getBookingFromForm());
				switch (result) {
				case "SUCCESS":
					if (!txtVoucher.getText().trim().isEmpty()) {
						Voucher voucher = voucherController.getVoucherByCode(txtVoucher.getText().trim());
						if (voucher != null) {
							BookingVoucher bv = new BookingVoucher();
							bv.setBookingId(controller.getLastBookingId());
							bv.setVoucherId(voucher.getVoucherId());

							double discount = Double.parseDouble(txtPrice.getText().trim())
									- Double.parseDouble(txtFinalPrice.getText().trim());
							bv.setDiscountAmount(discount);

							bookingVoucherController.addBookingVoucher(bv);
							voucherController.increaseUsedCount(voucher.getVoucherId());
						}
					}
					JOptionPane.showMessageDialog(this, "Booking Success");
					loadTable();
					clearForm();
					break;
				case "TRAINER_BUSY":
					JOptionPane.showMessageDialog(this, "Trainer already has a booking at this time.");
					break;
				case "PACKAGE_EXPIRED":
					JOptionPane.showMessageDialog(this, "Member package has expired.");
					break;
				default:
					JOptionPane.showMessageDialog(this, "Booking Failed");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Data");
			}
		});

		btnUpdate.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Select Booking");
				return;
			}
			try {
				if (controller.updateBooking(getBookingFromForm())) {
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
				JOptionPane.showMessageDialog(this, "Select Booking");
				return;
			}
			int id = Integer.parseInt(model.getValueAt(row, 0).toString());
			if (JOptionPane.showConfirmDialog(this, "Delete this booking?", "Confirm",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				bookingVoucherController.deleteByBookingId(id);

				if (controller.deleteBooking(id)) {
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