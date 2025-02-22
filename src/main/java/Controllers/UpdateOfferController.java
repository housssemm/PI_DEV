package Controllers;

import Models.Offre;
import Models.OffreCoach;
import Models.OffreProduit;
import Models.Etato;
import Services.CreateurEvenementService;
import Services.OffreService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;

public class UpdateOfferController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dureeValiditeField;

    @FXML
    private ChoiceBox<String> etatField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nouveauPrixTarifField;

    @FXML
    private TextField quantiteReservationMaxField;

    private Offre currentOffer;
    private OffreService offreService = new OffreService();

    @FXML
    public void initialize() {
        etatField.getItems().addAll("ACTIF", "INACTIF");
    }

    public void setOfferDetails(Offre offer) {
        this.currentOffer = offer;
        nomField.setText(offer.getNom());
        descriptionField.setText(offer.getDescription());
        dureeValiditeField.setValue(convertToLocalDateViaSqlDate(offer.getDuree_validite()));
        etatField.setValue(offer.getEtat().toString());

        if (offer instanceof OffreCoach) {
            OffreCoach offreCoach = (OffreCoach) offer;
            idField.setText(String.valueOf(offreCoach.getIdCoach()));
            nouveauPrixTarifField.setText(String.valueOf(offreCoach.getNouveauTarif()));
            quantiteReservationMaxField.setText(String.valueOf(offreCoach.getReservationMax()));
        } else if (offer instanceof OffreProduit) {
            OffreProduit offreProduit = (OffreProduit) offer;
            idField.setText(String.valueOf(offreProduit.getIdProduit()));
            nouveauPrixTarifField.setText(String.valueOf(offreProduit.getNouveauPrix()));
            quantiteReservationMaxField.setText(String.valueOf(offreProduit.getQuantiteMax()));
        }
    }

    private LocalDate convertToLocalDateViaSqlDate(java.util.Date dateToConvert) {
        return new Date(dateToConvert.getTime()).toLocalDate();
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        try {
            String nom = nomField.getText();
            String description = descriptionField.getText();
            LocalDate dureeValidite = dureeValiditeField.getValue();
            Etato etat = Etato.valueOf(etatField.getValue());
            int id = Integer.parseInt(idField.getText());
            double nouveauPrixTarif = Double.parseDouble(nouveauPrixTarifField.getText());
            int quantiteReservationMax = Integer.parseInt(quantiteReservationMaxField.getText());

            if (currentOffer instanceof OffreCoach) {
                OffreCoach offreCoach = (OffreCoach) currentOffer;
                offreCoach.setNom(nom);
                offreCoach.setDescription(description);
                offreCoach.setDuree_validite(Date.valueOf(dureeValidite));
                offreCoach.setEtat(etat);
                offreCoach.setIdCoach(id);
                offreCoach.setNouveauTarif(nouveauPrixTarif);
                offreCoach.setReservationMax(quantiteReservationMax);
                offreService.update(offreCoach);
            } else if (currentOffer instanceof OffreProduit) {
                OffreProduit offreProduit = (OffreProduit) currentOffer;
                offreProduit.setNom(nom);
                offreProduit.setDescription(description);
                offreProduit.setDuree_validite(Date.valueOf(dureeValidite));
                offreProduit.setEtat(etat);
                offreProduit.setIdProduit(id);
                offreProduit.setNouveauPrix(nouveauPrixTarif);
                offreProduit.setQuantiteMax(quantiteReservationMax);
                offreService.update(offreProduit);
            }

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre mise à jour avec succès.");
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour de l'offre.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //ROOT

    @FXML
    void GoToEvent(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Events.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GoToHome(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GoToProduit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GoToSeance(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajoutplanning.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GoToRec(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserReclamation.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void GoToOffre(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddOffre.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}