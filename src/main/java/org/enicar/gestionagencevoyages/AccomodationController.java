package org.enicar.gestionagencevoyages;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;

public class AccomodationController {
    public AccomodationController() {

    }
    @FXML
    private ListView<String> listSuppAcc; // Le type doit correspondre aux données

    public void initialize() {
        // 1. Remplir la liste (ici avec des données statiques pour l'exemple)
        listSuppAcc.setItems(FXCollections.observableArrayList(
                "Chambre individuelle",
                "Pension complete",
                "Spa",
                "Baby sitter"
        ));

        // 2. ÉTAPE CLÉ : Activer le mode de sélection multiple
        listSuppAcc.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}
