package org.enicar.gestionagencevoyages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.enicar.gestionagencevoyages.DAO.VolDAOImpl;
import org.enicar.gestionagencevoyages.Model.Services.Aeroport;
import org.enicar.gestionagencevoyages.Model.Services.Vol;
import org.enicar.gestionagencevoyages.Service.VolService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VolController implements Initializable {

    @FXML private TableView<Vol> volTable;
    @FXML private TableColumn<Vol, Integer> idColumn;
    @FXML private TableColumn<Vol, Double> prixColumn;
    @FXML private TableColumn<Vol, Aeroport> departColumn;
    @FXML private TableColumn<Vol, Aeroport> arriveeColumn;
    @FXML private TableColumn<Vol, Double> taxeColumn;
    @FXML private TableColumn<Vol, ObservableList<Aeroport>> escalesColumn;
    @FXML private TableColumn<Vol, Double> totalColumn;

    @FXML private Button ajouterVolButton;
    @FXML private Button retourButton; // IMPORTANT

    private final VolService volService = new VolService();
    private int currentReservationId = 0;

    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        rafraichirLaListe();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixBase"));
        departColumn.setCellValueFactory(new PropertyValueFactory<>("ADepart"));
        arriveeColumn.setCellValueFactory(new PropertyValueFactory<>("AArrivee"));
        taxeColumn.setCellValueFactory(new PropertyValueFactory<>("taxAeroport"));
        escalesColumn.setCellValueFactory(new PropertyValueFactory<>("escales"));

        departColumn.setCellFactory(col -> new TableCell<Vol, Aeroport>() {
            @Override
            protected void updateItem(Aeroport a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? null : a.nom() + " (" + a.codeIATA() + ")");
            }
        });

        arriveeColumn.setCellFactory(col -> new TableCell<Vol, Aeroport>() {
            @Override
            protected void updateItem(Aeroport a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? null : a.nom() + " (" + a.codeIATA() + ")");
            }
        });

        totalColumn.setCellFactory(col -> new TableCell<Vol, Double>() {
            @Override
            protected void updateItem(Double d, boolean empty) {
                super.updateItem(d, empty);
                if (empty) { setText(null); return; }
                Vol v = getTableView().getItems().get(getIndex());
                double total = v.getPrixBase() + v.getTaxAeroport();
                setText(String.format("%.2f", total));
            }
        });

        addDeleteColumn();
        rafraichirLaListe();
    }

    @FXML
    private void handleAjouterVol(ActionEvent event) {
        try {
            handleRetour(event); // Retour au menu
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @FXML
    private void handleRetour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("Le Bon Voyage");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDeleteColumn() {
        TableColumn<Vol, Void> colAction = new TableColumn<>("Action");

        Callback<TableColumn<Vol, Void>, TableCell<Vol, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Vol vol = getTableView().getItems().get(getIndex());
                    onDeleteVol(vol);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };

        colAction.setCellFactory(cellFactory);
        volTable.getColumns().add(colAction);
    }

    private void onDeleteVol(Vol vol) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le vol");
        alert.setContentText("Voulez-vous supprimer le vol #" + vol.getId() + " ?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            VolDAOImpl dao = new VolDAOImpl();
            dao.deleteVol(vol.getId());
            rafraichirLaListe();
        }
    }

    private void rafraichirLaListe() {
        try {
            ObservableList<Vol> items;

            if (currentReservationId != 0)
                items = FXCollections.observableArrayList(volService.getVolsForReservation(currentReservationId));
            else
                items = FXCollections.observableArrayList(volService.getAllVols());

            volTable.setItems(items);

        } catch (Throwable t) {
            VolDAOImpl dao = new VolDAOImpl();
            List<Vol> list;

            if (currentReservationId != 0) list = dao.getVolsByReservation(currentReservationId);
            else list = dao.getAllVols();

            volTable.setItems(FXCollections.observableArrayList(list));
        }
    }

    public void setReservationId(int idReservation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setReservationId'");
    }
}
