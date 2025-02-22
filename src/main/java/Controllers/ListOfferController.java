package Controllers;

import Models.Offre;
import Models.OffreCoach;
import Services.CreateurEvenementService;
import Services.OffreService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ListOfferController {

    @FXML
    private VBox offerList; // VBox où les offres seront affichées

    private OffreService offreService = new OffreService();

    @FXML
    public void initialize() {
        loadOffers();
    }

    void loadOffers() {
        try {
            List<Offre> offers = offreService.getAll();
            HBox row = new HBox(50); // HBox pour contenir 2 cartes par ligne
            row.setStyle("-fx-alignment: center-start;"); // Alignement à gauche

            for (int i = 0; i < offers.size(); i++) {
                Offre offer = offers.get(i);

                // Création de la carte de l'offre (VBox)
                VBox offerCard = new VBox();
                offerCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-padding: 20;");
                offerCard.setPrefSize(350, 309);
                offerCard.setSpacing(10);

                // Nom
                Label nameLabel = new Label(offer.getNom());
                nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #333333;");

                // Type et état
                HBox typeStatusBox = new HBox(10);
                Label typeLabel = new Label("Type: " + (offer instanceof OffreCoach ? "Coach" : "Produit"));
                typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666; -fx-padding: 5 7; -fx-background-radius: 5; -fx-background-color: #e0e0e0;");

                Label etatLabel = new Label("Etat: " + offer.getEtat());
                String etat = String.valueOf(offer.getEtat());
                if ("ACTIF".equals(etat)) {
                    etatLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
                } else if ("INACTIF".equals(etat)) {
                    etatLabel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
                } else {
                    etatLabel.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 5 7; -fx-background-radius: 5;");
                }

                typeStatusBox.getChildren().addAll(typeLabel, etatLabel);

                // Description
                Label descriptionLabel = new Label("Description: " + offer.getDescription());
                descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
                descriptionLabel.setWrapText(true);

                // Détails (Durée de validité)
                GridPane detailsGrid = new GridPane();
                detailsGrid.setHgap(10);
                detailsGrid.setVgap(8);
                Label validiteLabel = new Label(""+ offer.getDuree_validite());
                detailsGrid.addRow(0, new Label("Validité:"), validiteLabel);

                // Séparateur
                Separator separator = new Separator();
                separator.setStyle("-fx-padding: 10 0;");

                // Boutons de mise à jour et de suppression
                HBox buttonBox = new HBox(10);
                Button updateButton = new Button("Mettre à jour");
                updateButton.setOnAction(event -> handleUpdateButtonAction(offer));
                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(event -> handleDeleteButtonAction(offer));
                buttonBox.getChildren().addAll(updateButton, deleteButton);

                // Ajouter les composants à la carte
                offerCard.getChildren().addAll(nameLabel, typeStatusBox, descriptionLabel, detailsGrid, separator, buttonBox);

                // Ajouter la carte à la ligne actuelle
                row.getChildren().add(offerCard);

                // Tous les 2 offres, ajouter la ligne à la VBox et créer une nouvelle ligne
                if ((i + 1) % 2 == 0 || i == offers.size() - 1) {
                    offerList.getChildren().add(row);
                    row = new HBox(50);
                    row.setStyle("-fx-alignment: center-start;");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateButtonAction(Offre offer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateOffre.fxml"));
            Parent root = loader.load();

            UpdateOfferController updateOfferController = loader.getController();
            updateOfferController.setOfferDetails(offer);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mettre à jour l'offre");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteButtonAction(Offre offer) {
        try {
            offreService.delete(offer.getId());
            offerList.getChildren().clear();
            loadOffers(); // Recharger les offres après suppression
        } catch (Exception e) {
            e.printStackTrace();
        }
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