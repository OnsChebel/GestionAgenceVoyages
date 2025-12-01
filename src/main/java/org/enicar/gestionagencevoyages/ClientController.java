package org.enicar.gestionagencevoyages;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.enicar.gestionagencevoyages.Model.Personnes.Client;
import org.enicar.gestionagencevoyages.Model.Personnes.Adresse;
import org.enicar.gestionagencevoyages.Model.Personnes.Coordonnes;
import org.enicar.gestionagencevoyages.Service.ClientService;

import java.io.IOException;

public class ClientController {

    private final ClientService clientService = new ClientService();

    // Champs FXML (Correspondant aux fx:id)
    @FXML private TextField idField;
    @FXML private TextField prenomField;
    @FXML private TextField nomField;
    @FXML private TextField telField;
    @FXML private TextField emailField;
    @FXML private TextField rueField;
    @FXML private TextField villeField;
    @FXML private TextField codePostalField;
    @FXML private Button enregistrerButton;
    @FXML private Button retourButton;


    public void initialize() {
        idField.setDisable(true);
    }

    @FXML
    private void handleEnregistrerAction() {
        try {
            String prenom = prenomField.getText();
            String nom = nomField.getText();
            String email = emailField.getText();
            int telephone = Integer.parseInt(telField.getText());
            int codePostal = Integer.parseInt(codePostalField.getText());
            String rue = rueField.getText();
            String ville = villeField.getText();
            Coordonnes coord = new Coordonnes(email, telephone);
            Adresse adresse = new Adresse(rue, ville, codePostal);

            Client nouveauClient = new Client(
                    0,
                    nom,
                    prenom,
                    coord,
                    adresse
            );

            clientService.addClient(nouveauClient);
            System.out.println("Nouveau client enregistré avec succès. ID: " + nouveauClient.getId());

        } catch (NumberFormatException e) {
            System.err.println("Erreur de format : Le numéro de téléphone ou le code postal doit être un nombre.");
        } catch (Exception e) {
            System.err.println("Échec de l'enregistrement du client : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetourAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client-list.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setTitle("Gestion des clients");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'un client.");
            e.printStackTrace();

        }
    }
}
