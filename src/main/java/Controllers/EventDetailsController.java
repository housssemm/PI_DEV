package Controllers;

import Models.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class EventDetailsController {

    @FXML private ImageView img;
    @FXML private Label titleLabel;
    @FXML private Label typeLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label priceLabel;
    @FXML private Label dateLabel;
    @FXML private Label etatLabel;
    @FXML private Label organisateurLabel;
    @FXML private Label maxLabel;

    public void initialize() {
        // Empty initialize method, will be populated when event is passed.
    }

//    public void setEventDetails(Evenement event) {
//
//            // Check if the event image is available
//            if (event.getImage() != null && event.getImage().length > 0) {
//                Image image = new Image(new ByteArrayInputStream(event.getImage()));
//                img.setImage(image);  // Set the image in the ImageView
//            } else {
//                // If there's no image, set a default image
//                img.setImage(new Image(getClass().getResourceAsStream("/path/to/default-image.jpg")));
//            }
//        // Set the details of the event to the corresponding labels
//
//        titleLabel.setText("Title: " + event.getTitre());
//        descriptionLabel.setText("Description: " + event.getDescription());
//        locationLabel.setText("Location: " + event.getLieu());
//        priceLabel.setText("prix: " + event.getPrix());
//        dateLabel.setText("Dates: " + event.getDateDebut() + " à " + event.getDateFin());
//        typeLabel.setText("Type: " + event.getType());
//        etatLabel.setText("Etat: " + event.getEtat());
//        organisateurLabel.setText("Organisateur: " + event.getOrganisateur());
//        maxLabel.setText("capacite Maximale : " + event.getCapaciteMaximale());
//
//    }


    public void setEventDetails(Evenement event) {
        // Check if the event image is available
        if (event.getImage() != null && event.getImage().length > 0) {
            Image image = new Image(new ByteArrayInputStream(event.getImage()));
            img.setImage(image);  // Set the image in the ImageView
        } else {
            // If there's no image, set a default image
            img.setImage(new Image(getClass().getResourceAsStream("/path/to/default-image.jpg")));
        }

        // Set the details of the event to the corresponding labels
        titleLabel.setText(event.getTitre());
        descriptionLabel.setText("Description: " + event.getDescription());
        locationLabel.setText(event.getLieu());
        priceLabel.setText("prix: " + event.getPrix());
        dateLabel.setText("Du " + event.getDateDebut() + " au " + event.getDateFin());
        typeLabel.setText("Type: " + event.getType());
        organisateurLabel.setText("Organisateur: " + event.getOrganisateur());
        maxLabel.setText("capacite Maximale : " + event.getCapaciteMaximale());
        // Set price label with "DT" suffix
        double price = event.getPrix();  // Assuming getPrix() returns a double
        priceLabel.setText( price + " DT");

        // Update the etatLabel based on the event state (ACTIF or EXPIRE)
        String eventState = String.valueOf(event.getEtat());
        System.out.println("Event state: " + eventState);  // Debugging output

        if ("ACTIF".equals(eventState)) {
            etatLabel.setText("Etat: " + eventState);
            etatLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;-fx-padding: 5 7; -fx-background-radius: 5;");
        } else if ("EXPIRE".equals(eventState)) {
            etatLabel.setText("Etat: " + eventState);
            etatLabel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;-fx-padding: 5 7; -fx-background-radius: 5;");  // Red for expired
        } else {
            etatLabel.setText("Etat: " + eventState);
            etatLabel.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black;");  // Default gray color
        }
    }


}






