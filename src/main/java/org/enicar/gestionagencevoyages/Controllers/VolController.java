package org.enicar.gestionagencevoyages.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Services.Aeroport;
import org.enicar.gestionagencevoyages.Model.Services.Vol;
import org.enicar.gestionagencevoyages.Service.VolService;

import java.io.IOException;

public class VolController {

    @FXML private TextField idField;
    @FXML private TextField prixField;
    @FXML private TextField departField;
    @FXML private TextField arriveeField;
    @FXML private TextField escalesField;
    @FXML private TextField taxeField;
    @FXML private TextField totalField;

    private final VolService volService = new VolService();
    private int reservationId;

    public void setReservationId(int id) {
        this.reservationId = id;
    }

    @FXML
    public void initialize() {

        if(prixField != null) prixField.textProperty().addListener(o -> calculerTotal());
        if(taxeField != null) taxeField.textProperty().addListener(o -> calculerTotal());
    }

    private void calculerTotal() {
        try {
            String prixTxt = prixField.getText().replace(",", ".");
            String taxeTxt = taxeField.getText().replace(",", ".");

            double prix = prixTxt.isEmpty() ? 0 : Double.parseDouble(prixTxt);
            double taxe = taxeTxt.isEmpty() ? 0 : Double.parseDouble(taxeTxt);

            if(totalField != null) totalField.setText(String.format("%.2f", prix + taxe));
        } catch (NumberFormatException e) {
            if(totalField != null) totalField.setText("...");
        }
    }

    @FXML
    private void handleEnregistrer() {
        try {
            double prix = Double.parseDouble(prixField.getText().replace(",", "."));
            double taxe = Double.parseDouble(taxeField.getText().replace(",", "."));

            Aeroport depart = parseAeroport(departField.getText());
            Aeroport arrivee = parseAeroport(arriveeField.getText());

            Vol nouveauVol = new Vol(0, prix, depart, arrivee, taxe);

            String escalesText = escalesField.getText();
            if (escalesText != null && !escalesText.isBlank()) {
                String[] codes = escalesText.split(",");
                for (String code : codes) {
                    String cleanCode = code.trim().toUpperCase();
                    if (!cleanCode.isEmpty()) {
                        nouveauVol.ajouterEscale(new Aeroport("Escale " + cleanCode, cleanCode));
                    }
                }
            }

            // Sauvegarde
            volService.addVol(nouveauVol, this.reservationId);
            System.out.println("Vol enregistré !");

            // Retour à la liste
            handleRetour();

        } catch (NumberFormatException e) {
            System.err.println("Erreur de format numérique");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/vol-list.fxml"));
            Parent root = loader.load();

            VolListController controller = loader.getController();
            controller.initData(this.reservationId);

            Stage stage = (Stage) prixField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Aeroport parseAeroport(String text) {
        if (text == null || text.isBlank()) return new Aeroport("Inconnu", "XXX");
        if (text.contains(",")) {
            String[] parts = text.split(",");
            return new Aeroport(parts[0].trim(), parts[1].trim().toUpperCase());
        }
        return new Aeroport(text.trim(), text.trim().toUpperCase());
    }
}