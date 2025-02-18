package Controllers;

import Models.CreateurEvenement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Services.InvestisseurProduitService;
import Models.InvestisseurProduit;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class investController implements Initializable {

    private final InvestisseurProduitService investisseurProduitService = new InvestisseurProduitService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Récupérer la liste des investisseurs de produit
        List<InvestisseurProduit> investisseurProduits = investisseurProduitService.getAll();
        afficherInvestisseurs(investisseurProduits);
    }

    public void afficherInvestisseurs(List<InvestisseurProduit> investisseurProduits) {
        InvestorContainer.getChildren().clear(); // Effacer le contenu actuel

        GridPane gridPaneValides = new GridPane();
        GridPane gridPaneDemandes = new GridPane();

        configurerGridPane(gridPaneValides);
        configurerGridPane(gridPaneDemandes);

        ajouterEnTetes(gridPaneValides);
        ajouterEnTetes(gridPaneDemandes);

        int rowValides = 1, rowDemandes = 1;

        for (InvestisseurProduit investisseur : investisseurProduits) {
            if (investisseur.getCertificat_valide() == 1) {
                ajouterInvestisseurALaGrille(gridPaneValides, investisseur, rowValides, investisseurProduits, true);
                rowValides++;
            } else {
                ajouterInvestisseurALaGrille(gridPaneDemandes, investisseur, rowDemandes, investisseurProduits, false);
                rowDemandes++;
            }
        }

        ScrollPane scrollPaneValides = new ScrollPane(gridPaneValides);
        ScrollPane scrollPaneDemandes = new ScrollPane(gridPaneDemandes);

        InvestorContainer.getChildren().addAll(new Label("Investisseurs Validés"), scrollPaneValides,
                new Label("Demandes en attente"), scrollPaneDemandes);
    }

    private void configurerGridPane(GridPane gridPane) {
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15));
        gridPane.setAlignment(Pos.CENTER);
    }

    private void ajouterEnTetes(GridPane gridPane) {
        String[] headers = {"Nom", "Prénom", "Email", "Nom Entreprise", "Description", "Adresse", "Téléphone", "Action"};

        for (int col = 0; col < headers.length; col++) {
            Label headerLabel = new Label(headers[col]);
            headerLabel.getStyleClass().add("header-label");
            headerLabel.setAlignment(Pos.CENTER);
            headerLabel.setMinWidth(120);
            GridPane.setHalignment(headerLabel, HPos.CENTER);
            gridPane.add(headerLabel, col, 0);
        }
    }

    private void ajouterInvestisseurALaGrille(GridPane gridPane, InvestisseurProduit investisseur, int row, List<InvestisseurProduit> investisseurProduits, boolean estValide) {
        Label nomLabel = new Label(investisseur.getNom());
        Label prenomLabel = new Label(investisseur.getPrenom());
        Label emailLabel = new Label(investisseur.getEmail() != null ? investisseur.getEmail() : "Non renseigné");
        Label entrepriseLabel = new Label(investisseur.getNom_entreprise());
        Label descriptionLabel = new Label(investisseur.getDescription());
        Label adresseLabel = new Label(investisseur.getAdresse());
        Label telephoneLabel = new Label(investisseur.getTelephone());

        Label[] labels = {nomLabel, prenomLabel, emailLabel, entrepriseLabel, descriptionLabel, adresseLabel, telephoneLabel};
        for (Label label : labels) {
            label.setAlignment(Pos.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        gridPane.add(nomLabel, 0, row);
        gridPane.add(prenomLabel, 1, row);
        gridPane.add(emailLabel, 2, row);
        gridPane.add(entrepriseLabel, 3, row);
        gridPane.add(descriptionLabel, 4, row);
        gridPane.add(adresseLabel, 5, row);
        gridPane.add(telephoneLabel, 6, row);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("supprimer-button");
        GridPane.setHalignment(supprimerButton, HPos.CENTER);

        supprimerButton.setOnAction(event -> {
            // Supprimer l'investisseur de produit
            if (investisseurProduitService.deleteInvestisseurProduit(investisseur.getId())) {
                // Retirer l'investisseur de la liste affichée
                investisseurProduits.remove(investisseur);
                afficherInvestisseurs(investisseurProduits);  // Rafraîchir l'affichage
            }
        });

        if (estValide) {
            gridPane.add(supprimerButton, 7, row);
        } else {
            Button validerButton = new Button("Valider");
            validerButton.getStyleClass().add("valider-button");
            GridPane.setHalignment(validerButton, HPos.CENTER);

            validerButton.setOnAction(event -> {
                investisseur.setCertificat_valide((byte) 1);
                if (investisseurProduitService.updateInvestisseurProduit(investisseur)) {
                    afficherInvestisseurs(investisseurProduits);
                }
            });

            HBox actionsBox = new HBox(10, validerButton, supprimerButton);
            actionsBox.setAlignment(Pos.CENTER);
            gridPane.add(actionsBox, 7, row);
        }
    }



    @FXML
    private VBox InvestorContainer;

    @FXML
    private AnchorPane chart;

    @FXML
    private AnchorPane chart1;

    @FXML
    private AnchorPane demandecoachContainer;

    @FXML
    private Button event;

    @FXML
    private Button home;

    @FXML
    private AnchorPane investors;

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

