package org.enicar.gestionagencevoyages.Controllers;

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
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
    @FXML private Button reservationsButton;
    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> nomColumn;
    @FXML private TableColumn<Client, String> prenomColumn;
    @FXML private TableColumn<Client, Coordonnes> coordColumn;
    @FXML private TableColumn<Client, Adresse> adresseColumn;
    @FXML private TableColumn<Client, String> hisResvColumn;

    @FXML
    private void handleAjouterClientAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/hello-view.fxml"));
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

        addDeleteColumn();
        
        rafraichirLaListe();
    }

    private void addDeleteColumn() {
        TableColumn<Client, Void> colAction = new TableColumn<>("Action");
        
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = 
            new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
                @Override
                public TableCell<Client, Void> call(TableColumn<Client, Void> param) {
                    return new TableCell<Client, Void>() {
                        private final Button btn = new Button("Supprimer");
                        {
                            btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                            btn.setOnAction(event -> {
                                Client client = getTableView().getItems().get(getIndex());
                                onDeleteClient(client);
                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);
                            }
                        }
                    };
                }
            };
        
        colAction.setCellFactory(cellFactory);
        clientTable.getColumns().add(colAction);
    }

    private void onDeleteClient(Client client) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le client");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + client.getPrenom() + " " + client.getNom() + " ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            clientService.deleteClient(client);
        }
    }

    private void rafraichirLaListe() {
        clientTable.setItems(clientService.getClients());
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

    @FXML
    private void handleReservationsAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/reservation-list.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) reservationsButton.getScene().getWindow();
            stage.setTitle("Gestion des Réservations");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface des réservations.");
            e.printStackTrace();

        }
    }
}