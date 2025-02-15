

//
//package Controllers;
//
//import Models.EtatEvenement;
//import Models.Evenement;
//import Services.EvenementService;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.stage.FileChooser;
//import java.io.File;
//import java.sql.Blob;
//import java.sql.SQLException;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//
//public class AddEvenement {
//
//    @FXML private TextField idTitle, idDescription, idLieu, idPrix, idCaoMax, idOrganisateur, idType;
//    @FXML private DatePicker idDateD, idDateF;
//    @FXML private ChoiceBox<String> idEtat;
//    @FXML private Label idImgLabel;
//
//    private File imageFile;
//    private EvenementService evenementService = new EvenementService();
//
//    // Initialize ChoiceBox with statuses
//    @FXML
//    public void initialize() {
//        idEtat.getItems().addAll("ACTIF", "EXPIRE");
//        idEtat.setValue("ACTIF");
//    }
//
//    // Handle button click to save the event
//    public void handleButtonClick(ActionEvent event) {
//        String title = idTitle.getText();
//        String description = idDescription.getText();
//        LocalDate dateDebut = idDateD.getValue();
//        LocalDate dateFin = idDateF.getValue();
//        String lieu = idLieu.getText();
//        double prix = Double.parseDouble(idPrix.getText());
//        int capaciteMaximale = Integer.parseInt(idCaoMax.getText());
//        String organisateur = idOrganisateur.getText();
//        String type = idType.getText();
//        String etat = idEtat.getValue();
//
//        // Validate required fields
//        if (title.isEmpty() || description.isEmpty() || dateDebut == null || dateFin == null || lieu.isEmpty()) {
//            showAlert(AlertType.ERROR, "Formulaire incomplet", "Veuillez remplir tous les champs obligatoires.");
//            return;
//        }
//
//        // Convert the image file to byte[] (if selected)
//        byte[] imageBytes = null;
//        if (imageFile != null) {
//            try (FileInputStream fis = new FileInputStream(imageFile)) {
//                imageBytes = fis.readAllBytes();  // Convert image to byte[]
//            } catch (IOException e) {
//                e.printStackTrace();
//                showAlert(AlertType.ERROR, "Erreur d'image", "Erreur lors du traitement de l'image.");
//                return; // Exit if image processing fails
//            }
//        }
//
//        // Create the event object with byte[] for image
//        Evenement evenement = new Evenement(
//                title, description, dateDebut, dateFin, lieu, imageBytes, prix,
//                EtatEvenement.valueOf(etat), type, organisateur, capaciteMaximale
//        );
//
//        // Save the event to the database
//        try {
//            if (evenementService.create(evenement)) {
//                showAlert(AlertType.INFORMATION, "Événement enregistré", "L'événement a été ajouté avec succès.");
//                clearForm();
//            } else {
//                showAlert(AlertType.ERROR, "Erreur", "Impossible d'ajouter l'événement.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(AlertType.ERROR, "Erreur de base de données", "Une erreur est survenue lors de l'enregistrement.");
//        }
//    }
//
//    // Handle image file upload
//    public void handleImageUpload(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
//        imageFile = fileChooser.showOpenDialog(null);
//
//        if (imageFile != null) {
//            idImgLabel.setText(imageFile.getName()); // Display file name
//        } else {
//            idImgLabel.setText("Aucune image sélectionnée");
//        }
//    }
//
//
//    @FXML
//void goToEventList(ActionEvent event) throws IOException {
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_list.fxml"));
//    Parent root = loader.load();
//    idTitle.getScene().setRoot(root);
//
//}
//
//    // Utility method to show alert messages
//    private void showAlert(AlertType type, String title, String message) {
//        Alert alert = new Alert(type);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    // Clear the form after successful event creation
//    private void clearForm() {
//        idTitle.clear();
//        idDescription.clear();
//        idLieu.clear();
//        idPrix.clear();
//        idCaoMax.clear();
//        idOrganisateur.clear();
//        idType.clear();
//        idDateD.setValue(null);
//        idDateF.setValue(null);
//        idEtat.setValue("ACTIF");
//        idImgLabel.setText("Aucune image sélectionnée");
//        imageFile = null;
//    }
//}
//
//
//






























package Controllers;

import Models.EtatEvenement;
import Models.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import java.io.File;

import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

public class AddEvenement {

    @FXML
    private Button event;

    @FXML
    private Button home;

    @FXML
    private Button idButton;

    @FXML
    private Button idButton2;

    @FXML
    private TextField idCaoMax;

    @FXML
    private DatePicker idDateD;

    @FXML
    private DatePicker idDateF;

    @FXML
    private TextArea idDescription;

    @FXML
    private ChoiceBox<String> idEtat;

    @FXML
    private Button idImgButton;

    @FXML
    private Label idImgLabel;

    @FXML
    private TextField idLieu;

    @FXML
    private TextField idOrganisateur;

    @FXML
    private TextField idPrix;

    @FXML
    private TextField idTitle;

    @FXML
    private TextField idType;

    @FXML
    private ImageView isearch;

    @FXML
    private ImageView logout;

    @FXML
    private Button offre;

    @FXML
    private Button parametre;

    @FXML
    private Button produit;

    @FXML
    private Button reclamation;

    @FXML
    private Button seance;

    @FXML
    private TextField search;
    private File imageFile;
    private EvenementService evenementService = new EvenementService();

    // Initialize ChoiceBox with statuses
    @FXML
    public void initialize() {
        idEtat.getItems().addAll("ACTIF", "EXPIRE");
        idEtat.setValue("ACTIF");
    }
//
//    // Handle button click to save the event
//    public void handleButtonClick(ActionEvent event) {
//        String title = idTitle.getText();
//        String description = idDescription.getText();
//        LocalDate dateDebut = idDateD.getValue();
//        LocalDate dateFin = idDateF.getValue();
//        String lieu = idLieu.getText();
//        double prix = Double.parseDouble(idPrix.getText());
//        int capaciteMaximale = Integer.parseInt(idCaoMax.getText());
//        String organisateur = idOrganisateur.getText();
//        String type = idType.getText();
//        String etat = (String) idEtat.getValue();
//
//        // Validate required fields
//        if (title.isEmpty() || description.isEmpty() || dateDebut == null || dateFin == null || lieu.isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Formulaire incomplet", "Veuillez remplir tous les champs obligatoires.");
//            return;
//        }
//
//        // Convert the image file to byte[] (if selected)
//        byte[] imageBytes = null;
//        if (imageFile != null) {
//            try (FileInputStream fis = new FileInputStream(imageFile)) {
//                imageBytes = fis.readAllBytes();  // Convert image to byte[]
//            } catch (IOException e) {
//                e.printStackTrace();
//                showAlert(Alert.AlertType.ERROR, "Erreur d'image", "Erreur lors du traitement de l'image.");
//                return; // Exit if image processing fails
//            }
//        }
//
//        // Create the event object with byte[] for image
//        Evenement evenement = new Evenement(
//                title, description, dateDebut, dateFin, lieu, imageBytes, prix,
//                EtatEvenement.valueOf(etat), type, organisateur, capaciteMaximale
//        );
//
//        // Save the event to the database
//        try {
//            if (evenementService.create(evenement)) {
//                showAlert(Alert.AlertType.INFORMATION, "Événement enregistré", "L'événement a été ajouté avec succès.");
//                clearForm();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter l'événement.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Erreur de base de données", "Une erreur est survenue lors de l'enregistrement.");
//        }
//    }


    public void handleButtonClick(ActionEvent event) {
        try {
            String title = idTitle.getText().trim();
            String description = idDescription.getText().trim();
            LocalDate dateDebut = idDateD.getValue();
            LocalDate dateFin = idDateF.getValue();
            String lieu = idLieu.getText().trim();
            String organisateur = idOrganisateur.getText().trim();
            String type = idType.getText().trim();
            String etat = idEtat.getValue();

            // Vérifier si les champs obligatoires sont vides
            if (title.isEmpty() || description.isEmpty() || dateDebut == null || dateFin == null || lieu.isEmpty() || organisateur.isEmpty() || type.isEmpty()) {
                showAlert(AlertType.ERROR, "Formulaire incomplet", "Tous les champs sont obligatoires.");
                return;
            }

            // Vérifier la longueur des textes
            if (title.length() > 100) {
                showAlert(AlertType.ERROR, "Titre trop long", "Le titre ne doit pas dépasser 100 caractères.");
                return;
            }
            if (description.length() > 500) {
                showAlert(AlertType.ERROR, "Description trop longue", "La description ne doit pas dépasser 500 caractères.");
                return;
            }
            // Vérifier que la date de début n'est pas dans le passé
            if (dateDebut.isBefore(LocalDate.now())) {
                showAlert(AlertType.ERROR, "Date invalide", "La date de début doit être aujourd'hui ou plus tard.");
                return;
            }
            // Vérifier que la date de fin est après la date de début
            if (dateFin.isBefore(dateDebut)) {
                showAlert(AlertType.ERROR, "Erreur de date", "La date de fin doit être après la date de début.");
                return;
            }

            // Vérifier les valeurs numériques
            double prix;
            int capaciteMaximale;
            try {
                prix = Double.parseDouble(idPrix.getText().trim());
                if (prix < 0) {
                    showAlert(AlertType.ERROR, "Prix invalide", "Le prix doit être un nombre positif.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Prix invalide", "Veuillez entrer un prix valide.");
                return;
            }

            try {
                capaciteMaximale = Integer.parseInt(idCaoMax.getText().trim());
                if (capaciteMaximale <= 0) {
                    showAlert(AlertType.ERROR, "Capacité invalide", "La capacité maximale doit être un nombre positif.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Capacité invalide", "Veuillez entrer une capacité valide.");
                return;
            }

            // Vérifier l'état de l'événement
            EtatEvenement etatEnum;
            try {
                etatEnum = EtatEvenement.valueOf(etat);
            } catch (IllegalArgumentException e) {
                showAlert(AlertType.ERROR, "Erreur d'état", "L'état sélectionné est invalide.");
                return;
            }

            // Vérifier l'image
            byte[] imageBytes = null;
            if (imageFile != null) {
                try (FileInputStream fis = new FileInputStream(imageFile)) {
                    imageBytes = fis.readAllBytes();
                } catch (IOException e) {
                    showAlert(AlertType.ERROR, "Erreur d'image", "Impossible de lire l'image sélectionnée.");
                    return;
                }
            }

            // Création de l'événement
            Evenement evenement = new Evenement(
                    title, description, dateDebut, dateFin, lieu, imageBytes, prix,
                    etatEnum, type, organisateur, capaciteMaximale
            );

            // Sauvegarde dans la base de données
            if (evenementService.create(evenement)) {
                showAlert(AlertType.INFORMATION, "Succès", "L'événement a été ajouté avec succès.");
                clearForm();
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Impossible d'ajouter l'événement.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur interne", "Une erreur inattendue s'est produite.");
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


    @FXML
    void goToEventList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Events.fxml"));
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
















//
//
//@FXML
//    void goToEventList(ActionEvent event) {
//
//    }
//
//    @FXML
//    void handleButtonClick(ActionEvent event) {
//
//    }
//
//    @FXML
//    void handleImageUpload(ActionEvent event) {
//
//    }
//
//}
