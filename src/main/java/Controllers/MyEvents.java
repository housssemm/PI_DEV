package Controllers;

import javafx.event.ActionEvent;
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

public class MyEvents {

    @FXML private VBox eventList; // VBox where events will be displayed

    private EvenementService evenementService = new EvenementService();


        @FXML
    public void initialize() {
        loadEvents();
    }




void loadEvents() {
    try {
        List<Evenement> events = evenementService.getAll();  // Fetch all events
        for (Evenement event : events) {
            // Create main card container
            VBox eventCard = new VBox();
            eventCard.getStyleClass().add("event-card");
            eventCard.setSpacing(10);
            eventCard.setPadding(new Insets(15));
            eventCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

            // Image section
            ImageView imageView = new ImageView();
            if (event.getImage() != null && event.getImage().length > 0) {
                imageView.setImage(new Image(new ByteArrayInputStream(event.getImage())));
            } else {
                imageView.setImage(new Image(getClass().getResourceAsStream("/path/to/default-image.jpg")));
            }
            imageView.setFitHeight(200);
            imageView.setFitWidth(350);
            imageView.setPreserveRatio(true);
            imageView.getStyleClass().add("event-image");

            // Text content section
            VBox textContent = new VBox(5);
            Label titleLabel = new Label(event.getTitre());
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #333333;");

            Label typeLabel = new Label("Type: " + event.getType());
            typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

            Label locationLabel = new Label("Lieu: " + event.getLieu());
            locationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

            Label dateLabel = new Label("Date :Du " + event.getDateDebut() + " au " + event.getDateFin());
            dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

            Label decLabel = new Label("Description: "+event.getDescription());
            decLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

            Label priceLabel = new Label("Prix: "+event.getPrix() + " DT");
            priceLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: 700;");

            // Organisateur et Capacité maximale
            HBox organizerBox = new HBox(15);
            Label organizerLabel = new Label("👤 " + event.getOrganisateur());
            Label maxLabel = new Label("👥 " + event.getCapaciteMaximale());
            organizerBox.getChildren().addAll(organizerLabel, maxLabel);

            // Etat label (status)
            Label etatLabel = new Label("Etat: " + event.getEtat());
            String etat = String.valueOf(event.getEtat());
            if ("ACTIF".equals(etat)) {
                etatLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
            } else if ("EXPIRE".equals(etat)) {
                etatLabel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
            } else {
                etatLabel.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 5 7; -fx-background-radius: 5;");
            }

            // Remove the separate VBox (typeStatusBox) as you are directly adding it to the textContent
            textContent.getChildren().addAll(
                    titleLabel,
                    decLabel,
                    typeLabel,
                    locationLabel,
                    dateLabel,
                    priceLabel,
                    etatLabel,
                    organizerBox
//                    organizerLabel,
//                    maxLabel

            );

            // Create an HBox for the buttons "Supprimer" and "Modifier"
            HBox buttonBox = new HBox(10); // 10px spacing between buttons
            buttonBox.setStyle("-fx-alignment: center-left;"); // Align buttons to the left

            // Supprimer button
            Button deleteButton = new Button("Supprimer");
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setOnAction(e -> deleteEvent(event));

            // Modifier button
            Button updateButton = new Button("Modifier");
            updateButton.getStyleClass().add("update-button");
            updateButton.setOnAction(e -> {
                try {
                    updateEvent(event);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Add buttons to the HBox
            buttonBox.getChildren().addAll(updateButton, deleteButton);

            // Add the button box to textContent
            textContent.getChildren().add(buttonBox);

            // Create a container to hold the image and textContent side by side
            HBox contentRow = new HBox(15);
            contentRow.getChildren().addAll(imageView, textContent);

            // Add the contentRow to the eventCard VBox
            eventCard.getChildren().add(contentRow);

            // Add the eventCard to the VBox holding the list of events
            eventList.getChildren().add(eventCard);
        }
    } catch (Exception e) {
        e.printStackTrace();
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

@FXML
void updateEvent(Evenement event) throws IOException {
    // Charger la vue de modification d'événement
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
    Parent root = loader.load();

    // Récupérer le contrôleur et lui passer l'événement à modifier
    UpdateEventController updateController = loader.getController();

    // Passer l'événement sélectionné au contrôleur
    if (event != null) {
        updateController.setEventData(event, this); // Make sure setEventData() accepts Evenement
    }

    // Récupérer la scène de l'événement ActionEvent et changer le root de la scène actuelle
    Stage stage = (Stage) eventList.getScene().getWindow();  // Assurez-vous que vous utilisez la scène de l'événement
    stage.setScene(new Scene(root));
    stage.setTitle("Modifier l'événement");
    stage.show();
}
//ROOT
    @FXML
    void GoToEvent(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEvenement.fxml"));
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
}
