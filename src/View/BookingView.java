package View;

import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.BookingController;
import Model.Booking;

public class BookingView extends JFrame {
	private BookingController controller = new BookingController();
	private JTextField txtMemberId, txtTrainerId, txtBookingDate, txtBookingTime;
	private JComboBox<String> cboStatus;
	private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;
	private JTable table;
	private DefaultTableModel model;

	public BookingView() {
		setTitle("Booking PT Management");
		setSize(900,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel(null);

		JLabel lblTitle = new JLabel("BOOKING PT MANAGEMENT");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitle.setBounds(250, 20, 350, 30);
		panel.add(lblTitle);

		panel.add(createLabel("Member ID:", 40, 80));
		panel.add(txtMemberId = createField(170, 80));
		panel.add(createLabel("Trainer ID:", 40, 120));
		panel.add(txtTrainerId = createField(170, 120));
		panel.add(createLabel("Status:", 40, 160));
		panel.add(cboStatus = new JComboBox<>(new String[] { "Pending", "Confirmed", "Cancelled" }));
		cboStatus.setBounds(170, 160, 200, 25);

		panel.add(createLabel("Booking Date:", 450, 80));
		panel.add(txtBookingDate = createField(580, 80));
		panel.add(createLabel("Booking Time:", 450, 120));
		panel.add(txtBookingTime = createField(580, 120));

		panel.add(btnAdd = createBtn("Add", 100, 240));
		panel.add(btnUpdate = createBtn("Update", 230, 240));
		panel.add(btnDelete = createBtn("Delete", 360, 240));
		panel.add(btnRefresh = createBtn("Refresh", 490, 240));

		model = new DefaultTableModel(
				new String[] { "ID", "Member ID", "Trainer ID", "Booking Date", "Booking Time", "Status" }, 0);
		JScrollPane sp = new JScrollPane(table = new JTable(model));
		sp.setBounds(30, 310, 820, 220);
		panel.add(sp);

		add(panel);
		loadTable();
		event();
		setVisible(true);
	}

	private JLabel createLabel(String text, int x, int y) {
		JLabel l = new JLabel(text);
		l.setBounds(x, y, 120, 25);
		return l;
	}

	private JTextField createField(int x, int y) {
		JTextField t = new JTextField();
		t.setBounds(x, y, 200, 25);
		return t;
	}

	private JButton createBtn(String t, int x, int y) {
		JButton b = new JButton(t);
		b.setBounds(x, y, 100, 35);
		return b;
	}

	private void loadTable() {
		model.setRowCount(0);
		for (Booking b : controller.getAllBooking())
			model.addRow(new Object[] { b.getBookingId(), b.getMemberId(), b.getTrainerId(), b.getBookingDate(),
					b.getBookingTime(), b.getStatus() });
	}

	private void clearForm() {
		txtMemberId.setText("");
		txtTrainerId.setText("");
		txtBookingDate.setText("");
		txtBookingTime.setText("");
		cboStatus.setSelectedIndex(0);
	}

	private Booking getBooking() {
		Booking b = new Booking();
		int row = table.getSelectedRow();
		if (row != -1)
			b.setBookingId(Integer.parseInt(model.getValueAt(row, 0).toString()));
		b.setMemberId(Integer.parseInt(txtMemberId.getText()));
		b.setTrainerId(Integer.parseInt(txtTrainerId.getText()));
		b.setBookingDate(txtBookingDate.getText());
		b.setBookingTime(txtBookingTime.getText());
		b.setStatus(cboStatus.getSelectedItem().toString());
		return b;
	}

	private void event() {
		btnAdd.addActionListener(e -> {
			if (controller.addBooking(getBooking())) {
				JOptionPane.showMessageDialog(this, "Add Success");
				loadTable();
				clearForm();
			}
		});
		btnUpdate.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Select a booking!");
				return;
			}
			if (controller.updateBooking(getBooking())) {
				JOptionPane.showMessageDialog(this, "Update Success");
				loadTable();
				clearForm();
			}
		});
		btnDelete.addActionListener(e -> {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Select a booking!");
				return;
			}
			if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", 0) == 0)
				if (controller.deleteBooking(Integer.parseInt(model.getValueAt(r, 0).toString()))) {
					JOptionPane.showMessageDialog(this, "Delete Success");
					loadTable();
					clearForm();
				}
		});
		btnRefresh.addActionListener(e -> {
			clearForm();
			loadTable();
		});
		table.getSelectionModel().addListSelectionListener(e -> {
			int r = table.getSelectedRow();
			if (!e.getValueIsAdjusting() && r != -1) {
				txtMemberId.setText(model.getValueAt(r, 1).toString());
				txtTrainerId.setText(model.getValueAt(r, 2).toString());
				txtBookingDate.setText(model.getValueAt(r, 3).toString());
				txtBookingTime.setText(model.getValueAt(r, 4).toString());
				cboStatus.setSelectedItem(model.getValueAt(r, 5).toString());
			}
		});
	}
}