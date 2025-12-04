package org.enicar.gestionagencevoyages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnexionController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void onLogin(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        boolean ok = "admin".equals(user) && "admin".equals(pass);

        if (!ok) {
            errorLabel.setText("Identifiants incorrects");
            return;
        }

        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainRoot));
            stage.setTitle("Gestion Agence - connecté");
        } catch (Exception e) {
            errorLabel.setText("Erreur ouverture interface principale");
            e.printStackTrace();
        }
    }
}