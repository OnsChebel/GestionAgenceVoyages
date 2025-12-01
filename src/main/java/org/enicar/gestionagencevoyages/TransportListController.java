package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        transportTable.setItems(service.getTransports(reservationId));
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
    }

    @FXML
    private void handleAjouter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-transport.fxml"));
            Parent root = loader.load();

            TransportController controller = loader.getController();
            controller.setReservationId(this.currentReservationId);

            Stage stage = (Stage) transportTable.getScene().getWindow();
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
            Stage stage = (Stage) transportTable.getScene().getWindow();
            stage.setTitle("Liste des réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}