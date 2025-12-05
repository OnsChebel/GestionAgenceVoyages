package org.enicar.gestionagencevoyages.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Services.Accomodation;
import org.enicar.gestionagencevoyages.Service.AccomodationService;

import java.io.IOException;

public class AccomodationController {

    @FXML private TextField idField;
    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private ChoiceBox<String> typeChoiceBox;
    @FXML private TextField prixField;
    @FXML private ListView<String> listSuppAcc;
    @FXML private TextField tarifsSuppField;
    @FXML private TextField totalField;

    private final AccomodationService service = new AccomodationService();
    private int reservationId;

    public void setReservationId(int id) { this.reservationId = id; }

    @FXML
    public void initialize() {
        typeChoiceBox.getItems().addAll("Hôtel", "Auberge", "Maison d'hôte", "Appartement");
        typeChoiceBox.setValue("Hôtel");

        listSuppAcc.getItems().addAll("Chambre individuelle", "Pension complete", "Spa", "Baby sitter");
        listSuppAcc.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        prixField.textProperty().addListener(o -> calculerTotal());
        listSuppAcc.getSelectionModel().selectedItemProperty().addListener(o -> calculerTotal());
    }

    private void calculerTotal() {
        try {
            double prixBase = prixField.getText().isEmpty() ? 0 : Double.parseDouble(prixField.getText());
            double supp = 0;
            for (String item : listSuppAcc.getSelectionModel().getSelectedItems()) {
                switch (item) {
                    case "Chambre individuelle" -> supp += 100;
                    case "Pension complete" -> supp += 190;
                    case "Spa" -> supp += 225;
                    case "Baby sitter" -> supp += 300;
                }
            }
            tarifsSuppField.setText(String.valueOf(supp));
            totalField.setText(String.format("%.2f", prixBase + supp));
        } catch (NumberFormatException e) {
            totalField.setText("...");
        }
    }

    @FXML
    private void handleEnregistrer() {
        try {
            double prix = Double.parseDouble(prixField.getText());
            String nom = nomField.getText();
            String type = typeChoiceBox.getValue();
            String adresse = adresseField.getText();

            Accomodation acc = new Accomodation(0, prix, nom, type, adresse);
            acc.getServicesIncluts().addAll(listSuppAcc.getSelectionModel().getSelectedItems());
            acc.recalculerTarifsSupp();

            service.addAccomodation(acc, reservationId);
            retourALaListe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retourALaListe() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/accomodation-list.fxml"));
            Parent root = loader.load();
            AccomodationListController controller = loader.getController();
            controller.initData(this.reservationId);
            Stage stage = (Stage) idField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
