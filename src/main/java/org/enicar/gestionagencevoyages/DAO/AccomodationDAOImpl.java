package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Accomodation;
import org.enicar.gestionagencevoyages.util.databaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccomodationDAOImpl implements AccomodationDAO {

    public void addAccomodation(Accomodation acc, int reservationId) {
        String sqlAcc = "INSERT INTO Accomodation (nom, type, adresse, prixBase, tarifsSupp, reservation_id) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlSupp = "INSERT INTO Supplement (nom_service, accomodation_id) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = databaseManager.getConnection();
            conn.setAutoCommit(false);
            int accId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlAcc)) {
                pstmt.setString(1, acc.getNom());
                pstmt.setString(2, acc.getType());
                pstmt.setString(3, acc.getAdresse());
                pstmt.setDouble(4, acc.getPrixBase());
                pstmt.setDouble(5, acc.getTarifsSupp());
                pstmt.setInt(6, reservationId);
                pstmt.executeUpdate();
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) accId = rs.getInt(1);
                }
            }
            if (accId != -1 && acc.getServicesIncluts() != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlSupp)) {
                    for (String service : acc.getServicesIncluts()) {
                        pstmt.setString(1, service);
                        pstmt.setInt(2, accId);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }
            conn.commit();
            acc.setId(accId);
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { }
        }
    }

    public List<Accomodation> getAccomodationsByReservation(int reservationId) {
        List<Accomodation> list = new ArrayList<>();
        String sql = "SELECT * FROM Accomodation WHERE reservation_id = ?";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Accomodation acc = new Accomodation();
                    acc.setId(rs.getInt("id"));
                    acc.setNom(rs.getString("nom"));
                    acc.setType(rs.getString("type"));
                    acc.setAdresse(rs.getString("adresse"));
                    acc.setPrixBase(rs.getDouble("prixBase"));
                    acc.setTarifsSupp(rs.getDouble("tarifsSupp"));
                    acc.setServicesIncluts(getServicesForAcc(conn, acc.getId()));
                    list.add(acc);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private List<String> getServicesForAcc(Connection conn, int accId) throws SQLException {
        List<String> services = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT nom_service FROM Supplement WHERE accomodation_id = ?")) {
            pstmt.setInt(1, accId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) services.add(rs.getString("nom_service"));
            }
        }
        return services;
    }

    @Override
    public void deleteAccomodation(int accomodationId) {
        String sqlDeleteSupplements = "DELETE FROM Supplement WHERE accomodation_id = ?";
        String sqlDeleteAccomodation = "DELETE FROM Accomodation WHERE id = ?";
        Connection conn = null;
        try {
            conn = databaseManager.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteSupplements)) {
                pstmt.setInt(1, accomodationId);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteAccomodation)) {
                pstmt.setInt(1, accomodationId);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { }
        }
    }
}
