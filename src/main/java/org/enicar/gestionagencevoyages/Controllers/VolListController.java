package org.enicar.gestionagencevoyages.Controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Services.Aeroport;
import org.enicar.gestionagencevoyages.Model.Services.Vol;
import org.enicar.gestionagencevoyages.Service.VolService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class VolListController implements Initializable {

    @FXML private TableView<Vol> volTable;
    @FXML private TableColumn<Vol, Integer> idColumn;
    @FXML private TableColumn<Vol, Double> prixColumn;
    @FXML private TableColumn<Vol, Aeroport> departColumn;
    @FXML private TableColumn<Vol, Aeroport> arriveeColumn;
    @FXML private TableColumn<Vol, Double> taxeColumn;
    @FXML private TableColumn<Vol, ObservableList<Aeroport>> escalesColumn;
    @FXML private TableColumn<Vol, Double> totalColumn;

    @FXML private Button ajouterVolButton;
    @FXML private Button retourButton;

    private final VolService volService = new VolService();
    private int currentReservationId = 0;

    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        rafraichirLaListe();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Liaison des données avec Lambdas (Plus sûr)
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        prixColumn.setCellValueFactory(data -> data.getValue().prixBaseProperty().asObject());
        taxeColumn.setCellValueFactory(data -> data.getValue().taxAeroportProperty().asObject());
        departColumn.setCellValueFactory(data -> data.getValue().aDepartProperty());
        arriveeColumn.setCellValueFactory(data -> data.getValue().aArriveeProperty());

        escalesColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEscales()));

        // Calcul du total
        totalColumn.setCellValueFactory(data -> {
            Vol v = data.getValue();
            return new SimpleDoubleProperty(v.getPrixBase() + v.getTaxAeroport()).asObject();
        });

        // Cell Factories (Mise en forme)
        departColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Aeroport a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? "" : a.nom() + " (" + a.codeIATA() + ")");
            }
        });

        arriveeColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Aeroport a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? "" : a.nom() + " (" + a.codeIATA() + ")");
            }
        });

        escalesColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(ObservableList<Aeroport> list, boolean empty) {
                super.updateItem(list, empty);

                // CAS 1 : C'est une ligne vide (bas du tableau, pas de données)
                // On nettoie la cellule pour qu'elle soit blanche
                if (empty || list == null) {
                    setText(null);
                    setGraphic(null);
                }
                // CAS 2 : C'est une vraie ligne avec un Vol
                else {
                    // Sous-cas : Liste vide = Vol Direct
                    if (list.isEmpty()) {
                        setText("Direct");
                    }
                    // Sous-cas : Il y a des escales
                    else {
                        String text = list.stream()
                                .map(Aeroport::codeIATA)
                                .collect(Collectors.joining(", "));
                        setText(text);
                    }
                }
            }
        });

        totalColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                setText(empty || total == null ? "" : String.format("%.2f DT", total));
            }
        });

        addDeleteColumn();
    }

    private void addDeleteColumn() {
        TableColumn<Vol, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Vol v = getTableView().getItems().get(getIndex());
                    onDeleteVol(v);
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        volTable.getColumns().add(colAction);
    }

    private void onDeleteVol(Vol vol) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer le vol #" + vol.getId() + " ?", ButtonType.OK, ButtonType.CANCEL);
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            // CORRECTION : On passe par le service pour la suppression + MAJ liste
            volService.deleteVol(vol);
            rafraichirLaListe();
        }
    }

    private void rafraichirLaListe() {
        if (currentReservationId != 0) {
            volTable.setItems(volService.getVolsForReservation(currentReservationId));
        }
    }

    @FXML
    private void handleAjouterVol(ActionEvent event) {
        try {
            // CORRECTION : Ouvre la vue d'AJOUT (vol-add.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/ajouter-vol.fxml"));
            Parent root = loader.load();

            VolController controller = loader.getController();
            controller.setReservationId(this.currentReservationId);

            Stage stage = (Stage) ajouterVolButton.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/reservation-list.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("Liste des Réservations");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}