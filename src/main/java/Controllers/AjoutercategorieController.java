package Controllers;

import Models.Categorie;
import Services.CreateurEvenementService;
import Services.categorieService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class AjoutercategorieController {
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imgView;
    @FXML
    private TextField Nom_categ;
    @FXML
    private TextField label_Nom_categ;
    @FXML
    private GridPane GridPane;
    private File selectedImageFile;
    private categorieService categService = new categorieService();
    private int selectedCategorieId;
    private Categorie CategorieUpdated=new Categorie();

    @FXML
    void AjouterCategorie() throws Exception {
        if (!validateInput()) return;

        String imageName1 = "";
        // Récupérer uniquement le nom du fichier
        imageName1 = selectedImageFile.getName();
        Categorie c1 = new Categorie(label_Nom_categ.getText(), imageName1);
        categService.create(c1);
        showSuccessAlert();
        //mise a jour
        initialize();
        // Réinitialiser les champs après l'ajout
        label_Nom_categ.clear();
        imageView.setImage(null);
        selectedImageFile = null;
    }
    @FXML
    void upload_new_image() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            // Charger et afficher l'image dans l'ImageView
            Image image = new Image(file.toURI().toString());
            imgView.setFitWidth(90);  // Largeur maximale
            imgView.setFitHeight(80); // Hauteur maximale
            imgView.setPreserveRatio(false);  // Permet de déformer l'image si nécessaire
            imgView.setImage(image);
            imgView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 10;");
        }
    }
    @FXML
    void upload_Image() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            // Charger et afficher l'image dans l'ImageView
            Image image = new Image(file.toURI().toString());
            imageView.setFitWidth(90);  // Largeur maximale
            imageView.setFitHeight(80); // Hauteur maximale
            imageView.setPreserveRatio(false);  // Permet de déformer l'image si nécessaire
            imageView.setImage(image);
            imageView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 10;");
        }
    }
    @FXML
    public void initialize() throws Exception {

        if (GridPane == null) {
            System.out.println("GridPane is null! Check FXML configuration.");
        } else {
            // Exemple d'ajout d'un élément au GridPane
            System.out.println("GridPane initialized successfully.");
        }

        // Récupérer toutes les catégories depuis la base de données
        List<Categorie> categories = categService.getAll();

        // Nettoyer le GridPane avant d'ajouter de nouveaux éléments
        GridPane.getChildren().clear();

        int row = 0;
        int col = 0;

        for (Categorie categorie : categories) {
            ImageView imageView = new ImageView();
            imageView.setStyle("-fx-start-margin: 30");
            imageView.setFitWidth(60); // Taille de l'image (ajustée)
            imageView.setFitHeight(50); // Taille de l'image (ajustée)

            // Créer le chemin relatif de l'image dans resources/img
            String imagePath = "/img/" + categorie.getImage();
            // Vérifier si l'image existe et l'afficher
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                imageView.setImage(image);
            } else {
                System.out.println("Image non trouvée : " + imagePath);
            }
            // Créer un Text pour le nom de la catégorie
            Text nameText = new Text("Nom : " + categorie.getNom());
            nameText.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

            // Ajouter le bouton "supprimer"
            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 5;-fx-font-weight: bold;");
            deleteButton.setOnAction(event -> {
                if (confirmerSuppression()) {
                    try {
                        categService.delete(categorie.getId());
                        initialize();
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la suppression de la catégorie : " + e.getMessage());
                    }
                }
            });

            // Ajouter le bouton "Modifier"
            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: #f58400; -fx-text-fill: white; -fx-border-radius: 5;-fx-font-weight: bold;");

            modifyButton.setOnAction(event -> {

                // Remplir les champs avec les informations de la catégorie sélectionnée
                Nom_categ.setText(categorie.getNom());
                selectedCategorieId = categorie.getId();
                String imageName = categorie.getImage();
                String imagePath1 = "/img/" + imageName;

                // Vérifier si l'image existe dans resources
                InputStream imageStream1 = getClass().getResourceAsStream(imagePath1);
                if (imageStream1 != null) {
                    Image image = new Image(imageStream1);
                    imgView.setImage(image);  // Afficher l'image dans l'ImageView
                } else {
                    System.out.println("L'image n'a pas été trouvée : " + imagePath1);
                }
            });

            // Créer un VBox pour organiser l'image, l'ID, et le nom
            VBox contentBox = new VBox(3);
            contentBox.setAlignment(Pos.CENTER); // Centrer le contenu
            contentBox.getChildren().addAll(imageView,nameText, deleteButton, modifyButton);
            contentBox.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 10; -fx-spacing: 10;-fx-start-margin-right: 30; ");

            // Ajouter la VBox au GridPane
            GridPane.add(contentBox, col, row);

            // Gérer les colonnes et les lignes du GridPane
            col++;
            if (col == 5) { // 5 colonnes par ligne
                col = 0;
                row++;
            }
        }
    }
    @FXML
    void Modifier() throws Exception {
        if (!validateInput1()) return;
        // Associer l'ID de la catégorie sélectionnée
        CategorieUpdated.setId(selectedCategorieId);
        CategorieUpdated.setNom(Nom_categ.getText());
        CategorieUpdated.setNom(Nom_categ.getText());
        String imageName = selectedImageFile.getName();
        CategorieUpdated.setImage(imageName);
        categService.update(CategorieUpdated);
        showSuccessAlert1();
        Nom_categ.clear();
        imgView.setImage(null);
        selectedImageFile = null;
        // mise a jour
        initialize();
    }
    private boolean validateInput() {
        if (label_Nom_categ.getText() == null || label_Nom_categ.getText().trim().isEmpty()) {
            showAlert("Erreur de saisie", "Le champ 'Nom de catégorie' ne peut pas être vide.");
            return false;
        }
        if (!label_Nom_categ.getText().matches("[a-zA-Z\\s]*")) {
            showAlert("Erreur de saisie", "Le champ 'Nom du categorie' doit contenir des lettres.");
            return false;
        }
        if (selectedImageFile == null) {
            showAlert("Erreur de saisie", "Veuillez sélectionner une image.");
            return false;
        }
        return true;
    }
    private boolean validateInput1() {
        if (Nom_categ.getText() == null || Nom_categ.getText().trim().isEmpty()) {
            showAlert("Erreur de saisie", "Le champ 'Nom de catégorie' ne peut pas être vide.");
            return false;
        }
        if (!Nom_categ.getText().matches("[a-zA-Z\\s]*")) {
            showAlert("Erreur de saisie", "Le champ 'Nom du categorie' doit contenir des lettres.");
            return false;
        }
        if (selectedImageFile == null) {
            showAlert("Erreur de saisie", "Veuillez sélectionner une image.");
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
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("La categorie a été ajouté avec succès !");
        alert.showAndWait();
    }
    private void showSuccessAlert1() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("La categorie a été modifié avec succès !");
        alert.showAndWait();
    }
    private boolean confirmerSuppression() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Categorie ?");

        // Afficher la boîte de dialogue et retourner la réponse
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
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
    void GoToCategorie(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Categorie.fxml"));
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