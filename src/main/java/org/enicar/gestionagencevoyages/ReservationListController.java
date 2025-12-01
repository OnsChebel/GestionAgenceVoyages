package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Reservations.Reservation;
import org.enicar.gestionagencevoyages.Service.ReservationService;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

public class ReservationListController implements Initializable{
    @FXML
    private Button ajouterReservationButton;
    @FXML
    private Button menuButton;
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> idColumn;
    @FXML
    private TableColumn<Reservation, Integer> idClient;
    @FXML
    private TableColumn<Reservation, Date> dateColumn;
    @FXML
    private TableColumn<Reservation, String> statutColumn;

    private final ReservationService reservationService = new ReservationService();

    @FXML
    private void handleAjouterReservationAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-reservation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ajouterReservationButton.getScene().getWindow();
            stage.setTitle("Ajout de reservation");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'une réservation.");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        idClient.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateRes"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        dateColumn.setCellFactory(column -> new TableCell<Reservation, Date>() {
            @Override
            protected void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.jour() + "/" + date.mois() + "/" + date.annee());
                }
            }
        });
        reservationTable.setItems(reservationService.getReservations());
    }

    @FXML
    private void handleVoirVols() {
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) {
            System.out.println("Veuillez sélectionner une réservation d'abord !");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vol-list.fxml"));
            Parent root = loader.load();
            VolListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());
            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Vols de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoirAccomodations() {
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) return; // Sécurité

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accomodation-list.fxml"));
            Parent root = loader.load();

            AccomodationListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());

            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Hébergements de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleVoirTransports() {
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transport-list.fxml"));
            Parent root = loader.load();

            TransportListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());

            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Transports de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleVoirActivites() {
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("activite-list.fxml"));
            Parent root = loader.load();

            ActiviteListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());

            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Activités de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleMenuAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setTitle("Le Bon Voyage");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface principale");
            e.printStackTrace();

        }
    }

}
