package org.enicar.gestionagencevoyages.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.enicar.gestionagencevoyages.DAO.ReservationDAOImpl;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Reservations.Reservation;
import org.enicar.gestionagencevoyages.Service.ReservationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationListController implements Initializable {

    @FXML private Button ajouterReservationButton;
    @FXML private Button menuButton;
    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, Integer> idColumn;
    @FXML private TableColumn<Reservation, Integer> idClient;
    @FXML private TableColumn<Reservation, Date> dateColumn;
    @FXML private TableColumn<Reservation, String> statutColumn;

    private final ReservationService reservationService = new ReservationService();

    @FXML
    private void handleAjouterReservationAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/ajouter-reservation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ajouterReservationButton.getScene().getWindow();
            stage.setTitle("Ajout de reservation");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface d'ajout de réservation.");
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
                    try {
                        setText(date.jour() + "/" + date.mois() + "/" + date.annee());
                    } catch (Throwable t) {
                        setText(date.toString());
                    }
                }
            }
        });

        addDeleteColumn();
        rafraichirLaListe();
    }

    @FXML
    private void handleVoirVols(ActionEvent event) {
        System.out.println("handleVoirVols called");
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) {
            System.out.println("Veuillez sélectionner une réservation d'abord !");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/vol-list.fxml"));
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
        if (selectedRes == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/accomodation-list.fxml"));
            Parent root = loader.load();
            AccomodationListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());
            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Hébergements de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoirTransports() {
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/transport-list.fxml"));
            Parent root = loader.load();
            TransportListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());
            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Transports de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoirActivites() {
        Reservation selectedRes = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedRes == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/activite-list.fxml"));
            Parent root = loader.load();
            ActiviteListController controller = loader.getController();
            controller.initData(selectedRes.getIdReservation());
            Stage stage = (Stage) reservationTable.getScene().getWindow();
            stage.setTitle("Activités de la réservation " + selectedRes.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMenuAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setTitle("Le Bon Voyage");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface principale");
            e.printStackTrace();
        }
    }

    private void addDeleteColumn() {
        TableColumn<Reservation, Void> colAction = new TableColumn<>("Action");

        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory =
            new Callback<>() {
                @Override
                public TableCell<Reservation, Void> call(TableColumn<Reservation, Void> param) {
                    return new TableCell<>() {
                        private final Button btn = new Button("Supprimer");
                        {
                            btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                            btn.setOnAction(event -> {
                                Reservation reservation = getTableView().getItems().get(getIndex());
                                onDeleteReservation(reservation);
                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            setGraphic(empty ? null : btn);
                        }
                    };
                }
            };

        colAction.setCellFactory(cellFactory);
        reservationTable.getColumns().add(colAction);
    }

    private void onDeleteReservation(Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la réservation");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer la réservation #" + reservation.getIdReservation() + " ?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ReservationDAOImpl reservationDAO = new ReservationDAOImpl();
            reservationDAO.deleteReservation(reservation.getIdReservation());

            reservationTable.getItems().remove(reservation);

            try {
                reservationService.removeFromCache(reservation);
            } catch (Throwable ignored) { }

            reservationTable.setItems(reservationService.getAllReservations());
        }
    }

    private void rafraichirLaListe() {
        try {
            reservationTable.setItems(reservationService.getAllReservations());
        } catch (Throwable t) {
            ReservationDAOImpl dao = new ReservationDAOImpl();
            java.util.List<Reservation> list = dao.getAllReservations();
            reservationTable.setItems(FXCollections.observableArrayList(list));
        }
    }

}