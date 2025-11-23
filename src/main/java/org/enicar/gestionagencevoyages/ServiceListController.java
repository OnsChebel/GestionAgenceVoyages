package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceListController {
    @FXML
    private Button ajouterServiceButton;

    @FXML
    private void handleAjouterServiceAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-vol.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) ajouterServiceButton.getScene().getWindow();
            stage.setTitle("Ajout de service");
            stage.getScene().setRoot(root);
            // stage.sizeToScene(); // Optionnel : ajuster la taille à la nouvelle scène
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'un service.");
            e.printStackTrace();

        }
    }
}
