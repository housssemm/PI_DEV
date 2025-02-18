package Controllers;

import Models.Coach;
import Services.CoachService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoachController implements Initializable {

    private final CoachService coachService = new CoachService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Récupérer la liste des coachs
        List<Coach> coachs = coachService.getAll();

        // Afficher les coachs dans le VBox
        afficherCoachs(coachs);

    }
    public void afficherCoachs(List<Coach> coachs) {

        coachContainer.getChildren().clear(); // Effacer le contenu actuel

        // Grids pour afficher les coachs validés et non validés
        GridPane gridPaneValides = new GridPane();
        GridPane gridPaneDemandes = new GridPane();

        // Configuration des grilles
        configurerGridPane(gridPaneValides);
        configurerGridPane(gridPaneDemandes);

        // Ajouter les en-têtes des colonnes
        ajouterEnTetes(gridPaneValides);
        ajouterEnTetes(gridPaneDemandes);

        int rowValides = 1, rowDemandes = 1;

        for (Coach coach : coachs) {
            if (coach.getCertificat_valide() == 1) {
                ajouterCoachALaGrille(gridPaneValides, coach, rowValides, coachs, true);
                rowValides++;
            } else {
                ajouterCoachALaGrille(gridPaneDemandes, coach, rowDemandes, coachs, false);
                rowDemandes++;
            }
        }

        // ScrollPane pour chaque grille
        ScrollPane scrollPaneValides = new ScrollPane(gridPaneValides);
        ScrollPane scrollPaneDemandes = new ScrollPane(gridPaneDemandes);

        // Ajouter les ScrollPane au VBox
        coachContainer.getChildren().addAll(new Label("Coachs Validés"), scrollPaneValides,
                new Label("Demandes en attente"), scrollPaneDemandes);
    }

    // Configuration du GridPane
    private void configurerGridPane(GridPane gridPane) {
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15));
        gridPane.setAlignment(Pos.CENTER);
    }
    private void ajouterEnTetesAdherents(GridPane gridPane) {
        String[] headers = {"Nom", "Prénom", "Email", "Âge", "Taille", "Poids", "Action"};

        for (int col = 0; col < headers.length; col++) {
            Label headerLabel = new Label(headers[col]);
            headerLabel.getStyleClass().add("header-label");
            headerLabel.setAlignment(Pos.CENTER);
            headerLabel.setMinWidth(120);
            GridPane.setHalignment(headerLabel, HPos.CENTER);
            gridPane.add(headerLabel, col, 0);
        }
    }


    // Ajout des en-têtes de colonnes
    private void ajouterEnTetes(GridPane gridPane) {
        String[] headers = {"Nom", "Prénom", "Email", "Expérience", "Spécialité", "Note", "Action"};

        for (int col = 0; col < headers.length; col++) {
            Label headerLabel = new Label(headers[col]);
            headerLabel.getStyleClass().add("header-label");
            headerLabel.setAlignment(Pos.CENTER);
            headerLabel.setMinWidth(120);
            GridPane.setHalignment(headerLabel, HPos.CENTER);
            gridPane.add(headerLabel, col, 0);
        }
    }

    // Ajout d'un coach dans la grille
    private void ajouterCoachALaGrille(GridPane gridPane, Coach coach, int row, List<Coach> coachs, boolean estValide) {
        Label nomLabel = new Label(coach.getNom());
        Label prenomLabel = new Label(coach.getPrenom());
        Label emailLabel = new Label(coach.getEmail() != null ? coach.getEmail() : "Non renseigné");
        Label experienceLabel = new Label(String.valueOf(coach.getAnnee_experience()));
        Label specialiteLabel = new Label(coach.getSpecialite().name());
        Label noteLabel = new Label(String.valueOf(coach.getNote()));

        Label[] labels = {nomLabel, prenomLabel, emailLabel, experienceLabel, specialiteLabel, noteLabel};
        for (Label label : labels) {
            label.setAlignment(Pos.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        gridPane.add(nomLabel, 0, row);
        gridPane.add(prenomLabel, 1, row);
        gridPane.add(emailLabel, 2, row);
        gridPane.add(experienceLabel, 3, row);
        gridPane.add(specialiteLabel, 4, row);
        gridPane.add(noteLabel, 5, row);

        // Bouton Supprimer
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("supprimer-button");
        GridPane.setHalignment(supprimerButton, HPos.CENTER);


        supprimerButton.setOnAction(event -> {
            // Supprimer le coach de la base de données
            if (coachService.deleteCoach(coach.getId())) {
                // Retirer le coach de la liste affichée
                coachs.remove(coach);
                afficherCoachs(coachs);  // Rafraîchir l'affichage des coachs après suppression
            }
        });



        if (estValide) {
            gridPane.add(supprimerButton, 6, row);
        } else {
            // Bouton Valider
            Button validerButton = new Button("Valider");
            validerButton.getStyleClass().add("valider-button");
            GridPane.setHalignment(validerButton, HPos.CENTER);


            validerButton.setOnAction(event -> {
                coach.setCertificat_valide((byte) 1);  // Modifier la valeur du champ certificat_valide en mémoire

                // Utiliser la méthode updateCoach pour mettre à jour la base de données
                if (coachService.updateCoach(coach)) {
                    afficherCoachs(coachs);  // Rafraîchir l'affichage des coachs
                }
            });


            // Ajouter boutons dans la grille
            HBox actionsBox = new HBox(10, validerButton, supprimerButton);
            actionsBox.setAlignment(Pos.CENTER);
            gridPane.add(actionsBox, 6, row);
        }
    }

    @FXML
    private VBox coachContainer;

    @FXML
    private AnchorPane coachs;

    @FXML
    private AnchorPane demandecoachContainer;

    @FXML
    private Button event;

    @FXML
    private Button home;

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

}
