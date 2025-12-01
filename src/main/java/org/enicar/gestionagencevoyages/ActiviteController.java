package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Services.Activite;
import org.enicar.gestionagencevoyages.Service.ActiviteService;

import java.io.IOException;

public class ActiviteController {

    @FXML private TextField idField;
    @FXML private TextField prixField;
    @FXML private TextField intituleField;
    @FXML private TextField dateField;
    @FXML private TextField horaireField;
    @FXML private TextField dureeField;

    private final ActiviteService service = new ActiviteService();
    private int reservationId;

    public void setReservationId(int id) {
        this.reservationId = id;
    }

    @FXML
    private void handleEnregistrer() {
        try {
            double prix = Double.parseDouble(prixField.getText().replace(",", "."));
            String intitule = intituleField.getText();
            int horaire = Integer.parseInt(horaireField.getText());
            int duree = Integer.parseInt(dureeField.getText());

            Date dateActivite = parseDate(dateField.getText());

            Activite activite = new Activite(0, prix, intitule, dateActivite, horaire, duree);

            service.addActivite(activite, reservationId);
            System.out.println("Activité ajoutée !");

            handleRetour();

        } catch (NumberFormatException e) {
            System.err.println("Erreur : Vérifiez que le prix, l'horaire et la durée sont des nombres.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Date parseDate(String dateText) {
        try {
            String[] parts = dateText.split("/");
            return new Date(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        } catch (Exception e) {
            System.err.println("Format de date invalide. Utilisation date par défaut.");
            return new Date(1, 1, 2025);
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("activite-list.fxml"));
            Parent root = loader.load();

            ActiviteListController controller = loader.getController();
            controller.initData(this.reservationId);

            Stage stage = (Stage) idField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}