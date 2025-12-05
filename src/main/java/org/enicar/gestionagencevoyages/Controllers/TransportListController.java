package org.enicar.gestionagencevoyages.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Services.Transport;
import org.enicar.gestionagencevoyages.Service.TransportService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransportListController implements Initializable {

    @FXML private TableView<Transport> transportTable;
    @FXML private TableColumn<Transport, Integer> idColumn;
    @FXML private TableColumn<Transport, String> typeColumn;
    @FXML private TableColumn<Transport, Double> prixColumn;

    private final TransportService service = new TransportService();
    private int currentReservationId;

    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        rafraichirLaListe();
    }

    private void rafraichirLaListe() {
        transportTable.setItems(service.getTransports(currentReservationId));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixBaseProperty().asObject());

        prixColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : String.format("%.2f DT", item));
            }
        });

        addDeleteColumn();
    }

    private void addDeleteColumn() {
        TableColumn<Transport, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Transport t = getTableView().getItems().get(getIndex());
                    onDeleteTransport(t);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        transportTable.getColumns().add(colAction);
    }

    private void onDeleteTransport(Transport t) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le transport");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce transport ?");
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            service.deleteTransport(t.getId());
            rafraichirLaListe();
        }
    }

    @FXML
    private void handleAjouter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/ajouter-transport.fxml"));
            Parent root = loader.load();

            TransportController controller = loader.getController();
            controller.setReservationId(this.currentReservationId);

            Stage stage = (Stage) transportTable.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/reservation-list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) transportTable.getScene().getWindow();
            stage.setTitle("Liste des réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
