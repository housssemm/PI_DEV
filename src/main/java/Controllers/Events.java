//
//
//package Controllers;
//
//import javafx.scene.control.Label;
//import javafx.scene.control.Separator;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import Models.Evenement;
//import Services.EvenementService;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class Events {
//
//    @FXML
//    private VBox eventList; // VBox where events will be displayed
//
//    private EvenementService evenementService = new EvenementService();
//
//    @FXML
//    public void initialize() {
//        loadEvents();
//    }
//
//
//
//
//
//
//    void loadEvents() {
//        try {
//            List<Evenement> events = evenementService.getAll();
//            HBox row = new HBox(50); // Create a new HBox for each row of event cards
//            row.setStyle("-fx-alignment: center-start;"); // Align the items to the start of the HBox
//
//            for (int i = 0; i < events.size(); i++) {
//                Evenement event = events.get(i);
//
//                // Create the main card container (VBox)
//                VBox eventCard = new VBox();
//                eventCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-padding: 20;");
//                eventCard.setPrefSize(350, 309);
//                eventCard.setSpacing(10);
//
//                // Image section (ImageView)
//                ImageView imageView = new ImageView();
//                if (event.getImage() != null && event.getImage().length > 0) {
//                    imageView.setImage(new Image(new ByteArrayInputStream(event.getImage())));
//                } else {
//                    imageView.setImage(new Image(getClass().getResourceAsStream("/path/to/default-image.jpg")));
//                }
//                imageView.setFitWidth(350);
//                imageView.setPreserveRatio(true);
//                imageView.setStyle("-fx-background-radius: 15 15 0 0; -fx-cursor: hand;");
//
//                // Title Label
//                Label titleLabel = new Label(event.getTitre());
//                titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #333333;");
//
//                // Type and Status Labels (HBox for type and status)
//                HBox typeStatusBox = new HBox(10);
//                Label typeLabel = new Label("Type: " + event.getType());
//                typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666; -fx-padding: 5 7; -fx-background-radius: 5; -fx-background-color: #e0e0e0;");
//
//                // Set status color based on 'etat'
//                Label etatLabel = new Label("Etat: " + event.getEtat());
//                String etat = String.valueOf(event.getEtat());
//                if ("ACTIF".equals(etat)) {
//                    etatLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
//                } else if ("EXPIRE".equals(etat)) {
//                    etatLabel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
//                } else {
//                    etatLabel.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 5 7; -fx-background-radius: 5;");
//                }
//
//                typeStatusBox.getChildren().addAll(typeLabel, etatLabel);
//
//                // Description Label
//                Label descriptionLabel = new Label("Description: " + event.getDescription());
//                descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666; -fx-line-spacing: 3px;");
//                descriptionLabel.setWrapText(true);
//
//                // Location, Date, Price (GridPane)
//                GridPane detailsGrid = new GridPane();
//                detailsGrid.setHgap(10);
//                detailsGrid.setVgap(8);
//                Label locationLabel = new Label(event.getLieu());
//                Label dateLabel = new Label("Du " + event.getDateDebut() + " au " + event.getDateFin());
//                Label priceLabel = new Label(event.getPrix() + " DT");
//                detailsGrid.addRow(0, new Label("Lieu:"), locationLabel);
//                detailsGrid.addRow(1, new Label("Date:"), dateLabel);
//                detailsGrid.addRow(2, new Label("Prix:"), priceLabel);
//
//                // Separator between sections
//                Separator separator = new Separator();
//                separator.setStyle("-fx-padding: 10 0;");
//
//                // Organizer and Max Participants section (HBox)
//                HBox organizerBox = new HBox(15);
//                Label organizerLabel = new Label("👤"+event.getOrganisateur());
//                Label maxLabel = new Label("👥"+event.getCapaciteMaximale());
//                organizerBox.getChildren().addAll(organizerLabel, maxLabel);
//
//                // Adding all components to the eventCard
//                eventCard.getChildren().addAll(imageView, titleLabel, typeStatusBox, descriptionLabel, detailsGrid, separator, organizerBox);
//
//                // Add eventCard to the current row (HBox)
//                row.getChildren().add(eventCard);
//
//                // Every 2 events, add the row to the eventList and create a new row
//                if ((i + 1) % 2 == 0 || i == events.size() - 1) {
//                    eventList.getChildren().add(row);
//                    row = new HBox(50); // Start a new row with spacing
//                    row.setStyle("-fx-alignment: center-start;");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//    private void showEventDetails(Evenement event) {
//        try {
//            // Charger la vue des détails de l'événement
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetail.fxml"));
//            Parent root = loader.load();
//
//            // Récupérer le contrôleur de la vue des détails de l'événement
//            EventDetailsController eventDetailsController = loader.getController();
//
//            // Passer les détails de l'événement au contrôleur
//            eventDetailsController.setEventDetails(event);
//
//            // Récupérer la scène actuelle et mettre à jour le contenu
//            Stage stage = (Stage) eventList.getScene().getWindow(); // Utilisation du stage actuel
//            stage.setScene(new Scene(root)); // Mise à jour de la scène
//            stage.setTitle("Détails de l'événement"); // Titre de la fenêtre
//            stage.show(); // Afficher la nouvelle scène
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void deleteEvent(Evenement event) {
//        try {
//            evenementService.delete(event.getId()); // Supprime de la base de données
//            eventList.getChildren().clear(); // Efface l'affichage
//            loadEvents(); // Recharge la liste mise à jour
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }}





package Controllers;

import Services.CreateurEvenementService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Models.Evenement;
import Services.EvenementService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Events {

    @FXML
    private VBox eventList; // VBox où les événements seront affichés

    private EvenementService evenementService = new EvenementService();

    @FXML
    public void initialize() {
        loadEvents();
    }

    void loadEvents() {
        try {
            List<Evenement> events = evenementService.getAll();
            HBox row = new HBox(50); // HBox pour contenir 2 cartes par ligne
            row.setStyle("-fx-alignment: center-start;"); // Alignement à gauche

            for (int i = 0; i < events.size(); i++) {
                Evenement event = events.get(i);

                // Création de la carte de l'événement (VBox)
                VBox eventCard = new VBox();
                eventCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-padding: 20;");
                eventCard.setPrefSize(350, 309);
                eventCard.setSpacing(10);

                // ImageView
                ImageView imageView = new ImageView();
                if (event.getImage() != null && event.getImage().length > 0) {
                    imageView.setImage(new Image(new ByteArrayInputStream(event.getImage())));
                } else {
                    imageView.setImage(new Image(getClass().getResourceAsStream("/path/to/default-image.jpg")));
                }
                imageView.setFitWidth(350);
                imageView.setPreserveRatio(true);
                imageView.setStyle("-fx-background-radius: 15 15 0 0; -fx-cursor: hand;");

                // Titre
                Label titleLabel = new Label(event.getTitre());
                titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #333333;");

                // Type et état
                HBox typeStatusBox = new HBox(10);
                Label typeLabel = new Label("Type: " + event.getType());
                typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666; -fx-padding: 5 7; -fx-background-radius: 5; -fx-background-color: #e0e0e0;");

                Label etatLabel = new Label("Etat: " + event.getEtat());
                String etat = String.valueOf(event.getEtat());
                if ("ACTIF".equals(etat)) {
                    etatLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
                } else if ("EXPIRE".equals(etat)) {
                    etatLabel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
                } else {
                    etatLabel.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 5 7; -fx-background-radius: 5;");
                }

                typeStatusBox.getChildren().addAll(typeLabel, etatLabel);

                // Description
                Label descriptionLabel = new Label("Description: " + event.getDescription());
                descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
                descriptionLabel.setWrapText(true);

                // Détails (Lieu, Date, Prix)
                GridPane detailsGrid = new GridPane();
                detailsGrid.setHgap(10);
                detailsGrid.setVgap(8);
                Label locationLabel = new Label(event.getLieu());
                Label dateLabel = new Label("Du " + event.getDateDebut() + " au " + event.getDateFin());
                Label priceLabel = new Label(event.getPrix() + " DT");
                detailsGrid.addRow(0, new Label("Lieu:"), locationLabel);
                detailsGrid.addRow(1, new Label("Date:"), dateLabel);
                detailsGrid.addRow(2, new Label("Prix:"), priceLabel);



                priceLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: 700;");
                // Séparateur
                Separator separator = new Separator();
                separator.setStyle("-fx-padding: 10 0;");

                // Organisateur et Capacité maximale
                HBox organizerBox = new HBox(15);
                Label organizerLabel = new Label("👤 " + event.getOrganisateur());
                Label maxLabel = new Label("👥 " + event.getCapaciteMaximale());
                organizerBox.getChildren().addAll(organizerLabel, maxLabel);

                // Ajouter les composants à la carte
                eventCard.getChildren().addAll(imageView, titleLabel, typeStatusBox, descriptionLabel, detailsGrid, separator, organizerBox);

                // Ajouter un événement de clic sur la carte
                eventCard.setOnMouseClicked(eventClick -> showEventDetails(event));

                // Ajouter la carte à la ligne actuelle
                row.getChildren().add(eventCard);

                // Tous les 2 événements, ajouter la ligne à la VBox et créer une nouvelle ligne
                if ((i + 1) % 2 == 0 || i == events.size() - 1) {
                    eventList.getChildren().add(row);
                    row = new HBox(50);
                    row.setStyle("-fx-alignment: center-start;");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showEventDetails(Evenement event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetail.fxml"));
            Parent root = loader.load();

            EventDetailsController eventDetailsController = loader.getController();
            eventDetailsController.setEventDetails(event);

            Stage stage = (Stage) eventList.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Détails de l'événement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//ROOT
private CreateurEvenementService createurEvenementService = new CreateurEvenementService();
    @FXML
    void GoToEvent(ActionEvent actionEvent) {
        int id = Session.getInstance().getCurrentUser().getId();
        String path = "";

        try {
            if (createurEvenementService.isCreateurEvenement(id)) {
                path = "/AddEvenement.fxml";
            } else {
                path = "/Events.fxml";
            }

            // Now load the determined path
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ((Node) actionEvent.getSource()).getScene().setRoot(root);

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
