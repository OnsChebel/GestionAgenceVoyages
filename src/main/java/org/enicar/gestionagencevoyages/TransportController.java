package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Services.Transport;
import org.enicar.gestionagencevoyages.Service.TransportService;
import org.enicar.gestionagencevoyages.TransportListController;

import java.io.IOException;

public class TransportController {

    @FXML private TextField idField;
    @FXML private TextField prixField;
    @FXML private TextField typeField;

    private final TransportService service = new TransportService();
    private int reservationId;

    public void setReservationId(int id) {
        this.reservationId = id;
    }

    @FXML
    private void handleEnregistrer() {
        try {
            double prix = Double.parseDouble(prixField.getText().replace(",", "."));
            String type = typeField.getText();

            Transport t = new Transport(0, prix, type);
            service.addTransport(t, reservationId);
            System.out.println("Transport ajouté !");
            handleRetour();

        } catch (NumberFormatException e) {
            System.err.println("Erreur : Le prix doit être un nombre valide.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transport-list.fxml"));
            Parent root = loader.load();

            TransportListController controller = loader.getController();
            controller.initData(this.reservationId);

            Stage stage = (Stage) idField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}