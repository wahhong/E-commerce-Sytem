package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Delivery;

public class DeliveryDao {

    private Connection conn;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;

    public DeliveryDao(Connection conn) {
        this.conn = conn;
    }

    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        try {
            query = "SELECT * FROM delivery";
            ps = this.conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryID(rs.getInt("deliveryID"));
                delivery.setOrderID(rs.getBigDecimal("orderID").toBigInteger());
                delivery.setUserName(rs.getString("userName"));
                delivery.setUserPhone(rs.getString("userPhone"));
                delivery.setOrderAddress(rs.getString("orderAddress"));
                delivery.setRiderID(rs.getInt("riderID"));
                delivery.setDeliveryStatus(rs.getString("deliveryStatus"));
                delivery.setOrderShippingDate(rs.getString("orderShippingDate"));
                delivery.setOrderCompleteDate(rs.getString("orderCompleteDate"));
                deliveries.add(delivery);
            }
            System.out.println("Deliveries fetched: " + deliveries.size());
        } catch (SQLException e) {
            System.err.println("Error retrieving deliveries: " + e.getMessage());
        }
        return deliveries;
    }

    public void addDelivery(Delivery delivery) throws SQLException {
        query = "INSERT INTO delivery (orderID, userName, userPhone, orderAddress, deliveryStatus) VALUES (?, ?, ?, ?, ?)";
        ps = this.conn.prepareStatement(query);
        try {
            ps.setObject(1, delivery.getOrderID());
            ps.setString(2, delivery.getUserName());
            ps.setString(3, delivery.getUserPhone());
            ps.setString(4, delivery.getOrderAddress());
            ps.setString(5, delivery.getDeliveryStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding delivery: " + e.getMessage());
        }
    }

    public void updateDeliveryApi(Delivery delivery) throws SQLException {
        query = "UPDATE delivery SET orderAddress = ?, deliveryStatus = ?, orderShippingDate = ?, orderCompleteDate = ? WHERE orderID = ?";
        ps = this.conn.prepareStatement(query);
        ps.setString(1, delivery.getOrderAddress());
        ps.setString(2, delivery.getDeliveryStatus());
        if (delivery.getOrderShippingDate().isEmpty()) {
            ps.setNull(3, java.sql.Types.VARCHAR);
        } else {
            ps.setString(3, delivery.getOrderShippingDate());
        }

        if (delivery.getOrderCompleteDate().isEmpty()) {
            ps.setNull(4, java.sql.Types.VARCHAR);
        } else {
            ps.setString(4, delivery.getOrderCompleteDate());
        }
        ps.setObject(5, delivery.getOrderID());
        ps.executeUpdate();
    }

    public boolean updateDelivery(Delivery delivery) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("UPDATE delivery SET riderID = ?, deliveryStatus = ?");

        // Dynamically append fields to update
        if (delivery.getOrderShippingDate() != null && !delivery.getOrderShippingDate().isEmpty()) {
            queryBuilder.append(", orderShippingDate = ?");
        }
        if (delivery.getOrderCompleteDate() != null && !delivery.getOrderCompleteDate().isEmpty()) {
            queryBuilder.append(", orderCompleteDate = ?");
        }

        queryBuilder.append(" WHERE deliveryID = ?");

        ps = this.conn.prepareStatement(queryBuilder.toString());

        if (delivery.getRiderID() == 0) {
            ps.setNull(1, java.sql.Types.INTEGER);
        } else {
            ps.setInt(1, delivery.getRiderID());
        }

        ps.setString(2, delivery.getDeliveryStatus());

        int paramIndex = 3;

        if (delivery.getOrderShippingDate() != null && !delivery.getOrderShippingDate().isEmpty()) {
            ps.setString(paramIndex, delivery.getOrderShippingDate());
            paramIndex++;
        }

        if (delivery.getOrderCompleteDate() != null && !delivery.getOrderCompleteDate().isEmpty()) {
            ps.setString(paramIndex, delivery.getOrderCompleteDate());
            paramIndex++;
        }

        ps.setInt(paramIndex, delivery.getDeliveryID()); // Set deliveryID at the last position

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    }

}
