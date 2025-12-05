package org.enicar.gestionagencevoyages.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Services.Activite;
import org.enicar.gestionagencevoyages.Service.ActiviteService;

import java.io.IOException;

public class ActiviteController {

    @FXML private TextField intituleField;
    @FXML private TextField prixField;
    @FXML private TextField jourField;
    @FXML private TextField moisField;
    @FXML private TextField anneeField;
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
            String intitule = intituleField.getText();
            double prix = Double.parseDouble(prixField.getText().replace(",", "."));
            int jour = Integer.parseInt(jourField.getText());
            int mois = Integer.parseInt(moisField.getText());
            int annee = Integer.parseInt(anneeField.getText());
            int horaire = Integer.parseInt(horaireField.getText());
            int duree = Integer.parseInt(dureeField.getText());

            Date date = new Date(jour, mois, annee);
            Activite a = new Activite(0, prix, intitule, date, horaire, duree);

            service.addActivite(a, reservationId);
            System.out.println("Activité ajoutée !");
            handleRetour();

        } catch (NumberFormatException e) {
            System.err.println("Erreur : Veuillez entrer des nombres valides.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/activite-list.fxml"));
            Parent root = loader.load();

            ActiviteListController controller = loader.getController();
            controller.initData(this.reservationId);

            Stage stage = (Stage) intituleField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private TableColumn<Activite, Double> totalColumn;

}
