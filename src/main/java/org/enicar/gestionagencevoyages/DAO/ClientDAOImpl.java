package org.enicar.gestionagencevoyages.DAO;


import org.enicar.gestionagencevoyages.Model.Personnes.Client;
import org.enicar.gestionagencevoyages.Model.Personnes.Adresse;
import org.enicar.gestionagencevoyages.Model.Personnes.Coordonnes;
import org.enicar.gestionagencevoyages.util.databaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

public class ClientDAOImpl implements ClientDAO {

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, email, telephone, rue, ville, codePostal FROM Client";

        try (Connection conn = databaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Coordonnes coord = new Coordonnes(
                        rs.getString("email"),
                        rs.getInt("telephone")
                );

                Adresse adresse = new Adresse(
                        rs.getString("rue"),
                        rs.getString("ville"),
                        rs.getInt("codePostal")
                );

                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        coord,
                        adresse
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur de lecture des clients: " + e.getMessage());
        }
        return clients;
    }

    @Override
    public void addClient(Client client) {
        String sql = "INSERT INTO Client (nom, prenom, email, telephone, rue, ville, codePostal) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());

            if (client.getCoord() != null) {
                pstmt.setString(3, client.getCoord().email());
                pstmt.setInt(4, client.getCoord().telephone());
            } else {
                pstmt.setNull(3, Types.VARCHAR);
                pstmt.setNull(4, Types.INTEGER);
            }

            if (client.getAdresse() != null) {
                pstmt.setString(5, client.getAdresse().rue());
                pstmt.setString(6, client.getAdresse().ville());
                pstmt.setInt(7, client.getAdresse().codePostal());
            } else {
                pstmt.setNull(5, Types.VARCHAR);
                pstmt.setNull(6, Types.VARCHAR);
                pstmt.setNull(7, Types.INTEGER);
            }
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        client.setId(newId);
                        System.out.println("Client ajouté avec succès. ID généré : " + newId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de l'ajout du client : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override public Client getClientById(int id) { return null; }
    @Override public void updateClient(Client client) {}
    @Override public void deleteClient(int id) { }
}
