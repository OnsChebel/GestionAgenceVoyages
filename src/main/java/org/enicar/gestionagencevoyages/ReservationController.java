package org.enicar.gestionagencevoyages;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;

public class ReservationController {
    public ReservationController() {
        // Un constructeur public sans arguments est requis
        // si vous définissez un constructeur.
    }

    // 1. Déclaration de l'élément graphique FXML
    @FXML
    private ChoiceBox<String> ResStatut;

    // Liste des statuts possibles (selon la classe scellée StatutReservation)
    private final String[] statuts = {"En Cours", "Confirmée", "Annulée"};

    // Méthode appelée après le chargement du FXML (étape d'initialisation)
    public void initialize() {
        // 2. Initialiser la ChoiceBox avec les options
        ResStatut.setItems(FXCollections.observableArrayList(statuts));

        // Sélectionner un statut par défaut
        ResStatut.setValue("En Cours");
        // OU
        // ResStatut.getSelectionModel().selectFirst();
    }

    // ... autres champs FXML (boutons, champs de texte) ...
}


