package Controllers;

import Models.Planning;
import Services.PlanningService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ajoutplanningController {

    @FXML
    private TextField idcoachplan, titreplan, tarifplan;
    @FXML
    private VBox planningVBox;

    private HBox headerRow;

    @FXML
    public void initialize() {
        setupHeader();
    }


    @FXML
    void ajouterPlanning() {
        if (!validerChamps()) {
            return;
        }

        PlanningService ps = new PlanningService();
        int idCoach = Integer.parseInt(idcoachplan.getText());

        // Vérifier si le coach a déjà un planning
        if (ps.getPlanningByCoachId(idCoach) != null) {
            afficherAlerte("Erreur", "Vous avez déjà un planning et ne pouvez pas en ajouter un autre.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Planning p1 = new Planning(idCoach, titreplan.getText(), Double.parseDouble(tarifplan.getText()));
            ps.create(p1);
            AffichagePlanning(p1);
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue lors de l'ajout du planning.", Alert.AlertType.ERROR);
        }
    }

    private boolean validerChamps() {
        if (idcoachplan.getText().isEmpty() || titreplan.getText().isEmpty() || tarifplan.getText().isEmpty()) {
            afficherAlerte("Champs vides", "Tous les champs doivent être remplis.", Alert.AlertType.WARNING);
            return false;
        }

        if (!estEntier(idcoachplan.getText())) {
            afficherAlerte("Format invalide", "L'ID du coach doit être un nombre entier.", Alert.AlertType.ERROR);
            return false;
        }

        if (!estDoublePositif(tarifplan.getText())) {
            afficherAlerte("Format invalide", "Le tarif doit être un nombre positif valide.", Alert.AlertType.ERROR);
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

    private boolean estDoublePositif(String valeur) {
        try {
            double d = Double.parseDouble(valeur);
            return d > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void AffichagePlanning(Planning p) {
        HBox planningRow = new HBox(20);
        planningRow.setAlignment(Pos.CENTER);
        planningRow.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 15px;");

        Label titreLabel = new Label(p.getTitre());
        Label tarifLabel = new Label(p.getTarif() + " Dt");
        titreLabel.setStyle("-fx-font-size: 14px;");
        tarifLabel.setStyle("-fx-font-size: 14px;");

        Button modifyButton = new Button("Modifier");
        modifyButton.setOnAction(e -> {
            try {
                openModifierPopUp(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        planningRow.getChildren().addAll(titreLabel, new Label("|"), tarifLabel, new Label("|"), modifyButton);
        planningVBox.getChildren().add(planningRow);
    }

    public void mettreAJourAffichage(Planning planning) {
        planningVBox.getChildren().clear();
        setupHeader();
        AffichagePlanning(planning);
    }

    private void openModifierPopUp(Planning planning) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/popUpModifierPlanning.fxml"));
        DialogPane dialogPane = loader.load();

        popupModifierPlanController popUpController = loader.getController();
        popUpController.initData(planning, this);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Modifier Planning");
        dialog.showAndWait();
    }

    public void setupHeader() {
        headerRow = new HBox(20);
        headerRow.setAlignment(Pos.CENTER);
        headerRow.setStyle("-fx-background-color:  #f58400; -fx-padding:10px; -fx-border-color: black; -fx-border-width: 1px;");

        Label titreHeader = new Label("Titre");
        Label tarifHeader = new Label("Tarif");
        Label actionsHeader = new Label("Actions");

        titreHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        tarifHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        actionsHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        headerRow.getChildren().addAll(titreHeader, new Label("|"), tarifHeader, new Label("|"), actionsHeader);
        planningVBox.getChildren().add(headerRow);
    }

    @FXML
    void consulterplan(ActionEvent event) throws IOException {
        PlanningService ps = new PlanningService();
        int idCoach = Integer.parseInt(idcoachplan.getText());

        // Vérifier si le coach a un planning
        Planning planning = ps.getPlanningByCoachId(idCoach);
        if (planning == null) {
            afficherAlerte("Accès refusé", "Vous n'avez pas encore de planning. Veuillez en créer un d'abord.", Alert.AlertType.WARNING);
            return;
        }

        // Récupérer l'ID du planning depuis l'objet 'planning'
        int idPlanning = planning.getIdPlanning();  // Assurez-vous que votre modèle 'Planning' a cette méthode.

        // Charger le fichier FXML de planning
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/planning.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur de la nouvelle scène
        planningController controller = loader.getController();

        // Passer l'ID du coach et l'ID du planning au contrôleur
        controller.initData(idCoach, idPlanning);

        // Créer une nouvelle scène et l'afficher
        Stage stage = new Stage();
        stage.setTitle("Consulter Planning");
        stage.setScene(new Scene(root));
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
