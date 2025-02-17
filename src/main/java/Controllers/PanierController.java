package Controllers;

import Models.Categorie;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import Services.categorieService;
import Services.produitService;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


import java.io.File;
import java.util.List;


public class PanierController {
    @FXML
    private GridPane gridPaneCateg;
    @FXML
    private ScrollPane scrollPaneCateg;
    private categorieService categsService = new categorieService();
    //private produitService produitService = new produitService();

    @FXML
    void initializeCategories() throws Exception {
        if (gridPaneCateg == null) {
            System.out.println("GridPane is null! Check FXML configuration.");
        } else {
            System.out.println("GridPane initialized successfully.");
        }
        // Configuration du ScrollPane
        scrollPaneCateg.setFitToHeight(true);
        scrollPaneCateg.setFitToWidth(false);

        // Ajouter un espacement entre les éléments du GridPane
        gridPaneCateg.setHgap(20); // Espacement horizontal entre les colonnes
        gridPaneCateg.setAlignment(Pos.CENTER); // Centrer le contenu dans le GridPane

        // Récupérer tous les produits
        List<Categorie> categories = categsService.getAll(); // Remplacez par la méthode correcte pour récupérer vos produits

        // Nettoyer le GridPane
        gridPaneCateg.getChildren().clear();

        int columnIndex = 0; // Index pour les colonnes
        for (Categorie categ : categories) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(30); // Taille de l'image (ajustée)
            imageView.setFitHeight(30); // Taille de l'image (ajustée)

            // Vérifier si l'image existe et l'afficher
            File imageFile = new File(categ.getImage());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            }

            // Ajouter les informations du produit dans des Texts
            Text nomText = new Text("Nom : " + categ.getNom());

            nomText.setWrappingWidth(30); // Ajuster cette valeur selon la largeur de votre carte


            // Appliquer l'alignement à gauche directement sur les Texts
            nomText.setTextAlignment(TextAlignment.CENTER);

            // Créer une VBox pour chaque produit
            VBox contentBox = new VBox(10); // Espacement vertical de 10 pixels
            contentBox.setStyle("-fx-padding: 15; -fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-background-radius: 10;");
            contentBox.setAlignment(Pos.CENTER);

            // Fixer la largeur des cartes
            contentBox.setPrefWidth(150); // Largeur fixe pour toutes les cartes
            contentBox.setMinHeight(150); // Fixer la hauteur minimale des cartes pour qu'elle reste inchangée même si le texte dépasse
            // Ajouter l'image et les informations dans la VBox
            contentBox.getChildren().addAll(imageView, nomText);
            // Ajouter la VBox dans le GridPane
            gridPaneCateg.add(contentBox, columnIndex,0);
            columnIndex++; // Passer à la colonne suivante
        }
        // Définir la largeur totale du GridPane en fonction du nombre de produits
        double largeurCarte = 160; // Largeur approximative de chaque carte + marges
        gridPaneCateg.setPrefWidth(categories.size() * largeurCarte);
    }

}
