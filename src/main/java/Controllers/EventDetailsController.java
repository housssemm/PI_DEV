package Controllers;

import Models.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EventDetailsController {

    @FXML private Label titleLabel;
    @FXML private Label typeLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label priceLabel;
    @FXML private Label dateLabel;

    public void initialize() {
        // Empty initialize method, will be populated when event is passed.
    }

    public void setEventDetails(Evenement event) {
        // Set the details of the event to the corresponding labels
        titleLabel.setText("Title: " + event.getTitre());
        typeLabel.setText("Type: " + event.getType());
        descriptionLabel.setText("Description: " + event.getDescription());
        locationLabel.setText("Location: " + event.getLieu());
        priceLabel.setText("Price: " + event.getPrix());
        dateLabel.setText("Dates: " + event.getDateDebut() + " to " + event.getDateFin());
    }
}
