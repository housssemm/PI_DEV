package Controllers;

import Models.etat;
import Models.produit;
import Services.produitService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

public class ProduitController {
    @FXML
    private GridPane GridPaneProd;
    @FXML
    private TextField desc_produit;
    @FXML
    private ChoiceBox<etat> etat_produit;
    @FXML
    private TextField Id_categorie;
    @FXML
    private TextField Id_categorie1;
    @FXML
    private TextField Id_investisseur;
    @FXML
    private TextField Nom_Produit1;
    @FXML
    private TextField desc_prod1;
    @FXML
    private ChoiceBox<etat> etat_produit1;
    @FXML
    private TextField prix1;
    @FXML
    private TextField quantite_produit1;
    @FXML
    private TextField quantite_produit;
    @FXML
    private TextField nom_produit;
    @FXML
    private TextField prix_produit;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imgView;
    @FXML
    private ScrollPane scrollPaneProd;
    private File selectedImageFile;
    private produitService produitService = new produitService();
    private produit produitUpdated=new produit();
    private int selectedProduitId;

    @FXML
    void Ajouter()throws Exception {
        if (!validateInput()) return;

        String nom = nom_produit.getText();
        String description = desc_produit.getText();
        etat etatProduit = etat.valueOf(etat_produit.getValue().toString());
        int idCategorie =Integer.parseInt(Id_categorie.getText());
        int idInvestisseur =Integer.parseInt(Id_investisseur.getText());
        int quantite=Integer.parseInt(quantite_produit.getText());
        float prix = Float.parseFloat(prix_produit.getText());
        String imagePath = selectedImageFile.getAbsolutePath().replace("\\", "/");
        produit prod=new produit(idInvestisseur,nom,description,imagePath,etatProduit,idCategorie,quantite,prix);
        produitService.create(prod);
        initialize();
    }
    public void initialize() throws Exception {

        // Remplir le ChoiceBox pour l'état
        etat_produit.getItems().setAll(etat.values());
        etat_produit1.getItems().setAll(etat.values());
        if (GridPaneProd == null) {
            System.out.println("GridPane is null! Check FXML configuration.");
        } else {
            System.out.println("GridPane initialized successfully.");
        }
        // Configuration du ScrollPane
        scrollPaneProd.setFitToHeight(true);
        scrollPaneProd.setFitToWidth(false);

        // Ajouter un espacement entre les éléments du GridPane
        GridPaneProd.setHgap(20); // Espacement horizontal entre les colonnes
        GridPaneProd.setAlignment(Pos.CENTER); // Centrer le contenu dans le GridPane

        // Récupérer tous les produits
        List<produit> produits = produitService.getAll(); // Remplacez par la méthode correcte pour récupérer vos produits

        // Nettoyer le GridPane
        GridPaneProd.getChildren().clear();

        int columnIndex = 0; // Index pour les colonnes
        for (produit prod : produits) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(60); // Taille de l'image (ajustée)
            imageView.setFitHeight(50); // Taille de l'image (ajustée)

            // Vérifier si l'image existe et l'afficher
            File imageFile = new File(prod.getImage());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            }

            // Ajouter les informations du produit dans des Texts
            Text nomText = new Text("Nom : " + prod.getNom());
            Text descText = new Text("Description : " + prod.getDescription());
            Text etatText = new Text("État : " + prod.getEtat());
            Text quantiteText = new Text("Quantité : " + prod.getQuantite());
            Text prixText = new Text("Prix : " + prod.getPrix() + " TND");
            Text investisseurText = new Text("Investisseur : " + prod.getIdInvestisseur());
            Text categorieText = new Text("Catégorie : " + prod.getCategorieId());

            nomText.setWrappingWidth(150); // Ajuster cette valeur selon la largeur de votre carte
            descText.setWrappingWidth(150); // Ajuster cette valeur selon la largeur de votre carte

            // Appliquer l'alignement à gauche directement sur les Texts
            nomText.setTextAlignment(TextAlignment.CENTER);
            descText.setTextAlignment(TextAlignment.CENTER);

            // Ajouter le bouton "supprimer"
            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 5;-fx-font-weight: bold;");
            deleteButton.setOnAction(event -> {
                try {
                    produitService.delete(prod.getId());
                    initialize();
                } catch (Exception e) {
                    System.out.println("Erreur lors de la suppression de la catégorie : " + e.getMessage());
                }
            });
            // Ajouter le bouton "Modifier"
            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: #f58400; -fx-text-fill: white; -fx-border-radius: 5;-fx-font-weight: bold;");

            modifyButton.setOnAction(event -> {
                // Remplir les champs avec les informations de la catégorie sélectionnée
                Nom_Produit1.setText(prod.getNom());
                desc_prod1.setText(prod.getDescription());
                quantite_produit1.setText(String.valueOf(prod.getQuantite()));
                prix1.setText(String.valueOf(prod.getPrix()));
                Id_categorie1.setText(String.valueOf(prod.getCategorieId()));
                etat_produit1.setValue(prod.getEtat());
                selectedProduitId= prod.getId();
                // Charger l'image existante dans l'ImageView global
                File imageFile1 = new File(prod.getImage());
                if (imageFile1.exists()) {
                    String imagePath = "file:" + imageFile1.getAbsolutePath();
                    Image image = new Image(imagePath);
                    imgView.setImage(image);
                } else {
                    System.out.println("L'image n'existe pas à ce chemin.");
                }
            });
            // Créer une VBox pour chaque produit
            VBox produitBox = new VBox(10); // Espacement vertical de 10 pixels
            produitBox.setStyle("-fx-padding: 15; -fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-background-radius: 10;");
            produitBox.setAlignment(Pos.CENTER);

            // Fixer la largeur des cartes
            produitBox.setPrefWidth(200); // Largeur fixe pour toutes les cartes
            produitBox.setMinHeight(330); // Fixer la hauteur minimale des cartes pour qu'elle reste inchangée même si le texte dépasse
            // Ajouter l'image et les informations dans la VBox
            produitBox.getChildren().addAll(imageView, nomText, descText, etatText, quantiteText, prixText, investisseurText, categorieText,deleteButton,modifyButton);

            // Ajouter la VBox dans le GridPane
            GridPaneProd.add(produitBox, columnIndex, 0);
            columnIndex++; // Passer à la colonne suivante
        }
        // Définir la largeur totale du GridPane en fonction du nombre de produits
        double largeurCarte = 220; // Largeur approximative de chaque carte + marges
        GridPaneProd.setPrefWidth(produits.size() * largeurCarte);
    }
    @FXML
    void upload_Image() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            // Normaliser le chemin pour être compatible avec JavaFX
            selectedImageFile.getAbsolutePath().replace("\\", "/");
            // Charger et afficher l'image dans l'ImageView
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(90);  // Largeur maximale
            imageView.setFitHeight(80); // Hauteur maximale
            imageView.setPreserveRatio(false);  // Permet de déformer l'image si nécessaire
            imageView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 10;");
        }
    }
    @FXML
    void upload_new_Image() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            selectedImageFile.getAbsolutePath().replace("\\", "/");
            Image image = new Image(file.toURI().toString());
            imgView.setFitWidth(90);
            imgView.setFitHeight(80);
            imgView.setPreserveRatio(false);
            imgView.setImage(image);
            imgView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 10;");
        }
    }
    @FXML
    void Modifier() throws Exception {
        if (!validateInput1()) return;
        if (selectedImageFile == null || !selectedImageFile.exists()) {
            showAlert("Erreur", "L'image du produit est manquante.");
            return;
        }
            produitUpdated.setNom(Nom_Produit1.getText());
            produitUpdated.setDescription(desc_prod1.getText());
            String imagePath = selectedImageFile.getAbsolutePath().replace("\\", "/");
            produitUpdated.setImage(imagePath);
            produitUpdated.setEtat(etat.valueOf(etat_produit1.getValue().toString()));
            produitUpdated.setCategorieId(Integer.parseInt(Id_categorie1.getText()));
            produitUpdated.setQuantite(Integer.parseInt(quantite_produit1.getText()));
            produitUpdated.setPrix(Float.parseFloat(prix1.getText()));
            produitUpdated.setId(selectedProduitId); // Associer l'ID de la catégorie sélectionnée
            // Appeler le service pour mettre à jour la catégorie dans la base de données
            produitService.update(produitUpdated);
        // mise a jour
        initialize();
    }
    private boolean validateInput() {
        if (nom_produit.getText().trim().isEmpty() || nom_produit.getText() == null) {
            showAlert("Erreur de saisie", "Le champ 'Nom du produit' ne peut pas être vide.");
            return false;
        }
        if (!nom_produit.getText().matches("[a-zA-Z\\s]*")) {
            showAlert("Erreur de saisie", "Le champ 'Nom du produit' doit contenir des lettres.");
            return false;
        }
        if (desc_produit.getText().trim().isEmpty()) {
            showAlert("Erreur de saisie", "Le champ 'Description du produit' ne peut pas être vide.");
            return false;
        }
        if (!desc_produit.getText().matches("[a-zA-Z\\s]*")) {
            showAlert("Erreur de saisie", "Le champ 'description du produit' doit contenir des lettres .");
            return false;
        }
        if (etat_produit.getValue() == null) {
            showAlert("Erreur de saisie", "Veuillez sélectionner un état pour le produit.");
            return false;
        }
        if (quantite_produit.getText().isEmpty() || !quantite_produit.getText().matches("[1-9][0-9]*")) {
            showAlert("Erreur de saisie", "la quantite doit être un nombre valide.");
            return false;
        }
        if (Id_categorie.getText().isEmpty() || !Id_categorie.getText().matches("[1-9][0-9]*")) {
            showAlert("Erreur de saisie", "L'ID de catégorie doit être un nombre valide.");
            return false;
        }
        if (Id_investisseur.getText().isEmpty() || !Id_investisseur.getText().matches("[1-9][0-9]*")) {
            showAlert("Erreur de saisie", "L'ID de l'investisseur doit être un nombre valide.");
            return false;
        }
        if (prix_produit.getText().isEmpty() || !prix_produit.getText().matches("[1-9][0-9]*(\\.[0-9]+)?")) {
            showAlert("Erreur de saisie", "le prix de produit doit être un nombre valide.");
            return false;
        }
        if (selectedImageFile == null) {
            showAlert("Erreur de saisie", "Veuillez sélectionner une image pour le produit.");
            return false;
        }
        return true;
    }
    private boolean validateInput1() {
        if (!Nom_Produit1.getText().matches("[a-zA-Z\\s]*")) {
            showAlert("Erreur de saisie", "Le champ 'Nom du produit' doit contenir des lettres.");
            return false;
        }
        if (Nom_Produit1.getText().trim().isEmpty()) {
            showAlert("Erreur de saisie", "Le champ 'Nom de produit' ne peut pas être vide.");
            return false;
        }
        if (desc_prod1.getText().trim().isEmpty()) {
            showAlert("Erreur de saisie", "Le champ 'Description du produit' ne peut pas être vide.");
            return false;
        }
        if (!desc_prod1.getText().matches("[a-zA-Z\\s]*")) {
            showAlert("Erreur de saisie", "Le champ 'description du produit' doit contenir des lettres .");
            return false;
        }
        if (etat_produit1.getValue() == null) {
            showAlert("Erreur de saisie", "Veuillez sélectionner un état pour le produit.");
            return false;
        }
        if (quantite_produit1.getText().isEmpty() || !quantite_produit1.getText().matches("[1-9][0-9]*")) {
            showAlert("Erreur de saisie", "la quantite doit être un nombre valide.");
            return false;
        }
        if (Id_categorie1.getText().isEmpty() || !Id_categorie1.getText().matches("[1-9][0-9]*")) {
            showAlert("Erreur de saisie", "L'ID de catégorie doit être un nombre valide.");
            return false;
        }
        if (prix1.getText().isEmpty() || !prix1.getText().matches("[1-9][0-9]*(\\.[0-9]+)?")) {
            showAlert("Erreur de saisie", "le prix de produit doit être un nombre valide.");
            return false;
        }
        return true;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
   void GoToCategorie(ActionEvent actionEvent) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/Categorie.fxml"));
           Parent root = loader.load();
           ((Button) actionEvent.getSource()).getScene().setRoot(root);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
