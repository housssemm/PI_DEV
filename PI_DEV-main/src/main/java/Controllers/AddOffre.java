package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddOffre {

    @FXML
    private Button offreCoachButton;

    @FXML
    private Button offreProduitButton;

    @FXML
    private Button offersButton;

    @FXML
    private void handleOffreCoachButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AddOffreCoach.fxml"));
            Stage stage = (Stage) offreCoachButton.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }

    @FXML
    private void handleOffreProduitButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AddOffreProduit.fxml"));
            Stage stage = (Stage) offreProduitButton.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }

    @FXML
    private void handleOffersButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/listeOffer.fxml"));
            Stage stage = (Stage) offersButton.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user
        }
    }
}