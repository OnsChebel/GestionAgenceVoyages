package org.enicar.gestionagencevoyages.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.enicar.gestionagencevoyages.Model.Personnes.Date;
import org.enicar.gestionagencevoyages.Model.Services.Activite;
import org.enicar.gestionagencevoyages.Service.ActiviteService;

import java.util.Optional;

public class ActiviteListController {

    @FXML private TableView<Activite> activiteTable;
    @FXML private TableColumn<Activite, String> intituleColumn;
    @FXML private TableColumn<Activite, Date> dateColumn;
    @FXML private TableColumn<Activite, Integer> horaireColumn;
    @FXML private TableColumn<Activite, Integer> dureeColumn;
    @FXML private TableColumn<Activite, Double> prixColumn;
    @FXML private TableColumn<Activite, Double> totalColumn;

    private final ActiviteService service = new ActiviteService();
    private int currentReservationId;

    private final ObservableList<Activite> activites = FXCollections.observableArrayList();

    public void initData(int reservationId) {
        this.currentReservationId = reservationId;
        rafraichirLaListe();
    }

    @FXML
    private void initialize() {
        intituleColumn.setCellValueFactory(cellData -> 
            new ReadOnlyObjectWrapper<>(cellData.getValue().getIntitule())
        );

        horaireColumn.setCellValueFactory(cellData -> 
            new ReadOnlyObjectWrapper<>(cellData.getValue().getHoraire())
        );

        dureeColumn.setCellValueFactory(cellData -> 
            new ReadOnlyObjectWrapper<>(cellData.getValue().getDuree())
        );

        prixColumn.setCellValueFactory(cellData -> 
            new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixBase())
        );


        prixColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double prix, boolean empty) {
                super.updateItem(prix, empty);
                setText(empty || prix == null ? null : String.format("%.2f DT", prix));
            }
        });

        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        dateColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? null : date.jour() + "/" + date.mois() + "/" + date.annee());
            }
        });

        totalColumn.setCellValueFactory(cellData ->
            new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixBase() * cellData.getValue().getDuree())
        );
        totalColumn.setCellFactory(col -> new TableCell<Activite, Double>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                setText(empty || total == null ? null : String.format("%.2f DT", total));
            }
        });

        addDeleteColumn();
        activiteTable.setItems(activites);
    }


    private void addDeleteColumn() {
        TableColumn<Activite, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Activite a = getTableView().getItems().get(getIndex());
                    onDeleteActivite(a);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        activiteTable.getColumns().add(colAction);
    }

    private void onDeleteActivite(Activite a) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'activité");
        alert.setContentText("Voulez-vous vraiment supprimer cette activité ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            service.deleteActivite(a.getId());
            rafraichirLaListe();
        }
    }

    @FXML
    private void handleAjouter() {
        // Création d'un Dialog pour ajouter une activité
        Dialog<Activite> dialog = new Dialog<>();
        dialog.setTitle("Ajouter Activité");
        dialog.setHeaderText("Saisir les informations de l'activité");

        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField intituleField = new TextField();
        intituleField.setPromptText("Intitulé");

        TextField jourField = new TextField();
        jourField.setPromptText("Jour");

        TextField moisField = new TextField();
        moisField.setPromptText("Mois");

        TextField anneeField = new TextField();
        anneeField.setPromptText("Année");

        TextField horaireField = new TextField();
        horaireField.setPromptText("Horaire");

        TextField dureeField = new TextField();
        dureeField.setPromptText("Durée");

        TextField prixField = new TextField();
        prixField.setPromptText("Prix");

        grid.add(new Label("Intitulé:"), 0, 0);
        grid.add(intituleField, 1, 0);
        grid.add(new Label("Jour:"), 0, 1);
        grid.add(jourField, 1, 1);
        grid.add(new Label("Mois:"), 0, 2);
        grid.add(moisField, 1, 2);
        grid.add(new Label("Année:"), 0, 3);
        grid.add(anneeField, 1, 3);
        grid.add(new Label("Horaire:"), 0, 4);
        grid.add(horaireField, 1, 4);
        grid.add(new Label("Durée:"), 0, 5);
        grid.add(dureeField, 1, 5);
        grid.add(new Label("Prix:"), 0, 6);
        grid.add(prixField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ajouterButtonType) {
                try {
                    String intitule = intituleField.getText();
                    int jour = Integer.parseInt(jourField.getText());
                    int mois = Integer.parseInt(moisField.getText());
                    int annee = Integer.parseInt(anneeField.getText());
                    int horaire = Integer.parseInt(horaireField.getText());
                    int duree = Integer.parseInt(dureeField.getText());
                    double prix = Double.parseDouble(prixField.getText());

                    return new Activite(0, prix, intitule, new Date(jour, mois, annee), horaire, duree);
                } catch (Exception e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText("Erreur de saisie");
                    error.setContentText("Veuillez entrer des valeurs valides !");
                    error.showAndWait();
                }
            }
            return null;
        });

        Optional<Activite> result = dialog.showAndWait();
        result.ifPresent(a -> {
            service.addActivite(a, currentReservationId);
            rafraichirLaListe();
        });
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/enicar/gestionagencevoyages/reservation-list.fxml"));
            activiteTable.getScene().setRoot(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rafraichirLaListe() {
    activites.setAll(service.getActivitesForReservation(currentReservationId));
    }

}
