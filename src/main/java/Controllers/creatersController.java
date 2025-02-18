package Controllers;

import Models.CreateurEvenement;
import Services.CreateurEvenementService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class creatersController implements Initializable {

    private final CreateurEvenementService createurEvenementService = new CreateurEvenementService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Récupérer la liste des créateurs d'événements
        List<CreateurEvenement> createurEvenements = createurEvenementService.getAll();
        afficherCreateursEvenements(createurEvenements);
    }

    public void afficherCreateursEvenements(List<CreateurEvenement> createurEvenements) {
        EventersContainer.getChildren().clear(); // Effacer le contenu actuel

        GridPane gridPaneValides = new GridPane();
        GridPane gridPaneDemandes = new GridPane();

        configurerGridPane(gridPaneValides);
        configurerGridPane(gridPaneDemandes);

        ajouterEnTetes(gridPaneValides);
        ajouterEnTetes(gridPaneDemandes);

        int rowValides = 1, rowDemandes = 1;

        for (CreateurEvenement createur : createurEvenements) {
            if (createur.getCertificat_valide() == 1) {
                ajouterCreateurALaGrille(gridPaneValides, createur, rowValides, createurEvenements, true);
                rowValides++;
            } else {
                ajouterCreateurALaGrille(gridPaneDemandes, createur, rowDemandes, createurEvenements, false);
                rowDemandes++;
            }
        }

        ScrollPane scrollPaneValides = new ScrollPane(gridPaneValides);
        ScrollPane scrollPaneDemandes = new ScrollPane(gridPaneDemandes);

        EventersContainer.getChildren().addAll(new Label("Createurs Validés"), scrollPaneValides,
                new Label("Demandes en attente"), scrollPaneDemandes);
    }

    private void configurerGridPane(GridPane gridPane) {
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15));
        gridPane.setAlignment(Pos.CENTER);
    }

    private void ajouterEnTetes(GridPane gridPane) {
        String[] headers = {"Nom", "Prénom", "Email", "Organisation", "Description", "Adresse", "Téléphone", "Action"};

        for (int col = 0; col < headers.length; col++) {
            Label headerLabel = new Label(headers[col]);
            headerLabel.getStyleClass().add("header-label");
            headerLabel.setAlignment(Pos.CENTER);
            headerLabel.setMinWidth(120);
            GridPane.setHalignment(headerLabel, HPos.CENTER);
            gridPane.add(headerLabel, col, 0);
        }
    }

    private void ajouterCreateurALaGrille(GridPane gridPane, CreateurEvenement createur, int row, List<CreateurEvenement> createurEvenements, boolean estValide) {
        Label nomLabel = new Label(createur.getNom());
        Label prenomLabel = new Label(createur.getPrenom());
        Label emailLabel = new Label(createur.getEmail() != null ? createur.getEmail() : "Non renseigné");
        Label organisationLabel = new Label(createur.getNom_organisation());
        Label descriptionLabel = new Label(createur.getDescription());
        Label adresseLabel = new Label(createur.getAdresse());
        Label telephoneLabel = new Label(createur.getTelephone());

        Label[] labels = {nomLabel, prenomLabel, emailLabel, organisationLabel, descriptionLabel, adresseLabel, telephoneLabel};
        for (Label label : labels) {
            label.setAlignment(Pos.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        gridPane.add(nomLabel, 0, row);
        gridPane.add(prenomLabel, 1, row);
        gridPane.add(emailLabel, 2, row);
        gridPane.add(organisationLabel, 3, row);
        gridPane.add(descriptionLabel, 4, row);
        gridPane.add(adresseLabel, 5, row);
        gridPane.add(telephoneLabel, 6, row);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("supprimer-button");
        GridPane.setHalignment(supprimerButton, HPos.CENTER);

        supprimerButton.setOnAction(event -> {
            // Supprimer le créateur d'événement
            if (createurEvenementService.deleteCreateurEvenement(createur.getId())) {
                // Retirer le créateur de la liste affichée
                createurEvenements.remove(createur);
                afficherCreateursEvenements(createurEvenements);  // Rafraîchir l'affichage
            }
        });

        if (estValide) {
            gridPane.add(supprimerButton, 7, row);
        } else {
            Button validerButton = new Button("Valider");
            validerButton.getStyleClass().add("valider-button");
            GridPane.setHalignment(validerButton, HPos.CENTER);

            validerButton.setOnAction(event -> {
                createur.setCertificat_valide((byte) 1);
                if (createurEvenementService.updateCreateurEvenement(createur)) {
                    afficherCreateursEvenements(createurEvenements);
                }
            });

            HBox actionsBox = new HBox(10, validerButton, supprimerButton);
            actionsBox.setAlignment(Pos.CENTER);
            gridPane.add(actionsBox, 7, row);
        }
    }



@FXML
    private VBox EventersContainer;

    @FXML
    private AnchorPane chart;

    @FXML
    private AnchorPane chart1;

    @FXML
    private AnchorPane demandecoachContainer;

    @FXML
    private Button event;

    @FXML
    private AnchorPane eventers;

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

    @FXML
    private AnchorPane upchart;

}
