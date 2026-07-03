package View;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.BookingController;
import Controller.RatingController;
import Model.Booking;
import Model.Rating;

public class RatingView extends JFrame {

	private RatingController controller = new RatingController();
	private BookingController bookingController = new BookingController();

	private JComboBox<Booking> cboBooking;
	private JComboBox<Integer> cboStar;
	private JTextField txtDate;
	private JTextArea txtComment;
	private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;
	private JTable table;
	private DefaultTableModel model;

	public RatingView() {
		initComponents();
		loadBooking();
		loadTable();
		addEvents();
		setVisible(true);
	}

	private void initComponents() {
		setTitle("Rating Management");
		setSize(950, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p = new JPanel(null);

		JLabel lb = new JLabel("RATING MANAGEMENT");
		lb.setFont(new Font("Arial", Font.BOLD, 22));
		lb.setBounds(280, 20, 350, 30);
		p.add(lb);

		p.add(new JLabel("Booking")).setBounds(40, 80, 100, 25);
		cboBooking = new JComboBox<>();
		cboBooking.setBounds(150, 80, 220, 25);
		p.add(cboBooking);

		p.add(new JLabel("Star")).setBounds(40, 120, 100, 25);
		cboStar = new JComboBox<>();
		for (int i = 1; i <= 5; i++) {
			cboStar.addItem(i);
		}
		cboStar.setBounds(150, 120, 220, 25);
		p.add(cboStar);

		p.add(new JLabel("Rating Date")).setBounds(450, 80, 100, 25);
		txtDate = new JTextField();
		txtDate.setBounds(560, 80, 220, 25);
		p.add(txtDate);

		p.add(new JLabel("Comment")).setBounds(450, 120, 100, 25);
		txtComment = new JTextArea();
		JScrollPane spComment = new JScrollPane(txtComment);
		spComment.setBounds(560, 120, 220, 70);
		p.add(spComment);

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

		model = new DefaultTableModel(new String[]{
				"ID", "Booking", "Member", "Trainer", "Star", "Comment", "Date"
		}, 0);
		table = new JTable(model);

		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(30, 300, 880, 280);
		p.add(sp);

		add(p);
	}

	private void loadBooking() {
		cboBooking.removeAllItems();
		List<Booking> list = bookingController.getAllBooking();
		for (Booking b : list) {
			cboBooking.addItem(b);
		}
	}

	private void loadTable() {
		model.setRowCount(0);
		List<Rating> list = controller.getAllRating();
		for (Rating r : list) {
			model.addRow(new Object[]{
					r.getRatingId(),
					r.getBookingId(),
					r.getMemberName(),
					r.getTrainerName(),
					r.getStar(),
					r.getComment(),
					r.getRatingDate()
			});
		}
	}

	private void clearForm() {
		if (cboBooking.getItemCount() > 0) {
			cboBooking.setSelectedIndex(0);
		}
		cboStar.setSelectedIndex(0);
		txtComment.setText("");
		txtDate.setText("");
		table.clearSelection();
	}

	private Rating getRatingFromForm() {
		Rating r = new Rating();
		int row = table.getSelectedRow();
		if (row != -1) {
			r.setRatingId(Integer.parseInt(model.getValueAt(row, 0).toString()));
		}

		Booking b = (Booking) cboBooking.getSelectedItem();
		if (b != null) {
			r.setBookingId(b.getBookingId());
			r.setMemberId(b.getMemberId());
			r.setTrainerId(b.getTrainerId());
		}

		r.setStar((Integer) cboStar.getSelectedItem());
		r.setComment(txtComment.getText().trim());
		r.setRatingDate(txtDate.getText().trim());

		return r;
	}

	private void fillForm() {
		int row = table.getSelectedRow();
		if (row == -1) return;

		int bookingId = Integer.parseInt(model.getValueAt(row, 1).toString());
		for (int i = 0; i < cboBooking.getItemCount(); i++) {
			if (cboBooking.getItemAt(i).getBookingId() == bookingId) {
				cboBooking.setSelectedIndex(i);
				break;
			}
		}

		cboStar.setSelectedItem(Integer.parseInt(model.getValueAt(row, 4).toString()));
		txtComment.setText(model.getValueAt(row, 5).toString());
		txtDate.setText(model.getValueAt(row, 6).toString());
	}

	private void addEvents() {
		btnAdd.addActionListener(e -> {
			try {
				String result = controller.addRating(getRatingFromForm());
				switch (result) {
					case "SUCCESS":
						JOptionPane.showMessageDialog(this, "Rating Success");
						loadTable();
						clearForm();
						break;
					case "BOOKING_NOT_COMPLETED":
						JOptionPane.showMessageDialog(this, "Booking is not completed.");
						break;
					case "ALREADY_RATED":
						JOptionPane.showMessageDialog(this, "This booking has already been rated.");
						break;
					case "INVALID_STAR":
						JOptionPane.showMessageDialog(this, "Star must be from 1 to 5.");
						break;
					default:
						JOptionPane.showMessageDialog(this, "Rating Failed");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Data");
			}
		});

		btnUpdate.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Select Rating");
				return;
			}
			try {
				if (controller.updateRating(getRatingFromForm())) {
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
				JOptionPane.showMessageDialog(this, "Select Rating");
				return;
			}
			int id = Integer.parseInt(model.getValueAt(row, 0).toString());
			int confirm = JOptionPane.showConfirmDialog(this, "Delete this rating?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (controller.deleteRating(id)) {
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