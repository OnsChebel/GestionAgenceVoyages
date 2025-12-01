package org.enicar.gestionagencevoyages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Reservations.Reservation;
import org.enicar.gestionagencevoyages.Service.ReservationService;

import java.io.IOException;

public class ReservationController {
    public ReservationController() {
    }

    private final ReservationService reservationService = new ReservationService();

    private Reservation reservationCourante = null;

    @FXML
    private TextField idReservationField;

    @FXML
    private TextField dateReservationField;

    @FXML
    private ChoiceBox<String> ResStatut;

    @FXML
    private Button enregistrerButton;

    @FXML
    private TextField clientIdField;

    @FXML
    private Button retourButton;

    @FXML private Button btnAjoutVol;
    @FXML private Button btnAjoutHotel;
    @FXML private Button btnAjoutTransport;
    @FXML private Button btnAjoutActivite;


    private final String[] statuts = {"En Cours", "Confirmée", "Annulée"};

    public void initialize() {
        idReservationField.setDisable(true);
        ResStatut.setItems(FXCollections.observableArrayList(statuts));
        ResStatut.setValue("En Cours");
    }

    @FXML
    private void handleEnregistrerAction() {
        try {
            int clientId = Integer.parseInt(clientIdField.getText());
            Date dateRes = parseDateFromTextField(dateReservationField.getText());
            String statut = ResStatut.getValue();
            if (statut == null) statut = "En Cours";

            Reservation nouvelleRes = new Reservation(0, clientId, dateRes, statut);

            reservationService.addReservation(nouvelleRes);
            System.out.println("Réservation enregistrée avec ID: " + nouvelleRes.getIdReservation());

            this.reservationCourante = nouvelleRes;
            idReservationField.setText(String.valueOf(nouvelleRes.getIdReservation()));
            activerBoutonsServices();
        } catch (NumberFormatException e) {
            System.err.println("Erreur : L'ID Client doit être un nombre entier.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activerBoutonsServices() {
        btnAjoutVol.setDisable(false);
        btnAjoutHotel.setDisable(false);
        btnAjoutTransport.setDisable(false);
        btnAjoutActivite.setDisable(false);
        enregistrerButton.setDisable(true);
        enregistrerButton.setText("Sauvegardé !");

    }

    @FXML
    private void handleAjouterVolAction() {
        if (reservationCourante == null || reservationCourante.getIdReservation() == 0) {
            System.err.println("Erreur: Veuillez d'abord enregistrer la réservation !");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-vol.fxml"));
            Parent root = loader.load();
            VolController volController = loader.getController();
            volController.setReservationId(reservationCourante.getIdReservation());
            Stage stage = (Stage) btnAjoutVol.getScene().getWindow();
            stage.setTitle("Ajouter un Vol pour la réservation " + reservationCourante.getIdReservation());
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAjouterHotelAction() {
        if (reservationCourante == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-accomodation.fxml"));
            Parent root = loader.load();
            AccomodationController controller = loader.getController();
            controller.setReservationId(reservationCourante.getIdReservation());
            Stage stage = (Stage) btnAjoutHotel.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Date parseDateFromTextField(String dateText) {
        try {
            String[] parts = dateText.split("/");
            return new Date(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        } catch (Exception e) {
            System.out.println("Format de date invalide (utiliser JJ/MM/AAAA)");
            return new Date(1, 1, 2025);
        }
    }

    @FXML
    private void handleRetourAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservation-list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setTitle("Gestion des Réservation");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'une réservation.");
            e.printStackTrace();

        }
    }

    @FXML
    private void handleAjouterTransportAction() {
        if (reservationCourante == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-transport.fxml"));
            Parent root = loader.load();

            TransportController controller = loader.getController();
            controller.setReservationId(reservationCourante.getIdReservation());

            Stage stage = (Stage) btnAjoutTransport.getScene().getWindow();
            stage.setTitle("Ajouter Transport");
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleAjouterActiviteAction() {
        if (reservationCourante == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter-activite.fxml"));
            Parent root = loader.load();

            ActiviteController controller = loader.getController();
            controller.setReservationId(reservationCourante.getIdReservation());

            Stage stage = (Stage) btnAjoutActivite.getScene().getWindow();
            stage.setTitle("Ajouter Activité");
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}


