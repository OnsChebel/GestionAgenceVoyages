package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Services.Activite;
import org.enicar.gestionagencevoyages.Service.ActiviteService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActiviteListController implements Initializable {

    @FXML private TableView<Activite> activiteTable;
    @FXML private TableColumn<Activite, Integer> idColumn;
    @FXML private TableColumn<Activite, String> intituleColumn;
    @FXML private TableColumn<Activite, Date> dateColumn;
    @FXML private TableColumn<Activite, Integer> horaireColumn;
    @FXML private TableColumn<Activite, Integer> dureeColumn;
    @FXML private TableColumn<Activite, Double> prixColumn;

    private final ActiviteService service = new ActiviteService();
    private int currentReservationId;

    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        activiteTable.setItems(service.getActivitesForReservation(reservationId));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Liaison des données (Data Binding)
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        intituleColumn.setCellValueFactory(cellData -> cellData.getValue().intituleProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        horaireColumn.setCellValueFactory(cellData -> cellData.getValue().horaireProperty().asObject());
        dureeColumn.setCellValueFactory(cellData -> cellData.getValue().dureeProperty().asObject());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixBaseProperty().asObject());

        dateColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.jour() + "/" + item.mois() + "/" + item.annee());
                }
            }
        });

        horaireColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item + "h");
            }
        });

        dureeColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item + "h");
            }
        });

        prixColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%.2f DT", item));
            }
        });
    }

    @FXML
    private void handleAjouter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-activite.fxml"));
            Parent root = loader.load();

            ActiviteController controller = loader.getController();
            controller.setReservationId(this.currentReservationId);

            Stage stage = (Stage) activiteTable.getScene().getWindow();
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
            Stage stage = (Stage) activiteTable.getScene().getWindow();
            stage.setTitle("Liste des réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}