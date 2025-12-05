package org.enicar.gestionagencevoyages;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Services.Accomodation;
import org.enicar.gestionagencevoyages.Service.AccomodationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AccomodationListController implements Initializable {

    @FXML private TableView<Accomodation> accTable;
    @FXML private TableColumn<Accomodation, String> nomColumn;
    @FXML private TableColumn<Accomodation, String> typeColumn;
    @FXML private TableColumn<Accomodation, String> adresseColumn;
    @FXML private TableColumn<Accomodation, Double> prixColumn;
    @FXML private TableColumn<Accomodation, ObservableList<String>> supplementsColumn;
    @FXML private TableColumn<Accomodation, Double> totalColumn;

    private final AccomodationService service = new AccomodationService();
    private int currentReservationId;

    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        rafraichirLaListe();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixBaseProperty().asObject());
        supplementsColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getServicesIncluts()));
        totalColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrixBase() + cellData.getValue().getTarifsSupp()));

        prixColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : String.format("%.2f DT", item));
            }
        });

        totalColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : String.format("%.2f DT", item));
            }
        });

        supplementsColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(ObservableList<String> list, boolean empty) {
                super.updateItem(list, empty);
                if (empty || list == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(list.isEmpty() ? "Aucun" : list.stream().collect(Collectors.joining(", ")));
                }
            }
        });

        addDeleteColumn();
    }

    private void rafraichirLaListe() {
        ObservableList<Accomodation> list = FXCollections.observableArrayList(
                service.getAccomodationsForReservation(currentReservationId)
        );
        accTable.setItems(list);
    }

    private void addDeleteColumn() {
        TableColumn<Accomodation, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Accomodation acc = getTableView().getItems().get(getIndex());
                    onDeleteAccomodation(acc);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        accTable.getColumns().add(colAction);
    }

    private void onDeleteAccomodation(Accomodation acc) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'hébergement");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + acc.getNom() + " ?");
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            service.deleteAccomodation(acc.getId());
            rafraichirLaListe();
        }
    }

    @FXML
    private void handleAjouter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-accomodation.fxml"));
            Parent root = loader.load();
            AccomodationController controller = loader.getController();
            controller.setReservationId(this.currentReservationId);
            Stage stage = (Stage) accTable.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservation-list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) accTable.getScene().getWindow();
            stage.setTitle("Liste des réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
