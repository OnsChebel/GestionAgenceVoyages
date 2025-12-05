package org.enicar.gestionagencevoyages.DAO;

import org.enicar.gestionagencevoyages.Model.Services.Vol;
import org.enicar.gestionagencevoyages.Model.Services.Aeroport;
import org.enicar.gestionagencevoyages.util.databaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolDAOImpl implements VolDAO {

    @Override
    public void addVol(Vol vol, int reservationId) {
        String sqlVol = "INSERT INTO Vol (depart_nom, depart_code, arrivee_nom, arrivee_code, taxAeroport, prixBase, reservation_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlEscale = "INSERT INTO Escale (nom_aeroport, code_iata, vol_id) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = databaseManager.getConnection();
            conn.setAutoCommit(false);

            int volId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlVol)) {
                if (vol.getADepart() != null) {
                    pstmt.setString(1, vol.getADepart().nom());
                    pstmt.setString(2, vol.getADepart().codeIATA());
                } else {
                    pstmt.setNull(1, Types.VARCHAR); pstmt.setNull(2, Types.VARCHAR);
                }
                if (vol.getAArrivee() != null) {
                    pstmt.setString(3, vol.getAArrivee().nom());
                    pstmt.setString(4, vol.getAArrivee().codeIATA());
                } else {
                    pstmt.setNull(3, Types.VARCHAR); pstmt.setNull(4, Types.VARCHAR);
                }

                pstmt.setDouble(5, vol.getTaxAeroport());
                pstmt.setDouble(6, vol.getPrixBase());
                pstmt.setInt(7, reservationId);
                pstmt.executeUpdate();

                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) volId = rs.getInt(1);
                }
            }

            if (volId != -1 && vol.getEscales() != null && !vol.getEscales().isEmpty()) {
                try (PreparedStatement pstmtEsc = conn.prepareStatement(sqlEscale)) {
                    for (Aeroport escale : vol.getEscales()) {
                        pstmtEsc.setString(1, escale.nom());
                        pstmtEsc.setString(2, escale.codeIATA());
                        pstmtEsc.setInt(3, volId);
                        pstmtEsc.addBatch();
                    }
                    pstmtEsc.executeBatch();
                }
            }
            conn.commit();
            vol.setId(volId);
            System.out.println("Vol et escales enregistrés !");

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public List<Vol> getAllVols() {
        List<Vol> vols = new ArrayList<>();
        String sql = "SELECT * FROM Vol";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String departNom = rs.getString("depart_nom");
                String departCode = rs.getString("depart_code");
                String arriveeNom = rs.getString("arrivee_nom");
                String arriveeCode = rs.getString("arrivee_code");
                double taxAeroport = rs.getDouble("taxAeroport");
                double prixBase = rs.getDouble("prixBase");

                Aeroport aDepart = new Aeroport(departNom, departCode);
                Aeroport aArrivee = new Aeroport(arriveeNom, arriveeCode);

                Vol v = new Vol();
                v.setId(id);
                v.setADepart(aDepart);
                v.setAArrivee(aArrivee);
                v.setTaxAeroport(taxAeroport);
                v.setPrixBase(prixBase);
                v.setEscales(getEscalesForVol(conn, id));
                vols.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vols;
    }

    @Override
    public List<Vol> getVolsByReservation(int reservationId) {
        List<Vol> vols = new ArrayList<>();
        String sql = "SELECT * FROM Vol WHERE reservation_id = ?";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Vol v = new Vol();
                    v.setId(rs.getInt("id"));
                    v.setPrixBase(rs.getDouble("prixBase"));
                    v.setTaxAeroport(rs.getDouble("taxAeroport"));
                    Aeroport depart = new Aeroport(
                            rs.getString("depart_nom"),
                            rs.getString("depart_code")
                    );
                    v.setADepart(depart);
                    Aeroport arrivee = new Aeroport(
                            rs.getString("arrivee_nom"),
                            rs.getString("arrivee_code")
                    );
                    v.setAArrivee(arrivee);
                    v.setEscales(getEscalesForVol(conn, v.getId()));
                    vols.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERREUR LECTURE VOLS : " + e.getMessage());
            e.printStackTrace();
        }
        return vols;
    }

    private List<Aeroport> getEscalesForVol(Connection conn, int volId) throws SQLException {
        List<Aeroport> escales = new ArrayList<>();
        String sql = "SELECT nom_aeroport, code_iata FROM Escale WHERE vol_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, volId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("nom_aeroport");
                    String code = rs.getString("code_iata");
                    escales.add(new Aeroport(nom, code));
                    System.out.println("Escale trouvée pour vol " + volId + " : " + code);
                }
            }
        }
        return escales;
    }

    @Override
    public void deleteVol(int id) {
        String sql = "DELETE FROM Vol WHERE id = ?";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Vol supprimé. ID=" + id);
            } else {
                System.out.println("Aucun vol trouvé pour ID=" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}