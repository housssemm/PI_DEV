package Controllers;

import Models.*;
import Services.*;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PanierController {
    @FXML
    private GridPane gridProd;
    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollProd;
    @FXML
    private VBox cartBox;

    private categorieService categService = new categorieService();
    private produitService prodService = new produitService();
    private PanierProduitService paniProdService = new PanierProduitService();
    private produit produitSelectionne;
    private static PanierController instance;

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

            // Charger l'image à partir du classpath
            InputStream imageStream = getClass().getResourceAsStream("/img/" + categorie.getImage());
            Image image;
            image = new Image(imageStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50); // Ajuster la taille de l'image (taille identique)
            imageView.setFitWidth(50);  // Ajuster la taille de l'image (taille identique)

            // Créer un VBox pour chaque carte (texte en dessous de l'image)
            VBox card = new VBox(10, imageView, text); // Espacement de 10 entre l'image et le texte
            card.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 10;-fx-background-color: white;");
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
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Définir la largeur totale du GridPane en fonction du nombre de produits
        double largeurCarte = 120; // Largeur approximative de chaque carte + marges
        gridPane.setPrefWidth(categories.size() * largeurCarte);
    }
    private void showProductDetailsPopup(produit productItem) {
        try {
            // Charger le fichier FXML du popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopUpDetailsProduit.fxml"));
            AnchorPane popupLayout = loader.load();
            String etatString = productItem.getEtat().toString();
            // Récupérer le nom de la catégorie via l'idCategorie
            categorieService categorieService = new categorieService();
            String nomCategorie = categorieService.getNomCategorieById(productItem.getCategorieId());
            // Obtenir le contrôleur associé au fichier FXML
            PopUpDetailsProduitController controller = loader.getController();

            controller.setProduitSelectionne(productItem);
            // Configurer les détails du produit dans le popup
            controller.setProductDetails(
                    productItem.getNom(),
                    nomCategorie,
                    productItem.getDescription(),
                    etatString,
                    productItem.getPrix(),
                    "/img/" + productItem.getImage(),
                    productItem.getQuantite()
            );
            // Créer une nouvelle fenêtre pour le popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale
            popupStage.setTitle("Détails du Produit");

            // Configurer la scène avec le layout du popup
            Scene popupScene = new Scene(popupLayout, 650, 400);
            popupStage.setScene(popupScene);

            // Afficher le popup
            popupStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showProductsForCategory(Categorie categorie) throws Exception {
        List<produit> produits = prodService.getProduitsByCategorie(categorie.getId());

        // Réinitialiser le GridPane pour afficher de nouveaux produits
        gridProd.getChildren().clear();
        int row = 0;  // Variable pour gérer les lignes dans le GridPane
        int col = 0;  // Variable pour gérer les colonnes dans le GridPane

        // Espacement entre les cartes
        gridProd.setHgap(0);
        gridProd.setVgap(10);

        // Dimensions fixes des cartes
        double largeurCarte = 210;  // Largeur des cartes
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

            // Charger l'image à partir du classpath
                InputStream imageStream = getClass().getResourceAsStream("/img/" + productItem.getImage());
                Image image;
                image = new Image(imageStream);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(80);  // Ajuster la taille de l'image
                imageView.setFitWidth(100);  // Ajuster la taille de l'image

                // Créer un texte pour le nom du produit
                Text productName = new Text(productItem.getNom());
                productName.setFont(Font.font("Arial", 14));  // Taille de la police

                // Créer un texte pour le prix du produit
                Text productPrice = new Text("Prix: " + productItem.getPrix() + " TND");
                productPrice.setFont(Font.font("Arial", 12));

                // Créer un bouton "Ajouter au panier" avec une icône
                Button addToCartButton = new Button("Voir details");
                addToCartButton.setStyle("-fx-background-color: #F58400; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5;-fx-font-weight: bold;");

                addToCartButton.setOnAction(e -> {
                        produitSelectionne = productItem;
                        showProductDetailsPopup(productItem);
                });
                // Ajouter les composants à la carte
                productCard.getChildren().addAll(imageView, productName, productPrice, addToCartButton);
                productCard.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 5;-fx-background-color: white;");
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
        // Calculer la hauteur totale du GridPane en fonction du nombre de lignes
        double totalHeight = (row + 1) * (hauteurCarte + gridProd.getVgap());
        gridProd.setPrefHeight(totalHeight);
        scrollProd.setContent(gridProd);
        scrollProd.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }
    public PanierController() {
        instance = this;
    }
    public static PanierController getInstance() {
        return instance;
    }
    @FXML
    void click_on_Panier(ActionEvent event) {
        try {
            cartBox.getChildren().clear();

            // Récupérer l'ID de l'utilisateur courant
            User currentUser = Session.getInstance().getCurrentUser();
            int idUser = currentUser.getId();
            String nomPrenom = currentUser.getNom() + " " + currentUser.getPrenom();

            // Créer une instance du service panier
            panierService panierService = new panierService();

            // Récupérer ou créer un panier pour l'utilisateur
            panier panierUtilisateur = panierService.getOrCreatePanierForUser(idUser);

            // Récupérer l'ID du panier à partir de l'objet panier
            int panierId = panierUtilisateur.getId();
            List<panierProduit> panierProduits = paniProdService.getProduitsDansPanier(panierId);

            // Section du titre et des informations utilisateur
            Label title = new Label("Mon Panier");
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            VBox.setMargin(title, new Insets(20, 10, 10, 10));
            cartBox.getChildren().add(title);

            Label userLabel = new Label("Nom et Prenom : " + nomPrenom);
            userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            VBox.setMargin(userLabel, new Insets(0, 10, 10, 10));
            cartBox.getChildren().add(userLabel);

            // Section des produits avec défilement
            VBox productBox = new VBox(10);
            productBox.setPadding(new Insets(10));
            productBox.setStyle("-fx-background-color: #f5f5f5;-fx-text-fill: black;");

            // Ajouter un en-tête
            HBox headerBox = new HBox(10);
            headerBox.setAlignment(Pos.CENTER_LEFT);
            headerBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-font-weight: bold;-fx-text-fill: black;-fx-border-color:lightgray;");

            Label headerImage = new Label("Image");
            headerImage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;-fx-text-fill: black;");

            Label headerName = new Label("Nom");
            headerName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;-fx-text-fill: black;");

            Label headerQuantity = new Label("Quantité");
            headerQuantity.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;-fx-text-fill: black;");

            Label headerMontant = new Label("Montant");
            headerMontant.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;-fx-text-fill: black;");

            // Ajouter des marges pour les colonnes de l'en-tête
            HBox.setMargin(headerImage, new Insets(0, 15, 0, 0));
            HBox.setMargin(headerName, new Insets(0, 30, 0, 27));
            HBox.setMargin(headerQuantity, new Insets(0, 1, 0, 0));
            HBox.setMargin(headerMontant, new Insets(0, 20, 0, 0));

            headerBox.getChildren().addAll(headerImage, headerName, headerQuantity, headerMontant);
            productBox.getChildren().add(headerBox);

            double totalMontant = 0;
            for (panierProduit produitPanier : panierProduits) {
                // Récupérer les informations du produit via produitId
                produit produit = prodService.getById(produitPanier.getProduitId());
                HBox itemBox = new HBox(10);
                itemBox.setAlignment(Pos.CENTER_LEFT);
                itemBox.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px; -fx-padding: 4; -fx-background-color: white;-fx-font-weight:bold;-fx-text-fill: black;");
                ImageView imageView;
                try {
                    imageView = new ImageView(new Image(getClass().getResourceAsStream("/img/" + produit.getImage())));
                } catch (Exception e) {
                    imageView = new ImageView(new Image(getClass().getResourceAsStream("/img/default.png")));
                }
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                Label nameLabel = new Label(produit.getNom());
                nameLabel.setStyle("-fx-font-size: 13px;-fx-text-fill: black;");
                // Par défaut, afficher la quantité dans un Label (non modifiable)
                Label quantityLabel = new Label(String.valueOf(produitPanier.getQuantite()));
                quantityLabel.setStyle("-fx-font-size: 13px;-fx-text-fill: black;");

                double montant = produit.getPrix() * produitPanier.getQuantite();
                totalMontant += montant; // Ajouter le montant au total
                Label montantLabel = new Label(String.valueOf(montant));
                montantLabel.setStyle("-fx-font-size: 13px;-fx-text-fill: black;");

                Spinner<Integer> quantitySpinner = new Spinner<>();
                quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, produitPanier.getQuantite()));
                quantitySpinner.setStyle("-fx-font-size: 10px;");
                quantitySpinner.setPrefWidth(40);
                quantitySpinner.setVisible(false); // Cacher le Spinner au début

                Button modifyButton = new Button();
                modifyButton.setStyle("-fx-background-color: white;-fx-border-color:black;-fx-border-radius: 5px;");
                Image image = new Image(getClass().getResourceAsStream("/img/editer.png"));
                ImageView pencilIcon = new ImageView(image);
                pencilIcon.setFitWidth(20);
                pencilIcon.setFitHeight(20);

                // Ajouter l'icône au bouton
                modifyButton.setGraphic(pencilIcon);
                modifyButton.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
                modifyButton.setOnAction(e -> {
                    itemBox.getChildren().remove(quantityLabel); // Retirer le Label de quantité

                    int index = itemBox.getChildren().indexOf(montantLabel);
                    if (!itemBox.getChildren().contains(quantitySpinner)) {
                        itemBox.getChildren().add(index, quantitySpinner);
                    }
                    quantitySpinner.setVisible(true);
                });
                Button deleteButton = new Button("X");
                deleteButton.setStyle("-fx-background-color: #ff0000;");
                deleteButton.setOnAction(e -> {
                    try {
                        paniProdService.delete(produitPanier.getId());
                        cartBox.getChildren().clear();
                        click_on_Panier(e);
                    } catch (Exception ex) {
                        ex.printStackTrace(); // Déboguer ou afficher une alerte
                    }
                });
                //Ajouter des marges pour ajouter de l'espace entre les éléments
                HBox.setMargin(imageView, new Insets(0, 10, 0, 0));
                HBox.setMargin(nameLabel, new Insets(0, 5, 0, 0));
                HBox.setMargin(quantityLabel, new Insets(0, 5, 0, 0));
                HBox.setMargin(montantLabel, new Insets(0, 5, 0, 0));

                itemBox.getChildren().addAll(imageView, nameLabel, quantityLabel, montantLabel, modifyButton, deleteButton);
                productBox.getChildren().add(itemBox);
            }
            ScrollPane productScrollPane = new ScrollPane();
            productScrollPane.setContent(productBox);
            productScrollPane.setFitToWidth(true);
            productScrollPane.setStyle("-fx-background: transparent;");
            cartBox.getChildren().add(productScrollPane);
            if (!panierProduits.isEmpty()) {
                // Ajouter le total des montants
                Label totalLabel = new Label("Total : " + totalMontant + " TND");
                totalLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #000;");
                // Bouton Modifier Quantité
                Button updateQuantitiesButton = new Button("Modifier Quantité");
                updateQuantitiesButton.setStyle("-fx-background-color: #F58400; -fx-text-fill: white; -fx-font-weight: bold;");
                updateQuantitiesButton.setOnAction(e -> {
                    try {
                        for (Node node : productBox.getChildren()) {
                            if (node instanceof HBox) {
                                HBox itemBox = (HBox) node;
                                for (Node child : itemBox.getChildren()) {
                                    if (child instanceof Spinner) {
                                        Spinner<Integer> spinner = (Spinner<Integer>) child;
                                        int newQuantity = spinner.getValue();
                                        // Trouver le produit correspondant
                                        int index = productBox.getChildren().indexOf(itemBox);
                                        panierProduit produitPanier = panierProduits.get(index - 1); // Enlever l'index de l'en-tête
                                        // Vérifier le stock disponible
                                        produit produit = prodService.getById(produitPanier.getProduitId());
                                        if (newQuantity > produit.getQuantite()) {
                                            // Afficher une alerte si la quantité dépasse le stock
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle("Erreur de Stock");
                                            alert.setHeaderText("Quantité non disponible");
                                            alert.setContentText("La quantité demandée pour le produit "
                                                    + produit.getNom() + " dépasse le stock disponible ("
                                                    + produit.getQuantite() + ").");
                                            alert.showAndWait();
                                            return; // Arrêter l'exécution pour éviter une mise à jour incorrecte
                                        }


                                        produitPanier.setQuantite(newQuantity);
                                        // Mettre à jour la quantité dans la base de données
                                        paniProdService.modifierQuantiteProduitDansPanier(produitPanier.getId(), newQuantity);
                                    }
                                }
                            }
                        }
                        // Rafraîchir l'affichage
                        click_on_Panier(event);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                // Créer le conteneur pour le total et les boutons
                VBox actionsBox = new VBox(20);
                actionsBox.setAlignment(Pos.CENTER);
                actionsBox.setPadding(new Insets(25, 10, 40, 10));

                // Bouton de paiement
                Button paymentButton = new Button("Payer Panier");
                paymentButton.setStyle("-fx-background-color: #F58400; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-pref-width: 400; -fx-font-size: 14px;");

                double finalTotalMontant = totalMontant;
                paymentButton.setOnAction(e -> {
                    //paiement Panier
                    try {
                        // Charger le fichier FXML
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PaiementPopUp.fxml"));
                        Parent root = fxmlLoader.load();

                        // Récupérer le contrôleur de la fenêtre PaiementPopUp
                        PaiementPopUpController paiementPopUpController = fxmlLoader.getController();

                        paiementPopUpController.setTotalPrice(finalTotalMontant + " TND");
                        paiementPopUpController.setTotalMontant(finalTotalMontant);

                        paiementPopUpController.setPanierId(panierId);

                        // Créer une nouvelle scène pour la fenêtre pop-up
                        Stage stage = new Stage();
                        stage.setTitle("Paiement du Panier");
                        stage.setScene(new Scene(root));

                        // Optionnel : rendre la fenêtre modale
                        stage.initModality(Modality.APPLICATION_MODAL);

                        // Afficher la fenêtre
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                HBox totalBox = new HBox(120);
                totalBox.setAlignment(Pos.CENTER);
                totalBox.getChildren().addAll(totalLabel,updateQuantitiesButton);
                actionsBox.getChildren().addAll(totalBox,paymentButton);

                cartBox.getChildren().add(actionsBox);
            } else {
                Label emptyLabel = new Label("Votre panier est vide.");
                emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                cartBox.getChildren().add(emptyLabel);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Afficher l'exception pour le débogage
        }
    }
    @FXML
    void openPopUpRecommondation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopUpRecommendation.fxml"));
            Parent root = loader.load();

            ((Node) event.getSource()).getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierClient.fxml"));
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
