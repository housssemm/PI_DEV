package Controller;

import Models.Seance;
import Models.Type;
import Services.SeanceService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Date;
import java.sql.Time;

public class popupSeanceController {

    @FXML
    private TextArea fieldDescription;

    @FXML
    private TextField fieldLien;

    @FXML
    private ChoiceBox<Type> fieldType;

    @FXML
    private TextField field_HeureDebut;

    @FXML
    private TextField field_Heurefin;

    @FXML
    private TextField field_adherent_Id;

    @FXML
    private TextField fieldtitre;

    @FXML
    private DatePicker field_Date;


    private int idCoach; // Stocker idCoach ici
    private int idPlanning; // Stocker idPlanning ici

    @FXML
    public void initialize() {
        fieldType.getItems().setAll(Type.values());
    }
    @FXML
    void ajouterSeance() {
        if (!validerChamps()) {
            return;
        }
        try {
            // Check if the date is in the past
            Date currentDate = new Date(System.currentTimeMillis()); // Current date from system
            Date selectedDate = Date.valueOf(field_Date.getValue());

            if (selectedDate.before(currentDate)) {
                afficherAlerte("Erreur de date", "La date de la séance ne peut pas être dans le passé.", Alert.AlertType.ERROR);
                return;
            }

            // Check if the start time is before the end time
            Time heureDebut = Time.valueOf(field_HeureDebut.getText() + ":00");
            Time heureFin = Time.valueOf(field_Heurefin.getText() + ":00");

            if (heureDebut.after(heureFin) || heureDebut.equals(heureFin)) {
                afficherAlerte("Erreur de saisie", "L'heure de début doit être inférieure à l'heure de fin.", Alert.AlertType.ERROR);
                return;
            }

            SeanceService sc = new SeanceService();
            Seance s1 = new Seance(
                    fieldtitre.getText(),
                    fieldDescription.getText(),
                    selectedDate, // Use the selected date
                    idCoach,
                    Integer.parseInt(field_adherent_Id.getText()),
                    fieldType.getValue(),
                    fieldLien.getText(),
                    idPlanning,
                    heureDebut,
                    heureFin
            );

            sc.create(s1);
            fermerFenetre();
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue lors de l'ajout de la séance.", Alert.AlertType.ERROR);
        }
    }
    private boolean validerChamps() {
        if (fieldtitre.getText().isEmpty() || fieldDescription.getText().isEmpty() || fieldLien.getText().isEmpty()
                || field_HeureDebut.getText().isEmpty()
                || field_Heurefin.getText().isEmpty() || field_adherent_Id.getText().isEmpty()
                || field_Date.getValue() == null || fieldType.getValue() == null) {
            afficherAlerte("Champs vides", "Tous les champs doivent être remplis.", Alert.AlertType.WARNING);
            return false;
        }

        if (!estEntier(field_adherent_Id.getText())) {
            afficherAlerte("Format invalide", "L'ID de l'adhérent doit être un nombre entier.", Alert.AlertType.ERROR);
            return false;
        }

        if (!estHeureValide(field_HeureDebut.getText())) {
            afficherAlerte("Format heure invalide", "L'heure de début doit être au format HH:mm.", Alert.AlertType.ERROR);
            return false;
        }

        if (!estHeureValide(field_Heurefin.getText())) {
            afficherAlerte("Format heure invalide", "L'heure de fin doit être au format HH:mm.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }


    private boolean estEntier(String valeur) {
        try {
            Integer.parseInt(valeur);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // si une chaîne de caractères représente une heure au format HH:mm,
    // où l'heure est comprise entre 00 et 23 et les minutes entre 00 et 59.
    private boolean estHeureValide(String heure) {
        String regex = "^([01][0-9]|2[0-3]):([0-5][0-9])$";
        return heure.matches(regex);
    }

    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void initData(int idCoach, int idPlanning) {
        this.idCoach = idCoach;
        this.idPlanning = idPlanning;
        System.out.println("initData called. Coach ID: " + idCoach + ", Planning ID: " + idPlanning);
    }
    private void fermerFenetre() {
        Stage stage = (Stage) fieldtitre.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void annulerSeance() {
        fermerFenetre();
    }
}
