package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Activite;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.util.databaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteDAOImpl implements ActiviteDAO {

    @Override
    public void addActivite(Activite a, int reservationId) {
        String sql = "INSERT INTO Activite (intitule, dateActivite, horaire, duree, prixBase, reservation_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, a.getIntitule());
            pstmt.setString(2, formatDate(a.getDate()));
            pstmt.setInt(3, a.getHoraire());
            pstmt.setInt(4, a.getDuree());
            pstmt.setDouble(5, a.getPrixBase());
            pstmt.setInt(6, reservationId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Activite> getActivitesByReservation(int reservationId) {
        List<Activite> list = new ArrayList<>();
        String sql = "SELECT * FROM Activite WHERE reservation_id = ?";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Activite a = new Activite(
                            rs.getInt("id"),
                            rs.getDouble("prixBase"),
                            rs.getString("intitule"),
                            parseDate(rs.getString("dateActivite")),
                            rs.getInt("horaire"),
                            rs.getInt("duree")
                    );
                    list.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private String formatDate(Date date) {
        if (date == null) return "01/01/2000";
        return date.jour() + "/" + date.mois() + "/" + date.annee();
    }

    private Date parseDate(String dateStr) {
        try {
            if (dateStr == null || !dateStr.contains("/")) return new Date(1,1,2025);
            String[] parts = dateStr.split("/");
            return new Date(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } catch (Exception e) { return new Date(1,1,2025); }
    }
}
