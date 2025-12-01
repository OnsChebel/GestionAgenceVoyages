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

import org.enicar.gestionagencevoyages.Model.Personnes.Adresse;
import org.enicar.gestionagencevoyages.Model.Personnes.Client;
import org.enicar.gestionagencevoyages.Model.Personnes.Coordonnes;

import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;
import org.enicar.gestionagencevoyages.Service.ClientService;

public class ClientListController implements Initializable{

    private final ClientService clientService = new ClientService();

    @FXML private Button ajouterClientButton;
    @FXML private Button menuButton;
    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> nomColumn;
    @FXML private TableColumn<Client, String> prenomColumn;
    @FXML private TableColumn<Client, Coordonnes> coordColumn; // Type Object
    @FXML private TableColumn<Client, Adresse> adresseColumn;   // Type Object
    @FXML private TableColumn<Client, String> hisResvColumn;

    @FXML
    private void handleAjouterClientAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ajouterClientButton.getScene().getWindow();
            stage.setTitle("Ajout de client");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface de l'ajout d'un client.");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        coordColumn.setCellValueFactory(new PropertyValueFactory<>("coord"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        coordColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Coordonnes item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.email() + " | " + item.telephone());
            }
        });

        adresseColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Adresse item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.rue() + ", " + item.ville());
            }
        });

        rafraichirLaListe();
    }
    private void rafraichirLaListe() {
        clientTable.setItems(clientService.getClients());
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
