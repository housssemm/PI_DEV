package Controllers;

import Models.OffreCoach;
import Models.Etato;
import Services.CreateurEvenementService;
import Services.OffreCoachService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.sql.Date;

public class AddOffreCoach {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dureeValiditeField;

    @FXML
    private ChoiceBox<String> etatField;

    @FXML
    private TextField idCoachField;

    @FXML
    private TextField nouveauTarifField;

    @FXML
    private TextField reservationMaxField;

    private OffreCoachService offreCoachService = new OffreCoachService();

    @FXML
    public void initialize() {
        etatField.getItems().addAll("ACTIF", "INACTIF");
        etatField.setValue("ACTIF");
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        try {
            String nom = nomField.getText().trim();
            String description = descriptionField.getText().trim();
            LocalDate dureeValidite = dureeValiditeField.getValue();
            String etat = etatField.getValue();

            // Vérifier si les champs obligatoires sont vides
            if (nom.isEmpty() || description.isEmpty() || dureeValidite == null || idCoachField.getText().trim().isEmpty() || nouveauTarifField.getText().trim().isEmpty() || reservationMaxField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Formulaire incomplet", "Tous les champs sont obligatoires.");
                return;
            }

            // Vérifier la longueur des textes
            if (nom.length() > 100) {
                showAlert(Alert.AlertType.ERROR, "Nom trop long", "Le nom ne doit pas dépasser 100 caractères.");
                return;
            }
            if (description.length() > 500) {
                showAlert(Alert.AlertType.ERROR, "Description trop longue", "La description ne doit pas dépasser 500 caractères.");
                return;
            }

            // Vérifier que la date de validité n'est pas dans le passé
            if (dureeValidite.isBefore(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Date invalide", "La date de validité doit être aujourd'hui ou plus tard.");
                return;
            }

            // Vérifier les valeurs numériques
            int idCoach;
            double nouveauTarif;
            int reservationMax;
            try {
                idCoach = Integer.parseInt(idCoachField.getText().trim());
                if (idCoach <= 0) {
                    showAlert(Alert.AlertType.ERROR, "ID Coach invalide", "L'ID Coach doit être un nombre positif.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "ID Coach invalide", "Veuillez entrer un ID Coach valide.");
                return;
            }

            try {
                nouveauTarif = Double.parseDouble(nouveauTarifField.getText().trim());
                if (nouveauTarif < 0) {
                    showAlert(Alert.AlertType.ERROR, "Tarif invalide", "Le tarif doit être un nombre positif.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Tarif invalide", "Veuillez entrer un tarif valide.");
                return;
            }

            try {
                reservationMax = Integer.parseInt(reservationMaxField.getText().trim());
                if (reservationMax <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Capacité invalide", "La capacité maximale doit être un nombre positif.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Capacité invalide", "Veuillez entrer une capacité valide.");
                return;
            }

            // Vérifier l'état de l'offre
            Etato etatEnum;
            try {
                etatEnum = Etato.valueOf(etat);
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur d'état", "L'état sélectionné est invalide.");
                return;
            }

            // Création de l'offre
            OffreCoach offreCoach = new OffreCoach(0, nom, description, Date.valueOf(dureeValidite), etatEnum, idCoach, nouveauTarif, 0, reservationMax);
            offreCoachService.create(offreCoach);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre Coach ajoutée avec succès.");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'offre Coach.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        // Logique pour annuler l'opération et revenir à la page précédente
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear the form after successful offer creation
    private void clearForm() {
        nomField.clear();
        descriptionField.clear();
        dureeValiditeField.setValue(null);
        etatField.setValue("ACTIF");
        idCoachField.clear();
        nouveauTarifField.clear();
        reservationMaxField.clear();
    }

    //ROOT
    private CreateurEvenementService createurEvenementService = new CreateurEvenementService();
    @FXML
    void GoToEvent(ActionEvent actionEvent) {
        int id = Session.getInstance().getCurrentUser().getId();
        String path = "";

        try {
            if (createurEvenementService.isCreateurEvenement(id)) {
                path = "/AddEvenement.fxml";
            } else {
                path = "/Events.fxml";
            }

            // Now load the determined path
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ((Node) actionEvent.getSource()).getScene().setRoot(root);

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