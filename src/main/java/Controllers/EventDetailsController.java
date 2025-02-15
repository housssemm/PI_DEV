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

        titleLabel.setText("Title: " + event.getTitre());
        descriptionLabel.setText("Description: " + event.getDescription());
        locationLabel.setText("Location: " + event.getLieu());
        priceLabel.setText("prix: " + event.getPrix());
        dateLabel.setText("Dates: " + event.getDateDebut() + " à " + event.getDateFin());
        typeLabel.setText("Type: " + event.getType());
        etatLabel.setText("Etat: " + event.getEtat());
        organisateurLabel.setText("Organisateur: " + event.getOrganisateur());
        maxLabel.setText("capacite Maximale : " + event.getCapaciteMaximale());

    }
}






