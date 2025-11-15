package org.enicar.gestionagencevoyages;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;

public class TransportController {

    @FXML
    private ChoiceBox<String> transportType;

    // Liste des statuts possibles (selon la classe scellée StatutReservation)
    private final String[] types = {"Aucun","Voiture","Bus"};

    // Méthode appelée après le chargement du FXML (étape d'initialisation)
    public void initialize() {
        // 2. Initialiser la ChoiceBox avec les options
        transportType.setItems(FXCollections.observableArrayList(types));

        // Sélectionner un statut par défaut
        transportType.setValue("Aucun");
        // OU
        // ResStatut.getSelectionModel().selectFirst();
    }
}
