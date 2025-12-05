package org.enicar.gestionagencevoyages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;


import java.io.IOException;

public class MainController {
    @FXML
    private Button closeButton;

    @FXML
    private Button reservationsButton;

    @FXML
    private Button voyagesButton;

    @FXML
    private Button clientsButton;

    public void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleReservationsAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservation-list.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) reservationsButton.getScene().getWindow();
            stage.setTitle("Gestion des Réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface des réservations.");
            e.printStackTrace();

        }
    }

    @FXML
    private void handleVoyagesAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("service-list.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) voyagesButton.getScene().getWindow();
            stage.setTitle("Gestion des vols");
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface des vols.");
            e.printStackTrace();

        }

    }

    @FXML
    private void handleClientsAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client-list.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) clientsButton.getScene().getWindow();
            stage.setTitle("Gestion des clients");
            stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface des clients.");
            e.printStackTrace();

        }
    }

    @FXML
    public void onLogout(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("connexion.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            Scene currentScene = stage.getScene();
            double width = currentScene.getWidth();
            double height = currentScene.getHeight();
            
            Scene newScene = new Scene(loginRoot, width, height);
            stage.setScene(newScene);
            stage.setTitle("Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}