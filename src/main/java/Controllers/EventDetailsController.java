package Controllers;

import Models.Evenement;
import Models.ParticipantEvenement;
import Models.etatPaiement;
import Services.CreateurEvenementService;
import Services.ParticipantEvenementService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.ByteArrayInputStream;

import java.util.Date;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;


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
        this.event = event;
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
    private Evenement event;
    @FXML
    private void handleReservationClick(ActionEvent event) {
        if (this.event == null) {
            System.out.println("Erreur : Aucun événement sélectionné.");
            return;
        }

        int idEvenement = this.event.getId();
        int idParticipant = Session.getInstance().getCurrentUser().getId(); // Récupère l'ID du participant depuis la session
        Date dateInscription = new Date(); // Date actuelle
        etatPaiement etat = etatPaiement.EN_ATTENTE; // Valeur par défaut

        ParticipantEvenement participant = new ParticipantEvenement(idParticipant, dateInscription, etat, idEvenement);

        ParticipantEvenementService service = new ParticipantEvenementService();
        try {
            boolean success = service.create(participant);
            if (success) {
                //send mail
                String recipientEmail = Session.getInstance().getCurrentUser().getEmail();
                sendEmail(recipientEmail, this.event);

                // Affiche une alerte de succès avec un message de rappel
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réservation réussie");
                alert.setHeaderText(null);
                alert.setContentText("Votre réservation a été effectuée avec succès !\n\n⚠️Attention : Si vous ne fixez pas votre état de paiement \ndans 3 jours, votre réservation sera annulée.");
                alert.showAndWait();
            } else {
                // Affiche une alerte d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Échec de la réservation");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de votre réservation.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'inscription : " + e.getMessage());

            // Affiche une alerte d'erreur en cas d'exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur est survenue");
            alert.setContentText("Détails : " + e.getMessage());
            alert.showAndWait();
        }
    }
//    private void sendEmail(String recipientEmail, Evenement event) {
//        // Sender's email ID and password
////        final String username = "your-email@example.com";
////        final String password = "your-email-password";
//        final String username = "houssem.labidi9393@gmail.com";
//        final String password = "5def650c8c";
//
//        // Setup mail server properties
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.example.com");
//        props.put("mail.smtp.port", "587");
//
//        // Get the Session object
//        javax.mail.Session session = javax.mail.Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                        return new javax.mail.PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            // Create a default MimeMessage object
//            Message message = new MimeMessage(session);
//
//            // Set From: header field
//            message.setFrom(new InternetAddress(username));
//
//            // Set To: header field
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//
//            // Set Subject: header field
//            message.setSubject("Event Details: " + event.getTitre());
//
//            // Create the message body
//            String body = "Event Details:\n\n" +
//                    "Title: " + event.getTitre() + "\n" +
//                    "Description: " + event.getDescription() + "\n" +
//                    "Location: " + event.getLieu() + "\n" +
//                    "Price: " + event.getPrix() + " DT\n" +
//                    "Date: " + event.getDateDebut() + " to " + event.getDateFin() + "\n" +
//                    "Type: " + event.getType() + "\n" +
//                    "Organizer: " + event.getOrganisateur() + "\n" +
//                    "Max Capacity: " + event.getCapaciteMaximale() + "\n" +
//                    "State: " + event.getEtat();
//
//            // Set the message body
//            message.setText(body);
//
//            // Send message
//            Transport.send(message);
//
//            System.out.println("Email sent successfully!");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
private void sendEmail(String recipientEmail, Evenement event) {
    // Mailjet API credentials
//    final String apiKey = "your-mailjet-api-key";
//    final String apiSecret = "your-mailjet-api-secret";

    // Initialize Mailjet client using the builder pattern
    ClientOptions options = ClientOptions.builder()
            .apiKey(apiKey)
            .apiSecretKey(apiSecret)
            .build();
    MailjetClient client = new MailjetClient(options);

    // Create the email content
    JSONObject message = new JSONObject();
    message.put(Emailv31.Message.FROM, new JSONObject()
            .put("Email", "houssemm.labidi@gmail.com")
            .put("Name", "Couchini"));
    message.put(Emailv31.Message.TO, new JSONArray()
            .put(new JSONObject()
                    .put("Email", recipientEmail)
                    .put("Name", "Recipient Name")));
    message.put(Emailv31.Message.SUBJECT, "Evénement Details: " + event.getTitre());
    message.put(Emailv31.Message.TEXTPART,
            "Titre : " + event.getTitre() + "\n" +
            "Description: " + event.getDescription() + "\n" +
            "Lieu: " + event.getLieu() + "\n" +
            "Prix: " + event.getPrix() + " DT\n" +
            "Date : " + event.getDateDebut() + " au " + event.getDateFin() + "\n" +
            "Type: " + event.getType() + "\n" +
            "Organisateur : " + event.getOrganisateur() + "\n\n"

    );
    message.put(Emailv31.Message.HTMLPART,
            "Bonjour <b>" +Session.getInstance().getCurrentUser().getPrenom()+"</b><br />" +
                    "Vous êtes bien inscrit à l’événement "+event.getTitre()+" via notre application Coachini.<br />" +
                    " Voici les détails de votre inscription :<br /><br />"+

                    "<h3>Evénement Details:</h3><br />" +
            "<b>Titre:</b> " + event.getTitre() + "<br />" +
            "<b>Description:</b> " + event.getDescription() + "<br />" +
            "<b>Lieu:</b> " + event.getLieu() + "<br />" +
            "<b>Prix:</b> " + event.getPrix() + " DT<br />" +
            "<b>Date:</b> " + event.getDateDebut() + " to " + event.getDateFin() + "<br />" +
            "<b>Type:</b> " + event.getType() + "<br />" +
            "<b>Organisateur :</b> " + event.getOrganisateur() + "<br /><br />"+
            "<b>⚠\uFE0F Important :</b> Paiement requis <br />"+
            "Pour valider définitivement votre inscription, veuillez effectuer le paiement dans un délai de trois jours. <br /><br />"+
            "Passé ce délai, votre inscription sera annulée automatiquement. <br />"+
            "Merci de votre confiance et à bientôt ! <br />"+
            "<h2>L’équipe Coachini<h2>"
          );

    // Create the Mailjet request
    MailjetRequest request = new MailjetRequest(Emailv31.resource)
            .property(Emailv31.MESSAGES, new JSONArray().put(message));

    try {
        // Send the email
        MailjetResponse response = client.post(request);
        if (response.getStatus() == 200) {
            System.out.println("Email sent successfully!");
        } else {
            System.out.println("Error occurred: " + response.getStatus());
            System.out.println(response.getData());
        }
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






