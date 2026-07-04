package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gymsystem.payment.model.Order;

/**
 * DAO để quản lý dữ liệu Order trong database
 * Cung cấp các phương thức CRUD cho Order
 */
public class OrderDAO {

    private Connection conn;

    public OrderDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo đơn hàng mới
     */
    public boolean addOrder(Order order) {
        String sql = "INSERT INTO `order`(order_id, member_id, original_amount, final_amount, order_status, created_at) "
                + "VALUES(?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, order.getOrderId());
            ps.setInt(2, order.getMemberId());
            ps.setDouble(3, order.getOriginalAmount());
            ps.setDouble(4, order.getFinalAmount());
            ps.setString(5, order.getOrderStatus());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật đơn hàng
     */
    public boolean updateOrder(Order order) {
        String sql = "UPDATE `order` SET original_amount = ?, final_amount = ?, order_status = ? WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, order.getOriginalAmount());
            ps.setDouble(2, order.getFinalAmount());
            ps.setString(3, order.getOrderStatus());
            ps.setString(4, order.getOrderId());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy đơn hàng theo orderId
     */
    public Order getOrderById(String orderId) {
        String sql = "SELECT * FROM `order` WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy tất cả đơn hàng của một member
     */
    public List<Order> getOrdersByMemberId(int memberId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` WHERE member_id = ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Lấy tất cả đơn hàng theo status
     */
    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` WHERE order_status = ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Lấy tất cả đơn hàng
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Xóa đơn hàng
     */
    public boolean deleteOrder(String orderId) {
        String sql = "DELETE FROM `order` WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orderId);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra đơn hàng tồn tại
     */
    public boolean exists(String orderId) {
        return getOrderById(orderId) != null;
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    public boolean updateOrderStatus(String orderId, String status) {
        String sql = "UPDATE `order` SET order_status = ? WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, orderId);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Ánh xạ ResultSet sang Order object
     */
    private Order mapResultSetToOrder(ResultSet rs) throws Exception {
        String orderId = rs.getString("order_id");
        double originalAmount = rs.getDouble("original_amount");
        
        Order order = new Order(orderId, originalAmount);
        order.setFinalAmount(rs.getDouble("final_amount"));
        order.setOrderStatus(rs.getString("order_status"));
        
        return order;
    }
}
