package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Reservations.Reservation;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.util.databaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";

        try (Connection conn = databaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Date dateRes = parseDate(rs.getString("dateRes"));

                Reservation res = new Reservation(
                        rs.getInt("idReservation"),
                        rs.getInt("clientId"),
                        dateRes,
                        rs.getString("statut")
                );
                reservations.add(res);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture réservations: " + e.getMessage());
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void addReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservation (dateRes, statut, clientId) VALUES (?, ?, ?)";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, formatDate(reservation.getDateRes()));
            pstmt.setString(2, reservation.getStatut());
            pstmt.setInt(3, reservation.getClientId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        reservation.setIdReservation(rs.getInt(1));
                        System.out.println("Réservation ajoutée avec ID: " + reservation.getIdReservation());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur ajout réservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
    }

    @Override
    public void deleteReservation(int idReservation) {
        String sql = "DELETE FROM Reservation WHERE idReservation = ?";

        try (Connection conn = databaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idReservation);
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Réservation supprimée. ID=" + idReservation);
            } else {
                System.out.println("Aucune réservation trouvée pour ID=" + idReservation);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la suppression de la réservation : " + e.getMessage());
            e.printStackTrace();
        }
    }
// ...existing code...
    private Date parseDate(String dateStr) {
        if (dateStr == null || !dateStr.contains("/")) return new Date(1, 1, 2000);
        try {
            String[] parts = dateStr.split("/");
            return new Date(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        } catch (Exception e) {
            System.err.println("Erreur parsing date: " + dateStr);
            return new Date(1, 1, 2000);
        }
    }

    private String formatDate(Date date) {
        if (date == null) return "01/01/2000";
        return date.jour() + "/" + date.mois() + "/" + date.annee();
    }
}