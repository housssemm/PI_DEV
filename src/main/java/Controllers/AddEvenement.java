


package Controllers;

import Models.EtatEvenement;
import Models.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddEvenement {

    @FXML private TextField idTitle, idDescription, idLieu, idPrix, idCaoMax, idOrganisateur, idType;
    @FXML private DatePicker idDateD, idDateF;
    @FXML private ChoiceBox<String> idEtat;
    @FXML private Label idImgLabel;

    private File imageFile;
    private EvenementService evenementService = new EvenementService();

    // Initialize ChoiceBox with statuses
    @FXML
    public void initialize() {
        idEtat.getItems().addAll("ACTIF", "EXPIRE");
        idEtat.setValue("ACTIF");
    }

    // Handle button click to save the event
    public void handleButtonClick(ActionEvent event) {
        String title = idTitle.getText();
        String description = idDescription.getText();
        LocalDate dateDebut = idDateD.getValue();
        LocalDate dateFin = idDateF.getValue();
        String lieu = idLieu.getText();
        double prix = Double.parseDouble(idPrix.getText());
        int capaciteMaximale = Integer.parseInt(idCaoMax.getText());
        String organisateur = idOrganisateur.getText();
        String type = idType.getText();
        String etat = idEtat.getValue();

        // Validate required fields
        if (title.isEmpty() || description.isEmpty() || dateDebut == null || dateFin == null || lieu.isEmpty()) {
            showAlert(AlertType.ERROR, "Formulaire incomplet", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Convert the image file to byte[] (if selected)
        byte[] imageBytes = null;
        if (imageFile != null) {
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                imageBytes = fis.readAllBytes();  // Convert image to byte[]
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erreur d'image", "Erreur lors du traitement de l'image.");
                return; // Exit if image processing fails
            }
        }

        // Create the event object with byte[] for image
        Evenement evenement = new Evenement(
                title, description, dateDebut, dateFin, lieu, imageBytes, prix,
                EtatEvenement.valueOf(etat), type, organisateur, capaciteMaximale
        );

        // Save the event to the database
        try {
            if (evenementService.create(evenement)) {
                showAlert(AlertType.INFORMATION, "Événement enregistré", "L'événement a été ajouté avec succès.");
                clearForm();
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Impossible d'ajouter l'événement.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de base de données", "Une erreur est survenue lors de l'enregistrement.");
        }
    }

    // Handle image file upload
    public void handleImageUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        imageFile = fileChooser.showOpenDialog(null);

        if (imageFile != null) {
            idImgLabel.setText(imageFile.getName()); // Display file name
        } else {
            idImgLabel.setText("Aucune image sélectionnée");
        }
    }

    // Display events (called by another button to show events list)
//    public void Afficher(ActionEvent event) {
//        try {
//            // Assuming an 'EvenementService' method to fetch all events
//            List<Evenement> evenements = evenementService.getAll();
//            if (evenements.isEmpty()) {
//                showAlert(AlertType.INFORMATION, "Aucun événement", "Aucun événement à afficher.");
//            } else {
//                // Handle displaying the list of events (could open another window or list)
//                // Example: Pass the event list to another scene or table view
//                System.out.println(evenements);  // Debug output for the event list
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(AlertType.ERROR, "Erreur de récupération", "Une erreur est survenue lors de la récupération des événements.");
//        }
//    }


    @FXML
void Afficher(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_list.fxml"));
    Parent root = loader.load();
    idTitle.getScene().setRoot(root);

}

    // Utility method to show alert messages
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear the form after successful event creation
    private void clearForm() {
        idTitle.clear();
        idDescription.clear();
        idLieu.clear();
        idPrix.clear();
        idCaoMax.clear();
        idOrganisateur.clear();
        idType.clear();
        idDateD.setValue(null);
        idDateF.setValue(null);
        idEtat.setValue("ACTIF");
        idImgLabel.setText("Aucune image sélectionnée");
        imageFile = null;
    }
}
