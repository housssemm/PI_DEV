package Controllers;

import Models.Categorie;
import Models.produit;
import Services.categorieService;
import Services.produitService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

import java.io.File;
import java.util.List;

public class PanierController {
    @FXML
    private GridPane gridProd;
    @FXML
    private GridPane gridPane;  // Référence au GridPane dans le FXML
    @FXML
    private ScrollPane scrollPane; // Référence à ton ScrollPane dans le FXML
    @FXML
    private ScrollPane scrollProd;

    private categorieService categService = new categorieService();
    private produitService prodService = new produitService();

    @FXML
    public void initialize() throws Exception {
        // Obtenir les catégories depuis le service
        List<Categorie> categories = categService.getAll();

        int col = 0;  // Compteur pour les colonnes du GridPane

        // Créer les cartes pour chaque catégorie et les ajouter dans le GridPane
        for (Categorie categorie : categories) {
            // Créer un texte pour le nom de la catégorie
            Text text = new Text(categorie.getNom());
            text.setFont(Font.font("Arial", 12)); // Réduire la taille du texte

            // Créer une image pour la carte
            Image image = new Image("file:" + categorie.getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50); // Ajuster la taille de l'image (taille identique)
            imageView.setFitWidth(50);  // Ajuster la taille de l'image (taille identique)

            // Créer un VBox pour chaque carte (texte en dessous de l'image)
            VBox card = new VBox(10, imageView, text); // Espacement de 10 entre l'image et le texte
            card.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 10;");
            card.setPrefWidth(40);  // Largeur fixe pour chaque carte
            card.setPrefHeight(40); // Hauteur fixe pour chaque carte
            card.setAlignment(Pos.CENTER); // Centrer les éléments dans la carte

            card.setOnMouseEntered(e -> card.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #FFA500;"));
            card.setOnMouseExited(e -> card.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: transparent;"));

            // Ajouter un EventHandler pour le clic (afficher les produits de cette catégorie)
            card.setOnMouseClicked(e -> {
                try {
                    showProductsForCategory(categorie);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            // Ajouter chaque carte dans le GridPane à la position (col, 0)
            gridPane.add(card, col, 0);  // Toujours dans la première ligne (row = 0)

            // Incrémenter la colonne
            col++;
        }
        gridPane.setHgap(10);
        // Mettre le GridPane dans le ScrollPane
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Toujours afficher la barre de défilement horizontale

        // Définir la largeur totale du GridPane en fonction du nombre de produits
        double largeurCarte = 20; // Largeur approximative de chaque carte + marges
        gridPane.setPrefWidth(categories.size() * largeurCarte); // Utiliser gridPane ici
    }

    private void showProductsForCategory(Categorie categorie) throws Exception {
        List<produit> produits = prodService.getProduitsByCategorie(categorie.getId());

        // Réinitialiser le GridPane pour afficher de nouveaux produits
        gridProd.getChildren().clear();
        int row = 0;  // Variable pour gérer les lignes dans le GridPane
        int col = 0;  // Variable pour gérer les colonnes dans le GridPane

        // Espacement entre les cartes
        gridProd.setHgap(10);
        gridProd.setVgap(10);

        // Dimensions fixes des cartes
        double largeurCarte = 200;  // Largeur des cartes
        double hauteurCarte = 200;  // Hauteur des cartes

        for (produit productItem : produits) {
            // Créer une carte pour chaque produit
            VBox productCard = new VBox(10);  // Espacement interne
            productCard.setMinWidth(largeurCarte);
            productCard.setMaxWidth(largeurCarte);
            productCard.setPrefWidth(largeurCarte);
            productCard.setMinHeight(hauteurCarte);
            productCard.setMaxHeight(hauteurCarte);
            productCard.setPrefHeight(hauteurCarte);

            // Créer une image pour le produit
            ImageView imageView;
            File file = new File(productItem.getImage());

            if (file.exists()) {
                // Charger l'image du produit
                Image productImage = new Image("file:" + productItem.getImage());
                imageView = new ImageView(productImage);
                imageView.setFitHeight(80);  // Ajuster la taille de l'image
                imageView.setFitWidth(100);  // Ajuster la taille de l'image

                // Créer un texte pour le nom du produit
                Text productName = new Text(productItem.getNom());
                productName.setFont(Font.font("Arial", 14));  // Taille de la police

                // Créer un texte pour le prix du produit
                Text productPrice = new Text("Prix: " + productItem.getPrix() + " TND");
                productPrice.setFont(Font.font("Arial", 12));

                // Créer un bouton "Ajouter au panier" avec une icône
                Button addToCartButton = new Button("Ajouter au panier");
                addToCartButton.setStyle("-fx-background-color: #F58400; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5;-fx-font-weight: bold;");

                // Ajouter un gestionnaire d'événement pour le bouton
                addToCartButton.setOnAction(e -> {
                    System.out.println("Produit ajouté au panier : " + productItem.getNom());
                    // Vous pouvez appeler une méthode pour ajouter le produit au panier ici
                });

                // Ajouter les composants à la carte
                productCard.getChildren().addAll(imageView, productName, productPrice, addToCartButton);
                productCard.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 10;");
                productCard.setAlignment(Pos.CENTER); // Centrer les éléments dans la carte

                // Ajouter la carte dans le GridPane
                gridProd.add(productCard, col, row);

                // Incrémenter la colonne
                col++;
                // Si le nombre de colonnes atteint 3, passer à la ligne suivante
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }
        // Calculer la hauteur totale du GridPane en fonction du nombre de lignes
        double totalHeight = (row + 1) * (hauteurCarte + gridProd.getVgap());
        gridProd.setPrefHeight(totalHeight);
        scrollProd.setContent(gridProd);
        scrollProd.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
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
}
