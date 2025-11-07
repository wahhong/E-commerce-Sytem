package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Rider;

public class RiderDao {

    private Connection conn;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;

    public RiderDao(Connection conn) {
        this.conn = conn;
    }

    public List<Rider> getAllRiders() {
        List<Rider> riders = new ArrayList<>();
        try {
            query = "SELECT * FROM rider";
            ps = this.conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Rider rider = new Rider();
                rider.setRiderID(rs.getInt("riderID"));
                rider.setRiderName(rs.getString("riderName"));
                rider.setRiderPlateNumber(rs.getString("riderPlateNumber"));
                rider.setRiderPhone(rs.getString("riderPhone"));
                riders.add(rider);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving deliveries: " + e.getMessage());
        }
        return riders;
    }

    public Rider getRiderById(int riderID) {
        Rider rider = null;
        try {
            query = "SELECT * FROM rider WHERE riderID = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, riderID);
            rs = ps.executeQuery();

            if (rs.next()) {
                rider = new Rider();
                rider.setRiderID(rs.getInt("riderID"));
                rider.setRiderName(rs.getString("riderName"));
                rider.setRiderPlateNumber(rs.getString("riderPlateNumber"));
                rider.setRiderPhone(rs.getString("riderPhone"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving rider by ID: " + e.getMessage());
        }
        return rider;
    }

}
