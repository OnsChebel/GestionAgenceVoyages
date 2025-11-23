package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservationListController {
    @FXML
    private Button ajouterReservationButton;

    @FXML
    private void handleAjouterReservationAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-reservation.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) ajouterReservationButton.getScene().getWindow();
            stage.setTitle("Ajout de reservation");
            stage.getScene().setRoot(root);
            // stage.sizeToScene(); // Optionnel : ajuster la taille à la nouvelle scène
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'une réservation.");
            e.printStackTrace();

        }
    }

}
