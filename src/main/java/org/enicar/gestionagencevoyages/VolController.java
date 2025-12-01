package org.enicar.gestionagencevoyages;

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
        prixField.textProperty().addListener((observable, oldValue, newValue) -> {
            calculerTotal();
        });

        taxeField.textProperty().addListener((observable, oldValue, newValue) -> {
            calculerTotal();
        });
    }

    private void calculerTotal() {
        try {
            String prixTxt = prixField.getText().isEmpty() ? "0" : prixField.getText();
            String taxeTxt = taxeField.getText().isEmpty() ? "0" : taxeField.getText();

            double prix = Double.parseDouble(prixTxt.replace(",", "."));
            double taxe = Double.parseDouble(taxeTxt.replace(",", "."));

            double total = prix + taxe;
            totalField.setText(String.format("%.2f", total));

        } catch (NumberFormatException e) {
            totalField.setText("...");
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
                        nouveauVol.ajouterEscale(new Aeroport(cleanCode, cleanCode));
                    }
                }
            }

            volService.addVol(nouveauVol, this.reservationId);

            idField.setText(String.valueOf(nouveauVol.getId()));
            System.out.println("Vol ajouté avec ID: " + nouveauVol.getId());
            retourALaListe();
        } catch (NumberFormatException e) {
            System.err.println("Erreur: Vérifiez que les prix sont bien des chiffres.");
        } catch (Exception e) {
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

    public void retourALaListe() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vol-list.fxml"));
            Parent root = loader.load();
            VolListController controller = loader.getController();
            controller.initData(this.reservationId);
            Stage stage = (Stage) idField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}