package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Transport;
import org.enicar.gestionagencevoyages.util.databaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportDAOImpl implements TransportDAO {

    @Override
    public void addTransport(Transport t, int reservationId) {
        String sql = "INSERT INTO Transport (type, prixBase, reservation_id) VALUES (?, ?, ?)";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.getType());
            pstmt.setDouble(2, t.getPrixBase());
            pstmt.setInt(3, reservationId);

            pstmt.executeUpdate();


            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transport> getTransportsByReservation(int reservationId) {
        List<Transport> list = new ArrayList<>();
        String sql = "SELECT * FROM Transport WHERE reservation_id = ?";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transport t = new Transport(
                            rs.getInt("id"),
                            rs.getDouble("prixBase"),
                            rs.getString("type")
                    );
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}