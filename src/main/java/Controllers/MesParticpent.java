//package Controllers;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Insets;
//import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import java.util.List;
//import Models.Evenement;
//import Models.User;
//import Services.ParticipantEvenementService;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
//public class MesParticpent {
//
//    @FXML private Label eventTitle;
//    @FXML private Label eventDate;
//    @FXML private Label eventLocation;
//    @FXML private Label eventDescription;
//
//    @FXML private TableView<User> participantsTable;
//
//    @FXML private TableColumn<User, String> colNom;
//    @FXML private TableColumn<User, String> colEmail;
//
//    private ParticipantEvenementService participantService = new ParticipantEvenementService();
//public void initialize() {
//    colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
//    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//
//}
//    public void setEventData(Evenement event, MyEvents myEvents) {
//        eventTitle.setText(event.getTitre());
//        eventDate.setText("Date: " + event.getDateDebut() + " - " + event.getDateFin());
//        eventLocation.setText("Lieu: " + event.getLieu());
//        eventDescription.setText("Description: " + event.getDescription());
//
//        loadParticipants(event.getId());
//    }
//
//
//
//    private void loadParticipants(int eventId) {
//        List<User> participants = participantService.getParticipantsByEvent(eventId);
//        System.out.println("Participants récupérés : " + participants);
//
//        participantsTable.getItems().setAll(participants);
//    }


package Controllers;

import Models.EtatEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Models.Evenement;
import Models.User;
import Services.ParticipantEvenementService;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.ByteArrayInputStream;
import java.util.List;

public class MesParticpent {

    @FXML private VBox eventDetailsContainer;
    @FXML private TableView<User> participantsTable;
    @FXML private TableColumn<User, String> colNom;
    @FXML private TableColumn<User, String> colEmail;

    private ParticipantEvenementService participantService = new ParticipantEvenementService();

    public void initialize() {
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void setEventData(Evenement event, MyEvents myEvents) {
        // Clear previous content
        eventDetailsContainer.getChildren().clear();

        // Create event card similar to MyEvents
        VBox eventCard = createEventCard(event);
        eventDetailsContainer.getChildren().addAll(eventCard, participantsTable);

        loadParticipants(event.getId());
    }

    private VBox createEventCard(Evenement event) {
        VBox eventCard = new VBox();
        eventCard.getStyleClass().add("event-card");
        eventCard.setSpacing(10);
        eventCard.setPadding(new Insets(15));
        eventCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 0);");

        // Image section
        ImageView imageView = new ImageView();
        if (event.getImage() != null && event.getImage().length > 0) {
            imageView.setImage(new Image(new ByteArrayInputStream(event.getImage())));
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/default_event.jpg")));
        }
        imageView.setFitHeight(200);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        // Text content section
        VBox textContent = new VBox(5);
        Label titleLabel = new Label(event.getTitre());
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #333333;");

        Label typeLabel = new Label("Type: " + event.getType());
        typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

        Label locationLabel = new Label("Lieu: " + event.getLieu());
        locationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

        Label dateLabel = new Label("Date : Du " + event.getDateDebut() + " au " + event.getDateFin());
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

        Label descLabel = new Label("Description: " + event.getDescription());
        descLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");

        Label priceLabel = new Label("Prix: " + event.getPrix() + " DT");
        priceLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: 700;");

        // Status label
        Label etatLabel = new Label("Etat: " + event.getEtat());
        EtatEvenement etat = event.getEtat();
        if ("ACTIF".equals(etat)) {
            etatLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
        } else if ("EXPIRE".equals(etat)) {
            etatLabel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 5 7; -fx-background-radius: 5;");
        } else {
            etatLabel.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 5 7; -fx-background-radius: 5;");
        }

        // Organizer info
        HBox organizerBox = new HBox(15);
        Label organizerLabel = new Label("👤 " + event.getOrganisateur());
        Label maxLabel = new Label("👥 " + event.getCapaciteMaximale());
        organizerBox.getChildren().addAll(organizerLabel, maxLabel);

        // Assemble text content
        textContent.getChildren().addAll(
                titleLabel,
                descLabel,
                typeLabel,
                locationLabel,
                dateLabel,
                priceLabel,
                etatLabel,
                organizerBox
        );

        // Create content row
        HBox contentRow = new HBox(15);
        contentRow.getChildren().addAll(imageView, textContent);

        eventCard.getChildren().add(contentRow);
        return eventCard;
    }

    private void loadParticipants(int eventId) {
        List<User> participants = participantService.getParticipantsByEvent(eventId);
        participantsTable.getItems().setAll(participants);
    }

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







