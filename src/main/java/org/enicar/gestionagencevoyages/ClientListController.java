package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientListController {
    @FXML
    private Button ajouterClientButton;

    @FXML
    private void handleAjouterClientAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) ajouterClientButton.getScene().getWindow();
            stage.setTitle("Ajout de client");
            stage.getScene().setRoot(root);
            // stage.sizeToScene(); // Optionnel : ajuster la taille à la nouvelle scène
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'un client.");
            e.printStackTrace();

        }
    }
}
