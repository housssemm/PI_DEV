package Controllers;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


import Models.Evenement;
import Services.EvenementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Events {

    @FXML private VBox eventList; // VBox where events will be displayed

    private EvenementService evenementService = new EvenementService();


        @FXML
    public void initialize() {
        loadEvents();
    }

//    private void loadEvents() {
//        try {
//            List<Evenement> events = evenementService.getAll(); // Get all events from the service
//            for (Evenement event : events) {
//                HBox eventBox = new HBox(10); // Create a new HBox for each event
//
//                // Create ImageView for the event image
//                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(event.getImage())));
//                imageView.setFitHeight(50);
//                imageView.setFitWidth(50);
//
//                // Create Label for the event type
//                Label typeLabel = new Label(event.getType());
//
//                // Create Label for the event title
//                Label titleLabel = new Label(event.getTitre());
//
//                // Create Label for the event location
//                Label locationLabel = new Label(event.getLieu());
//
//                // Create a Button for more details
//                Button detailsButton = new Button("Plus de détails");
//                detailsButton.setOnAction(e -> showEventDetails(event)); // Handle button action
//
//                // Add components to the HBox
//                eventBox.getChildren().addAll(imageView, typeLabel, titleLabel, locationLabel, detailsButton);
//
//                // Add the HBox to the VBox
//                eventList.getChildren().add(eventBox);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle errors here
//        }
//    }



    void loadEvents() {
        try {
            List<Evenement> events = evenementService.getAll();
            for (Evenement event : events) {
                // Create main card container
                VBox eventCard = new VBox();
                eventCard.getStyleClass().add("event-card");
                eventCard.setSpacing(10);
                eventCard.setPadding(new Insets(15));

                // Image section
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(event.getImage())));
                imageView.setFitHeight(300);
                imageView.setFitWidth(300);
                imageView.setPreserveRatio(true);
                imageView.getStyleClass().add("event-image");

                // Text content section
                VBox textContent = new VBox(5);

                Label titleLabel = new Label(event.getTitre());
                titleLabel.getStyleClass().add("event-title");

                Label typeLabel = new Label("Type: " + event.getType());
                typeLabel.getStyleClass().add("event-detail");

                Label locationLabel = new Label("Lieu: " + event.getLieu());
                locationLabel.getStyleClass().add("event-detail");

                Label dateLabel = new Label("Date: " + event.getDateDebut());
             // Add date if available
                dateLabel.getStyleClass().add("event-detail");



                Button detailsButton = new Button("Plus de détails");
                detailsButton.getStyleClass().add("details-button");
                detailsButton.setOnAction(e -> showEventDetails(event));

                // Bouton supprimer
                Button deleteButton = new Button("Supprimer");
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(e -> deleteEvent(event));
                // Bouton Modifier
                Button updateButton = new Button("Modifier");
                updateButton.getStyleClass().add("update-button");
                updateButton.setOnAction(e -> {
                    try {
                        updateEvent(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });


                // Arrange layout
                HBox contentRow = new HBox(15);
                contentRow.getChildren().addAll(imageView, textContent);

                textContent.getChildren().addAll(
                        titleLabel,
                        typeLabel,
                        locationLabel,
                        dateLabel,
                        detailsButton,
                        deleteButton,
                        updateButton

                );

                eventCard.getChildren().add(contentRow);
                eventList.getChildren().add(eventCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void showEventDetails(Evenement event) {
//        try {
//            // Load the event details FXML
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetail.fxml"));
//            Parent root = loader.load();
//
//            // Get the controller of the event details screen
//            EventDetailsController eventDetailsController = loader.getController();
//
//            // Pass the event data to the controller
//            eventDetailsController.setEventDetails(event);
//
//            // Create a new stage and show the event details scene
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Event Details");
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle errors if the FXML fails to load
//        }
//    }
private void showEventDetails(Evenement event) {
    try {
        // Charger la vue des détails de l'événement
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetail.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur de la vue des détails de l'événement
        EventDetailsController eventDetailsController = loader.getController();

        // Passer les détails de l'événement au contrôleur
        eventDetailsController.setEventDetails(event);

        // Récupérer la scène actuelle et mettre à jour le contenu
        Stage stage = (Stage) eventList.getScene().getWindow(); // Utilisation du stage actuel
        stage.setScene(new Scene(root)); // Mise à jour de la scène
        stage.setTitle("Détails de l'événement"); // Titre de la fenêtre
        stage.show(); // Afficher la nouvelle scène
    } catch (IOException e) {
        e.printStackTrace();
        // Gérer les erreurs si le fichier FXML ne peut pas être chargé
    }
}


    private void deleteEvent(Evenement event) {
        try {
            evenementService.delete(event.getId()); // Supprime de la base de données
            eventList.getChildren().clear(); // Efface l'affichage
            loadEvents(); // Recharge la liste mise à jour
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void updateEvent(Evenement event) {
//        try {
//            // Charger l'interface de modification
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
//            Parent root = loader.load();
//
//            // Récupérer le contrôleur et lui passer l'événement à modifier
//            UpdateEventController updateController = loader.getController();
//            updateController.setEventData(event, this); // Passe l'event et la référence au contrôleur principal
//
//            // Ouvrir une nouvelle fenêtre pour la modification
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Modifier l'événement");
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
@FXML
void updateEvent(Evenement event) throws IOException {
    // Charger la vue de modification d'événement
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
    Parent root = loader.load();

    // Récupérer le contrôleur et lui passer l'événement à modifier
    UpdateEventController updateController = loader.getController();

    // Passer l'événement sélectionné au contrôleur
    if (event != null) {
        updateController.setEventData(event, this);
    }

    // Récupérer la scène de l'événement ActionEvent et changer le root de la scène actuelle
    Stage stage = (Stage) eventList.getScene().getWindow();  // Assurez-vous que vous utilisez la scène de l'événement
    stage.setScene(new Scene(root));
    stage.setTitle("Modifier l'événement");
    stage.show();
}


}
