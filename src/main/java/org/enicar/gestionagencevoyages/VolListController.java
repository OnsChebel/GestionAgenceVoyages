package org.enicar.gestionagencevoyages;

import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.enicar.gestionagencevoyages.DAO.VolDAOImpl;
import org.enicar.gestionagencevoyages.Model.Services.Aeroport;
import org.enicar.gestionagencevoyages.Model.Services.Vol;
import org.enicar.gestionagencevoyages.Service.VolService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

        // Colonnes simples
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixBase"));
        taxeColumn.setCellValueFactory(cellData -> cellData.getValue().taxAeroportProperty().asObject());

        // Colonnes aéroports
        departColumn.setCellValueFactory(cellData -> cellData.getValue().aDepartProperty());
        departColumn.setCellFactory(col -> new TableCell<Vol, Aeroport>() {
            @Override
            protected void updateItem(Aeroport a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? "" : a.nom() + " (" + a.codeIATA() + ")");
            }
        });

        arriveeColumn.setCellValueFactory(cellData -> cellData.getValue().aArriveeProperty());
        arriveeColumn.setCellFactory(col -> new TableCell<Vol, Aeroport>() {
            @Override
            protected void updateItem(Aeroport a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? "" : a.nom() + " (" + a.codeIATA() + ")");
            }
        });

        // Escales
        escalesColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getEscales())
        );        escalesColumn.setCellFactory(col -> new TableCell<Vol, ObservableList<Aeroport>>() {
            @Override
            protected void updateItem(ObservableList<Aeroport> escales, boolean empty) {
                super.updateItem(escales, empty);
                if (empty || escales == null || escales.isEmpty()) {
                    setText("");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Aeroport a : escales) {
                        sb.append(a.nom()).append(" (").append(a.codeIATA()).append("), ");
                    }
                    sb.setLength(sb.length() - 2); // retire la dernière virgule
                    setText(sb.toString());
                }
            }
        });

        // Total
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());
        totalColumn.setCellFactory(col -> new TableCell<Vol, Double>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                setText(empty || total == null ? "" : String.format("%.2f", total));
            }
        });

        // Colonne Action (Supprimer)
        addDeleteColumn();

        // Rafraichir la liste
        rafraichirLaListe();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Voulez-vous supprimer le vol #" + vol.getId() + " ?",
                ButtonType.OK, ButtonType.CANCEL);

        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le vol");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            new VolDAOImpl().deleteVol(vol.getId());
            rafraichirLaListe();
        }
    }

    private void rafraichirLaListe() {
        try {
            List<Vol> list = (currentReservationId != 0)
                    ? volService.getVolsForReservation(currentReservationId)
                    : volService.getAllVols();

            volTable.setItems(FXCollections.observableArrayList(list));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAjouterVol(ActionEvent event) {
        handleRetour(event);
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
}
