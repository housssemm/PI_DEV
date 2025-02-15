package Controllers;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


import Models.Evenement;
import Services.EvenementService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
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



    private void loadEvents() {
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

                Text description = new Text(event.getDescription());
                description.setWrappingWidth(300);
                description.getStyleClass().add("event-detail");

                // Button section
                Button detailsButton = new Button("Plus de détails");
                detailsButton.getStyleClass().add("details-button");
                detailsButton.setOnAction(e -> showEventDetails(event));

                // Arrange layout
                HBox contentRow = new HBox(15);
                contentRow.getChildren().addAll(imageView, textContent);

                textContent.getChildren().addAll(
                        titleLabel,
                        typeLabel,
                        locationLabel,
                        dateLabel,
                        description,
                        detailsButton
                );

                eventCard.getChildren().add(contentRow);
                eventList.getChildren().add(eventCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showEventDetails(Evenement event) {
        try {
            // Load the event details FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetail.fxml"));
            Parent root = loader.load();

            // Get the controller of the event details screen
            EventDetailsController eventDetailsController = loader.getController();

            // Pass the event data to the controller
            eventDetailsController.setEventDetails(event);

            // Create a new stage and show the event details scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors if the FXML fails to load
        }
    }
}
