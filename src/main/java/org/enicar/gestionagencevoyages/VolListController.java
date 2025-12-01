package org.enicar.gestionagencevoyages;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private final VolService volService = new VolService();
    private int currentReservationId;


    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        volTable.setItems(volService.getVolsForReservation(reservationId));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixBaseProperty().asObject());
        taxeColumn.setCellValueFactory(cellData -> cellData.getValue().taxAeroportProperty().asObject());
        departColumn.setCellValueFactory(cellData -> cellData.getValue().aDepartProperty());
        arriveeColumn.setCellValueFactory(cellData -> cellData.getValue().aArriveeProperty());
        escalesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEscales()));

        totalColumn.setCellValueFactory(cellData -> {
            Vol v = cellData.getValue();
            double total = v.getPrixBase() + v.getTaxAeroport();
            return new SimpleObjectProperty<>(total);
        });

        departColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Aeroport item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? "" : item.nom() + " (" + item.codeIATA() + ")");
            }
        });

        arriveeColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Aeroport item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? "" : item.nom() + " (" + item.codeIATA() + ")");
            }
        });

        escalesColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(ObservableList<Aeroport> list, boolean empty) {
                super.updateItem(list, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    if (list == null || list.isEmpty()) {
                        setText("Direct");
                    }
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
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f DT", item));
                }
            }
        });
    }

    @FXML
    private void handleAjouterVol() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-vol.fxml"));
            Parent root = loader.load();
            VolController controller = loader.getController();
            controller.setReservationId(this.currentReservationId);
            Stage stage = (Stage) volTable.getScene().getWindow();
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
            Stage stage = (Stage) volTable.getScene().getWindow();
            stage.setTitle("Liste des réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}