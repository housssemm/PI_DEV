//package Controllers;
//
//import Models.*;
//import Services.*;
//import Utils.Session;
//import javafx.animation.TranslateTransition;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.Objects;
//import java.util.ResourceBundle;
//import java.util.stream.Collectors;
//
//
//public class SigninUpController  implements Initializable {
//
//    private static final String SITE_KEY = "6LdgZeUqAAAAAOlPg-W703BS7aIJ5skRdwoppZ5M";
//    private static final String SECRET_KEY = "6LdgZeUqAAAAANOZ6ybiFaHuKMCtSNRLnJZzFa-1";
//
//    public SigninUpController() throws SQLException {
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        // loadRecaptcha();
//          //si_email.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsAndTriggerRecaptcha());
//       // si_mdp.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsAndTriggerRecaptcha());
//     su_nom.getStyleClass().clear();  // Retirer tous les styles existants
//        su_nom.getStyleClass().add("textfield");
//        su_nom.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[a-zA-Z]*")) { // Vérifie que seuls des lettres sont autorisées
//                su_nom.getStyleClass().remove("textfield");
//                su_nom.getStyleClass().add("text-field-error");
//            } else {
//                // Appliquer le style normal
//                su_nom.getStyleClass().remove("text-field-error");
//                su_nom.getStyleClass().add("textfield");}
//        });
//        su_prenom.getStyleClass().clear();  // Retirer tous les styles existants
//        su_prenom.getStyleClass().add("textfield");
//        su_prenom.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[a-zA-Z]*")) { // Vérifie que seuls des lettres sont autorisées
//                su_prenom.getStyleClass().remove("textfield");
//                su_prenom.getStyleClass().add("text-field-error");
//            } else {
//                // Appliquer le style normal
//                su_prenom.getStyleClass().remove("text-field-error");
//                su_prenom.getStyleClass().add("textfield");}
//        });
//        age.getStyleClass().clear();  // Retirer tous les styles existants
//        age.getStyleClass().add("textfield");
//
//        age.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9]*")) { // Vérifie que seuls des chiffres sont autorisés
//                age.getStyleClass().remove("textfield");
//                age.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                age.getStyleClass().remove("text-field-error");
//                age.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//        anneeExperience.getStyleClass().clear();  // Retirer tous les styles existants
//        anneeExperience.getStyleClass().add("textfield");
//
//        anneeExperience.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9]*")) { // Vérifie que seuls des chiffres sont autorisés
//                anneeExperience.getStyleClass().remove("textfield");
//                anneeExperience.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                anneeExperience.getStyleClass().remove("text-field-error");
//                anneeExperience.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//        telephoneCreateur.getStyleClass().clear();  // Retirer tous les styles existants
//        telephoneCreateur.getStyleClass().add("textfield");
//        telephoneCreateur.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9]{8}")) { // Vérifie que seuls des chiffres sont autorisés
//                telephoneCreateur.getStyleClass().remove("textfield");
//                telephoneCreateur.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                telephoneCreateur.getStyleClass().remove("text-field-error");
//                telephoneCreateur.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//        telephoneInvestisseur.getStyleClass().clear();  // Retirer tous les styles existants
//        telephoneInvestisseur.getStyleClass().add("textfield");
//        telephoneInvestisseur.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9]{8}")) { // Vérifie que seuls des chiffres sont autorisés
//                telephoneInvestisseur.getStyleClass().remove("textfield");
//                telephoneInvestisseur.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                telephoneInvestisseur.getStyleClass().remove("text-field-error");
//                telephoneInvestisseur.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//        su_email.getStyleClass().clear();  // Retirer tous les styles existants
//        su_email.getStyleClass().add("textfield");
//        su_email.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // Vérifie le format de l'email
//                su_email.getStyleClass().remove("textfield");
//                su_email.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                su_email.getStyleClass().remove("text-field-error");
//                su_email.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//        si_email.getStyleClass().clear();  // Retirer tous les styles existants
//        si_email.getStyleClass().add("textfield");
//        si_email.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // Vérifie le format de l'email
//                si_email.getStyleClass().remove("textfield");
//                si_email.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                si_email.getStyleClass().remove("text-field-error");
//                si_email.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//
//        taille.getStyleClass().clear();  // Retirer tous les styles existants
//        taille.getStyleClass().add("textfield");
//        taille.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*\\.?\\d*")) { // Vérifie que seuls des chiffres et un point décimal sont autorisés
//                taille.getStyleClass().remove("textfield");
//                taille.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                taille.getStyleClass().remove("text-field-error");
//                taille.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//        poids.getStyleClass().clear();  // Retirer tous les styles existants
//        poids.getStyleClass().add("textfield");
//
//        poids.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*\\.?\\d*")) { // Vérifie que seuls des chiffres et un point décimal sont autorisés
//                poids.getStyleClass().remove("textfield");
//                poids.getStyleClass().add("text-field-error");  // Applique le style erreur
//            } else {
//                // Appliquer le style normal
//                poids.getStyleClass().remove("text-field-error");
//                poids.getStyleClass().add("textfield");  // Applique le style normal
//            }
//        });
//
//
//
//
//
//        choicebox.getItems().addAll("Adhérent", "Coach", "Investisseur de produits", "Créateur des événements");
//        genre.getItems().setAll(Arrays.stream(GenreG.values()).map(Enum::name).collect(Collectors.toList())
//        );
//        objectifPersonnel.getItems().setAll(Arrays.stream(ObjP.values()).map(Enum::name).collect(Collectors.toList())
//        );
//        niveauActivite.getItems().setAll(
//                Arrays.stream(NiveauA.values()).map(Enum::name).collect(Collectors.toList())
//        );
//        specialite.getItems().setAll(Arrays.stream(SpecialiteC.values()).map(Enum::name).collect(Collectors.toList())
//        );
//
//
//
//
//
//        choicebox.setOnAction(event -> {
//            String selectedRole = choicebox.getValue();
//            System.out.println("Role selected: " + selectedRole);
//            UserData userData = new UserData();  // On crée un objet userData pour le rôle sélectionné
//            updateDynamicFields(selectedRole, userData); // Mettre à jour les champs dynamiques
//        });
//    }
//
//    private final AdherentService adherentService = new AdherentService();
//    private final CoachService coachService = new CoachService();
//    private final InvestisseurProduitService investisseurService = new InvestisseurProduitService();
//    private final CreateurEvenementService createurEvenementService = new CreateurEvenementService();
//
//    private void updateDynamicFields(String selectedRole, UserData userData) {
//        // Masquer tous les champs dynamiques
//        adherentFields.setVisible(false);
//        coachFields.setVisible(false);
//        investisseurFields.setVisible(false);
//        createurEventFields.setVisible(false);
//
//
//        // Afficher les champs correspondant au rôle sélectionné
//        switch (selectedRole) {
//            case "Adhérent":
//                adherentFields.setVisible(true);
////                    adherentService.createAdherent(userData);
//                break;
//            case "Coach":
//                coachFields.setVisible(true);
////                    coachService.createCoach(userData);
//                break;
//            case "Investisseur de produits":
//                investisseurFields.setVisible(true);
////                    investisseurService.createInvestisseurProduit(userData);
//                break;
//            case "Créateur des événements":
//                createurEventFields.setVisible(true);
////                    createurEvenementService.createCreateurEvenement(userData);
//                break;
//        }
//    }
//
//
//    @FXML
//    private void handleSuivantButtonAction() {
//
//        UserData userData = new UserData();
//        // Vérification des champs communs
//        if (isFieldEmpty(su_nom.getText()) || isFieldEmpty(su_prenom.getText()) ||
//                isFieldEmpty(su_email.getText()) || isFieldEmpty(su_mdp.getText())) {
//            showError("Tous les champs obligatoires doivent être remplis.");
//            return;
//        }
//
//        if (!isValidEmail(su_email.getText())) {
//            showError("Format d'email invalide.");
//            return;
//        }
//        // Contrôle du nom : uniquement des lettres
//        if (!su_nom.getText().matches("[a-zA-Z]*")) {
//            showError("Le nom ne peut contenir que des lettres.");
//            return;
//        }
//
//        // Contrôle du prénom : uniquement des lettres
//        if (!su_prenom.getText().matches("[a-zA-Z]*")) {
//            showError("Le prénom ne peut contenir que des lettres.");
//            return;
//        }
//
//        userData.setNom(su_nom.getText());
//        userData.setPrenom(su_prenom.getText());
//        userData.setEmail(su_email.getText());
//        userData.setMDP(su_mdp.getText());
//
//        String selectedRole = choicebox.getValue();
//        switch (selectedRole) {
//            case "Adhérent":
//                if (!isValidFloat(poids.getText()) || !isValidFloat(taille.getText()) || !isValidInt(age.getText())) {
//                    showError("Poids et taille doivent être des nombres réels. L'âge doit être un entier.");
//                    return;
//                }
//                // Contrôle sur Poids et Taille
//                if (!poids.getText().matches("[0-9]+(\\.[0-9]+)?")) { // Vérifie que le poids est un nombre réel
//                    showError("Le poids doit être un nombre réel.");
//                    return;
//                }
//
//                if (!taille.getText().matches("[0-9]+(\\.[0-9]+)?")) { // Vérifie que la taille est un nombre réel
//                    showError("La taille doit être un nombre réel.");
//                    return;
//                }
//
//                // Contrôle sur Âge
//                if (!age.getText().matches("[0-9]+")) { // Vérifie que l'âge est un entier
//                    showError("L'âge doit être un nombre entier.");
//                    return;
//                }
//                userData.setPoids(Float.parseFloat(poids.getText()));
//                userData.setTaille(Float.parseFloat(taille.getText()));
//                userData.setAge(Integer.parseInt(age.getText()));
//                userData.setGenre(GenreG.valueOf(genre.getValue()));
//                userData.setObjectifPersonnel(ObjP.valueOf(objectifPersonnel.getValue()));
//                userData.setNiveauActivite(NiveauA.valueOf(niveauActivite.getValue()));
//                adherentService.createAdherent(userData);
//                showAlertSuccess("Adhérent", userData.getNom());
//
//                break;
//            case "Coach":
//                if (!isValidInt(anneeExperience.getText())) {
//                    showError("L'année d'expérience doit être un nombre entier.");
//                    return;
//                }
//                // Vérification de la spécialité
//                if (specialite.getValue() == null) {
//                    showError("La spécialité doit être choisie.");
//                    return;
//                }
//                userData.setAnneeExperience(Integer.parseInt(anneeExperience.getText()));
//                userData.setSpecialite(SpecialiteC.valueOf(specialite.getValue()));
//                coachService.createCoach(userData);
//                showAlertSuccess("Coach", userData.getNom());
//                break;
//            case "Investisseur de produits":
//                if (isFieldEmpty(nomEntreprise.getText()) || isFieldEmpty(descriptionInvestisseur.getText()) ||
//                        isFieldEmpty(adresseInvestisseur.getText()) || !isValidInt(telephoneInvestisseur.getText())) {
//                    showError("Tous les champs doivent être remplis et le téléphone doit être un entier.");
//                    return;
//                }
//                // Vérification du téléphone
//                if (!telephoneInvestisseur.getText().matches("[0-9]{8}")) { // Téléphone valide avec 8 chiffres
//                    showError("Le téléphone doit contenir exactement 8 chiffres.");
//                    return;
//                }
//                userData.setNomEntreprise(nomEntreprise.getText());
//                userData.setDescriptionInvestisseur(descriptionInvestisseur.getText());
//                userData.setAdresseInvestisseur(adresseInvestisseur.getText());
//                userData.setTelephoneInvestisseur(telephoneInvestisseur.getText());
//                investisseurService.createInvestisseurProduit(userData);
//                showAlertSuccess("Investisseur de produit", userData.getNom());
//                break;
//            case "Créateur des événements":
//                if (isFieldEmpty(nomOrganisation.getText()) || isFieldEmpty(descriptionCreateur.getText()) ||
//                        isFieldEmpty(adresseCreateur.getText()) || !isValidInt(telephoneCreateur.getText())) {
//                    showError("Tous les champs doivent être remplis et le téléphone doit être un entier.");
//                    return;
//                }
//                // Vérification du téléphone
//                if (!telephoneCreateur.getText().matches("[0-9]{8}")) { // Téléphone valide avec 8 chiffres
//                    showError("Le téléphone doit contenir exactement 8 chiffres.");
//                    return;
//                }
//                userData.setNomOrganisation(nomOrganisation.getText());
//                userData.setDescriptionCreateur(descriptionCreateur.getText());
//                userData.setAdresseCreateur(adresseCreateur.getText());
//                userData.setTelephoneCreateur(telephoneCreateur.getText());
//                createurEvenementService.createCreateurEvenement(userData);
//                showAlertSuccess("Créateur d'événements", userData.getNom());
//
//                break;
//        }
//
//        updateDynamicFields(selectedRole, userData);
//    }
//
//
//    private void showAlertSuccess(String role, String nom) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Création réussie");
//        alert.setHeaderText(null);
//        alert.setContentText(role + " " + nom + " a été créé avec succès !");
//        alert.showAndWait();
//    }
//
//    private boolean isFieldEmpty(String value) {
//        return value == null || value.trim().isEmpty();
//    }
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//
//        return email != null && email.matches(emailRegex);
//    }
//    private boolean isValidFloat(String value) {
//        return value.matches("^-?\\d+(\\.\\d+)?$");
//    }
//
//    private boolean isValidInt(String value) {
//        return value.matches("^-?\\d+$");
//    }
//    private void showError(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Erreur");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    @FXML
//    private Button recoverbtn;
//
//
//
//    @FXML
//    private Button close;
//
//    public void close () {
//        javafx.application. Platform.exit();
//    }
//
//
//    @FXML
//    private Label edit_label1;
//
//    @FXML
//    private Label edit_label2;
//
//    @FXML
//    private AnchorPane login_form;
//
//    @FXML
//    private AnchorPane main_form;
//
//    @FXML
//    private Button si_boutonseconnecter;
//
//    @FXML
//    private TextField si_email;
//
//    @FXML
//    private PasswordField si_mdp;
//
//    @FXML
//    private Label si_seconnecter;
//
//    @FXML
//    private AnchorPane signup_form;
//
//
//    @FXML
//    private TextField su_email;
//
//    @FXML
//    private PasswordField su_mdp;
//
//    @FXML
//    private TextField su_nom;
//
//    @FXML
//    private TextField su_prenom;
//
//    @FXML
//    private Button su_signupbutton;
//
//    @FXML
//    private AnchorPane sub_form;
//
//
//
//    @FXML
//    private Button sub_loginbutton;
//
//    @FXML
//    private Label titreimage;
//    @FXML
//    private Button su_buttonajoutimage;
//    private File selectedImageFile;
//    @FXML
//    private void ouvrirResetPassword() {
//        try {
//            // Charger le fichier FXML de la réinitialisation du mot de passe
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reset_password.fxml"));
//            BorderPane root = loader.load();
//
//            // Créer une nouvelle scène et appliquer le CSS
//            Scene scene = new Scene(root, 400, 300);
//            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/reset_password.css")).toExternalForm());
//
//            // Ouvrir une nouvelle fenêtre
//            Stage stage = new Stage();
//            stage.setTitle("Réinitialisation du mot de passe");
//            stage.setScene(scene);
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//    @FXML
//    private ImageView photo;
//    @FXML
//    private Button sub_signupbutton;
//    @FXML
//    private ChoiceBox <String> choicebox;
//
//    @FXML private AnchorPane adherentFields;
//    @FXML private TextField poids;
//    @FXML private TextField taille;
//    @FXML private TextField age;
//    @FXML private ComboBox<String> genre;
//    @FXML private ComboBox<String> objectifPersonnel;
//    @FXML private ComboBox<String> niveauActivite;
//
//    @FXML private AnchorPane coachFields;
//    @FXML private TextField anneeExperience;
//
//    @FXML private ComboBox<String> specialite;
//
//
//    @FXML private AnchorPane investisseurFields;
//    @FXML private TextField nomEntreprise;
//    @FXML private TextField descriptionInvestisseur;
//    @FXML private TextField adresseInvestisseur;
//    @FXML private TextField telephoneInvestisseur;
//
//
//    @FXML private AnchorPane createurEventFields;
//    @FXML private TextField nomOrganisation;
//    @FXML private TextField descriptionCreateur;
//    @FXML private TextField adresseCreateur;
//    @FXML private TextField telephoneCreateur;
//
//    // Méthode pour charger reCAPTCHA avec gestion d'erreurs supplémentaires
////    private void loadRecaptcha() {
////        WebEngine webEngine = Captcha.getEngine();
////
////        // Activer JavaScript (important pour le reCAPTCHA)
////        webEngine.setJavaScriptEnabled(true);
////
////        // Modifier le User-Agent pour éviter d’être bloqué
////        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
////
////        String htmlContent = "<html>" +
////                "<head>" +
////                "<script src='https://www.google.com/recaptcha/enterprise.js?render=" + SITE_KEY + "'></script>" +
////                "</head>" +
////                "<body>" +
////                "<script>" +
////                "function onRecaptchaSuccess(token) {" +
////                "   window.java.onRecaptchaSuccess(token);" +
////                "}" +
////                "grecaptcha.enterprise.ready(function() {" +
////                "   grecaptcha.enterprise.execute('" + SITE_KEY + "', {action: 'login'}).then(onRecaptchaSuccess).catch(function(e) {" +
////                "       alert('Erreur reCAPTCHA: ' + e);" +  // Affiche l'erreur si elle se produit
////                "   });" +
////                "});" +
////                "</script>" +
////                "</body>" +
////                "</html>";
////
////        webEngine.loadContent(htmlContent);
////
////        // Ajout d'un pont Java-JavaScript
////        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
////            if (newState == Worker.State.SUCCEEDED) {
////                JSObject window = (JSObject) webEngine.executeScript("window");
////                window.setMember("java", new JavaBridge());
////                System.out.println("reCAPTCHA prêt");
////            } else if (newState == Worker.State.FAILED) {
////                System.out.println("Erreur de chargement de WebView.");
////            }
////        });
////
////        // Écouter les erreurs JavaScript dans WebView (debug)
////        webEngine.setOnError(event -> System.out.println("Erreur WebView : " + event.getMessage()));
////        webEngine.setOnAlert(event -> System.out.println("Alerte WebView : " + event.getData()));
////    }
////
////    // Vérifier les erreurs JavaScript et affichage d'alertes
////    private void onAlert(String message) {
////        System.out.println("Alert message: " + message);
////    }
////
////    // Méthode pour vérifier les champs et déclencher reCAPTCHA
////    private void checkFieldsAndTriggerRecaptcha() {
////        String email = si_email.getText();
////        String password = si_mdp.getText();
////
////        // Vérifiez que les champs ne sont pas vides
////        if (!email.isEmpty() && !password.isEmpty()) {
////            System.out.println("Champs remplis. Déclenchement de reCAPTCHA...");
////            // Vous pouvez ajouter ici une logique supplémentaire si nécessaire
////        }
////    }
////
////    // Classe interne pour gérer le pont Java-JavaScript
////    public class JavaBridge {
////        public void onRecaptchaSuccess(String token) {
////            System.out.println("Token reCAPTCHA reçu : " + token);
////
////            // Validez le token reCAPTCHA avec la clé secrète
////            validateRecaptchaToken(token, si_email.getText(), si_mdp.getText());
////        }
////    }
////
////    // Méthode pour valider le token reCAPTCHA
////    private void validateRecaptchaToken(String token, String email, String password) {
////        try {
////            // Créez le corps de la requête JSON pour l'API reCAPTCHA
////            JSONObject requestBody = new JSONObject();
////            requestBody.put("secret", SECRET_KEY);
////            requestBody.put("response", token);
////
////            // Envoyez la requête HTTP POST à l'API reCAPTCHA
////            OkHttpClient client = new OkHttpClient();
////            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
////            RequestBody body = RequestBody.create(requestBody.toString(), JSON);
////
////            Request request = new Request.Builder()
////                    .url("https://www.google.com/recaptcha/api/siteverify") // URL de l'API reCAPTCHA
////                    .post(body)
////                    .build();
////
////            Response response = client.newCall(request).execute();
////            String responseBody = response.body().string();
////            System.out.println("Réponse de l'API reCAPTCHA : " + responseBody);
////
////            // Analysez la réponse
////            JSONObject jsonResponse = new JSONObject(responseBody);
////            boolean success = jsonResponse.getBoolean("success");
////
////            if (success) {
////                System.out.println("reCAPTCHA validé avec succès.");
////                // Envoyez les données au backend
////                sendDataToBackend(email, password);
////            } else {
////                System.out.println("Échec de la validation reCAPTCHA.");
////                // Gérez l'échec de la validation (par exemple, affichez un message d'erreur)
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    // Méthode pour envoyer les données au backend
////    private void sendDataToBackend(String email, String password) {
////        try {
////            // Créez le corps de la requête JSON pour votre backend
////            JSONObject requestBody = new JSONObject();
////            requestBody.put("email", email);
////            requestBody.put("password", password);
////
////            // Envoyez la requête HTTP POST à votre backend
////            OkHttpClient client = new OkHttpClient();
////            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
////            RequestBody body = RequestBody.create(requestBody.toString(), JSON);
////
////            Request request = new Request.Builder()
////                    .url("http://localhost:8000") // Remplacez par l'URL de votre backend
////                    .post(body)
////                    .build();
////
////            Response response = client.newCall(request).execute();
////            System.out.println("Réponse du backend : " + response.body().string());
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//    private UserService userService = new UserService();
//
//    public void login(ActionEvent actionEvent) {
//        Alert alert;
//        String email = si_email.getText();
//        String password = si_mdp.getText();
//        if (email.isEmpty() || password.isEmpty()) {
//            alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Message d'erreur");
//            alert.setHeaderText(null);
//            alert.setContentText("Champs vides ! veuillez remplir tous les champs !");
//            alert.showAndWait();
//        } else {
//            boolean isAuthenticated = userService.login(email, password);
//            if (isAuthenticated) {
//                // Set the current user in the session
//                User currentUser = userService.getUserByEmailAndPassword(email, password);
//                Session.getInstance().setCurrentUser(currentUser);
//
//                alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Connexion réussie");
//                alert.setHeaderText(null);
//                alert.setContentText("Bienvenue, " + currentUser.getNom() + "!");
//                alert.showAndWait();
//
//                // Load the appropriate home page based on the user's ID
//                if (currentUser.getId() == 1) {
//                    GoToHomeAdmin(actionEvent);
//                } else {
//                    GoToHome(actionEvent);
//                }
//
//            } else {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Message d'erreur");
//                alert.setHeaderText(null);
//                alert.setContentText("Échec de connexion Email ou mot de passe incorrect !");
//                alert.showAndWait();
//            }
//        }
//    }
//    void GoToHome(ActionEvent actionEvent) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
//            Parent root = loader.load();
//            ((Button) actionEvent.getSource()).getScene().setRoot(root);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    void GoToHomeAdmin(ActionEvent actionEvent) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
//            Parent root = loader.load();
//            ((Button) actionEvent.getSource()).getScene().setRoot(root);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private void showAlert(Alert.AlertType alertType, String title, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//    @FXML
//
//
//    public void signupSlider () {
//
//        TranslateTransition slider1 = new TranslateTransition();
//        slider1.setNode(sub_form);
//        slider1.setToX(600);
//        slider1.setDuration(Duration.seconds(.5));
//        slider1.play();
//        slider1.setOnFinished((ActionEvent event) -> {
//            edit_label1.setText("Accéder à votre compte");
//            edit_label2.setText("Se connecter");
//            login_form.setVisible(false);
//            sub_signupbutton.setVisible(false);
//            sub_loginbutton.setVisible(true);
//            signup_form.setVisible(true);
//
//
//        });
//    }
////    @FXML
////    private WebView Captcha;
//
//    public void loginSlider() {
//
//        TranslateTransition slider1 = new TranslateTransition () ;
//        slider1.setNode(sub_form);
//        slider1.setToX(0);
//        slider1.setDuration (Duration.seconds(.5));
//        slider1.play();
//
//        slider1.setOnFinished((ActionEvent event) -> {
//            edit_label1.setText("Pemière fois avec nous ?");
//            edit_label2.setText("Créer votre compte");
//            login_form.setVisible(true);
//            sub_signupbutton.setVisible(true);
//            sub_loginbutton.setVisible(false);
//            login_form.setVisible(true);
//            signup_form.setVisible(false);
//        });}}
//
////    @FXML
////    private void handleSelectCV() {
////        FileChooser fileChooser = new FileChooser();
////        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
////        titreCV.setVisible(false);
////        // Ouvrir la boîte de dialogue
////        Stage stage = (Stage) su_buttonajoutCV.getScene().getWindow();
////        File file = fileChooser.showOpenDialog(stage);
////
////        if (file != null) {
////            selectedCVFile = file;
////            titreCV.setText(file.getName());
////            titreCV.setVisible(true);
////        } else {
////            titreCV.setVisible(false);
////        }
////    }
//
//
//
//
//
////private void loadRecaptcha() {
////    WebEngine webEngine = Captcha.getEngine();
////
////    // Activer JavaScript (important pour le reCAPTCHA)
////    webEngine.setJavaScriptEnabled(true);
////
////    // Modifier le User-Agent pour éviter d’être bloqué
////    webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; Windows 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
////
////    String htmlContent = "<html>" +
////            "<head>" +
////            "<script src='https://www.google.com/recaptcha/enterprise.js?render=" + SITE_KEY + "'></script>" +
////            "</head>" +
////            "<body>" +
////            "<script>" +
////            "function onRecaptchaSuccess(token) {" +
////            "   window.java.onRecaptchaSuccess(token);" +
////            "}" +
////            "grecaptcha.enterprise.ready(function() {" +
////            "   grecaptcha.enterprise.execute('" + SITE_KEY + "', {action: 'login'}).then(onRecaptchaSuccess).catch(function(e) {" +
////            "       alert('Erreur reCAPTCHA: ' + e);" +  // Affiche l'erreur si elle se produit
////            "   });" +
////            "});" +
////            "</script>" +
////            "</body>" +
////            "</html>";
////
////    // Charger le contenu HTML dans WebView
////    webEngine.loadContent(htmlContent);
////
////    // Ajout d'un pont Java-JavaScript
////    JSObject window = (JSObject) webEngine.executeScript("window");
////    window.setMember("java", new JavaBridge());
////
////    System.out.println("reCAPTCHA chargé sans listeners.");
////}










package Controllers;

import Models.*;
import Services.*;
import Services.UserService;
import Utils.MyDb;
import Utils.Session;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent; // Utilisez ceci pour JavaFX
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import okhttp3.*;
import org.json.JSONObject;


import java.io.*;
import java.net.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import java.io.IOException;

public class SigninUpController  implements Initializable {

    private static final String SITE_KEY = "6LdgZeUqAAAAAOlPg-W703BS7aIJ5skRdwoppZ5M";
    private static final String SECRET_KEY = "6LdgZeUqAAAAANOZ6ybiFaHuKMCtSNRLnJZzFa-1";

    public SigninUpController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCaptcha();
        // loadRecaptcha();
        //si_email.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsAndTriggerRecaptcha());
        // si_mdp.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsAndTriggerRecaptcha());
        su_nom.getStyleClass().clear();  // Retirer tous les styles existants
        su_nom.getStyleClass().add("textfield");
        su_nom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) { // Vérifie que seuls des lettres sont autorisées
                su_nom.getStyleClass().remove("textfield");
                su_nom.getStyleClass().add("text-field-error");
            } else {
                // Appliquer le style normal
                su_nom.getStyleClass().remove("text-field-error");
                su_nom.getStyleClass().add("textfield");}
        });
        su_prenom.getStyleClass().clear();  // Retirer tous les styles existants
        su_prenom.getStyleClass().add("textfield");
        su_prenom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) { // Vérifie que seuls des lettres sont autorisées
                su_prenom.getStyleClass().remove("textfield");
                su_prenom.getStyleClass().add("text-field-error");
            } else {
                // Appliquer le style normal
                su_prenom.getStyleClass().remove("text-field-error");
                su_prenom.getStyleClass().add("textfield");}
        });
        age.getStyleClass().clear();  // Retirer tous les styles existants
        age.getStyleClass().add("textfield");

        age.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) { // Vérifie que seuls des chiffres sont autorisés
                age.getStyleClass().remove("textfield");
                age.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                age.getStyleClass().remove("text-field-error");
                age.getStyleClass().add("textfield");  // Applique le style normal
            }
        });
        anneeExperience.getStyleClass().clear();  // Retirer tous les styles existants
        anneeExperience.getStyleClass().add("textfield");

        anneeExperience.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) { // Vérifie que seuls des chiffres sont autorisés
                anneeExperience.getStyleClass().remove("textfield");
                anneeExperience.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                anneeExperience.getStyleClass().remove("text-field-error");
                anneeExperience.getStyleClass().add("textfield");  // Applique le style normal
            }
        });
        telephoneCreateur.getStyleClass().clear();  // Retirer tous les styles existants
        telephoneCreateur.getStyleClass().add("textfield");
        telephoneCreateur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{8}")) { // Vérifie que seuls des chiffres sont autorisés
                telephoneCreateur.getStyleClass().remove("textfield");
                telephoneCreateur.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                telephoneCreateur.getStyleClass().remove("text-field-error");
                telephoneCreateur.getStyleClass().add("textfield");  // Applique le style normal
            }
        });
        telephoneInvestisseur.getStyleClass().clear();  // Retirer tous les styles existants
        telephoneInvestisseur.getStyleClass().add("textfield");
        telephoneInvestisseur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{8}")) { // Vérifie que seuls des chiffres sont autorisés
                telephoneInvestisseur.getStyleClass().remove("textfield");
                telephoneInvestisseur.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                telephoneInvestisseur.getStyleClass().remove("text-field-error");
                telephoneInvestisseur.getStyleClass().add("textfield");  // Applique le style normal
            }
        });
        su_email.getStyleClass().clear();  // Retirer tous les styles existants
        su_email.getStyleClass().add("textfield");
        su_email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // Vérifie le format de l'email
                su_email.getStyleClass().remove("textfield");
                su_email.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                su_email.getStyleClass().remove("text-field-error");
                su_email.getStyleClass().add("textfield");  // Applique le style normal
            }
        });
        si_email.getStyleClass().clear();  // Retirer tous les styles existants
        si_email.getStyleClass().add("textfield");
        si_email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // Vérifie le format de l'email
                si_email.getStyleClass().remove("textfield");
                si_email.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                si_email.getStyleClass().remove("text-field-error");
                si_email.getStyleClass().add("textfield");  // Applique le style normal
            }
        });

        taille.getStyleClass().clear();  // Retirer tous les styles existants
        taille.getStyleClass().add("textfield");
        taille.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) { // Vérifie que seuls des chiffres et un point décimal sont autorisés
                taille.getStyleClass().remove("textfield");
                taille.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                taille.getStyleClass().remove("text-field-error");
                taille.getStyleClass().add("textfield");  // Applique le style normal
            }
        });
        poids.getStyleClass().clear();  // Retirer tous les styles existants
        poids.getStyleClass().add("textfield");

        poids.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) { // Vérifie que seuls des chiffres et un point décimal sont autorisés
                poids.getStyleClass().remove("textfield");
                poids.getStyleClass().add("text-field-error");  // Applique le style erreur
            } else {
                // Appliquer le style normal
                poids.getStyleClass().remove("text-field-error");
                poids.getStyleClass().add("textfield");  // Applique le style normal
            }
        });





        choicebox.getItems().addAll("Adhérent", "Coach", "Investisseur de produits", "Créateur des événements");
        genre.getItems().setAll(Arrays.stream(GenreG.values()).map(Enum::name).collect(Collectors.toList())
        );
        objectifPersonnel.getItems().setAll(Arrays.stream(ObjP.values()).map(Enum::name).collect(Collectors.toList())
        );
        niveauActivite.getItems().setAll(
                Arrays.stream(NiveauA.values()).map(Enum::name).collect(Collectors.toList())
        );
        specialite.getItems().setAll(Arrays.stream(SpecialiteC.values()).map(Enum::name).collect(Collectors.toList())
        );





        choicebox.setOnAction(event -> {
            String selectedRole = choicebox.getValue();
            System.out.println("Role selected: " + selectedRole);
            UserData userData = new UserData();  // On crée un objet userData pour le rôle sélectionné
            updateDynamicFields(selectedRole, userData); // Mettre à jour les champs dynamiques
        });
    }

    private final AdherentService adherentService = new AdherentService();
    private final CoachService coachService = new CoachService();
    private final InvestisseurProduitService investisseurService = new InvestisseurProduitService();
    private final CreateurEvenementService createurEvenementService = new CreateurEvenementService();

    private void updateDynamicFields(String selectedRole, UserData userData) {
        // Masquer tous les champs dynamiques
        adherentFields.setVisible(false);
        coachFields.setVisible(false);
        investisseurFields.setVisible(false);
        createurEventFields.setVisible(false);


        // Afficher les champs correspondant au rôle sélectionné
        switch (selectedRole) {
            case "Adhérent":
                adherentFields.setVisible(true);
//                    adherentService.createAdherent(userData);
                break;
            case "Coach":
                coachFields.setVisible(true);
//                    coachService.createCoach(userData);
                break;
            case "Investisseur de produits":
                investisseurFields.setVisible(true);
//                    investisseurService.createInvestisseurProduit(userData);
                break;
            case "Créateur des événements":
                createurEventFields.setVisible(true);
//                    createurEvenementService.createCreateurEvenement(userData);
                break;
        }
    }


    @FXML
    private void handleSuivantButtonAction() {

        UserData userData = new UserData();
        // Vérification des champs communs
        if (isFieldEmpty(su_nom.getText()) || isFieldEmpty(su_prenom.getText()) ||
                isFieldEmpty(su_email.getText()) || isFieldEmpty(su_mdp.getText())) {
            showError("Tous les champs obligatoires doivent être remplis.");
            return;
        }

        if (!isValidEmail(su_email.getText())) {
            showError("Format d'email invalide.");
            return;
        }
        // Contrôle du nom : uniquement des lettres
        if (!su_nom.getText().matches("[a-zA-Z]*")) {
            showError("Le nom ne peut contenir que des lettres.");
            return;
        }

        // Contrôle du prénom : uniquement des lettres
        if (!su_prenom.getText().matches("[a-zA-Z]*")) {
            showError("Le prénom ne peut contenir que des lettres.");
            return;
        }

        userData.setNom(su_nom.getText());
        userData.setPrenom(su_prenom.getText());
        userData.setEmail(su_email.getText());
        userData.setMDP(su_mdp.getText());

        String selectedRole = choicebox.getValue();
        switch (selectedRole) {
            case "Adhérent":
                if (!isValidFloat(poids.getText()) || !isValidFloat(taille.getText()) || !isValidInt(age.getText())) {
                    showError("Poids et taille doivent être des nombres réels. L'âge doit être un entier.");
                    return;
                }
                // Contrôle sur Poids et Taille
                if (!poids.getText().matches("[0-9]+(\\.[0-9]+)?")) { // Vérifie que le poids est un nombre réel
                    showError("Le poids doit être un nombre réel.");
                    return;
                }

                if (!taille.getText().matches("[0-9]+(\\.[0-9]+)?")) { // Vérifie que la taille est un nombre réel
                    showError("La taille doit être un nombre réel.");
                    return;
                }

                // Contrôle sur Âge
                if (!age.getText().matches("[0-9]+")) { // Vérifie que l'âge est un entier
                    showError("L'âge doit être un nombre entier.");
                    return;
                }
                userData.setPoids(Float.parseFloat(poids.getText()));
                userData.setTaille(Float.parseFloat(taille.getText()));
                userData.setAge(Integer.parseInt(age.getText()));
                userData.setGenre(GenreG.valueOf(genre.getValue()));
                userData.setObjectifPersonnel(ObjP.valueOf(objectifPersonnel.getValue()));
                userData.setNiveauActivite(NiveauA.valueOf(niveauActivite.getValue()));
                adherentService.createAdherent(userData);
                showAlertSuccess("Adhérent", userData.getNom());

                break;
            case "Coach":
                if (!isValidInt(anneeExperience.getText())) {
                    showError("L'année d'expérience doit être un nombre entier.");
                    return;
                }
                // Vérification de la spécialité
                if (specialite.getValue() == null) {
                    showError("La spécialité doit être choisie.");
                    return;
                }
                userData.setAnneeExperience(Integer.parseInt(anneeExperience.getText()));
                userData.setSpecialite(SpecialiteC.valueOf(specialite.getValue()));
                coachService.createCoach(userData);
                showAlertSuccess("Coach", userData.getNom());
                break;
            case "Investisseur de produits":
                if (isFieldEmpty(nomEntreprise.getText()) || isFieldEmpty(descriptionInvestisseur.getText()) ||
                        isFieldEmpty(adresseInvestisseur.getText()) || !isValidInt(telephoneInvestisseur.getText())) {
                    showError("Tous les champs doivent être remplis et le téléphone doit être un entier.");
                    return;
                }
                // Vérification du téléphone
                if (!telephoneInvestisseur.getText().matches("[0-9]{8}")) { // Téléphone valide avec 8 chiffres
                    showError("Le téléphone doit contenir exactement 8 chiffres.");
                    return;
                }
                userData.setNomEntreprise(nomEntreprise.getText());
                userData.setDescriptionInvestisseur(descriptionInvestisseur.getText());
                userData.setAdresseInvestisseur(adresseInvestisseur.getText());
                userData.setTelephoneInvestisseur(telephoneInvestisseur.getText());
                investisseurService.createInvestisseurProduit(userData);
                showAlertSuccess("Investisseur de produit", userData.getNom());
                break;
            case "Créateur des événements":
                if (isFieldEmpty(nomOrganisation.getText()) || isFieldEmpty(descriptionCreateur.getText()) ||
                        isFieldEmpty(adresseCreateur.getText()) || !isValidInt(telephoneCreateur.getText())) {
                    showError("Tous les champs doivent être remplis et le téléphone doit être un entier.");
                    return;
                }
                // Vérification du téléphone
                if (!telephoneCreateur.getText().matches("[0-9]{8}")) { // Téléphone valide avec 8 chiffres
                    showError("Le téléphone doit contenir exactement 8 chiffres.");
                    return;
                }
                userData.setNomOrganisation(nomOrganisation.getText());
                userData.setDescriptionCreateur(descriptionCreateur.getText());
                userData.setAdresseCreateur(adresseCreateur.getText());
                userData.setTelephoneCreateur(telephoneCreateur.getText());
                createurEvenementService.createCreateurEvenement(userData);
                showAlertSuccess("Créateur d'événements", userData.getNom());

                break;
        }

        updateDynamicFields(selectedRole, userData);
    }


    private void showAlertSuccess(String role, String nom) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Création réussie");
        alert.setHeaderText(null);
        alert.setContentText(role + " " + nom + " a été créé avec succès !");
        alert.showAndWait();
    }

    private boolean isFieldEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return email != null && email.matches(emailRegex);
    }
    private boolean isValidFloat(String value) {
        return value.matches("^-?\\d+(\\.\\d+)?$");
    }

    private boolean isValidInt(String value) {
        return value.matches("^-?\\d+$");
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private Button recoverbtn;



    @FXML
    private Button close;

    public void close () {
        javafx.application. Platform.exit();
    }


    @FXML
    private Label edit_label1;

    @FXML
    private Label edit_label2;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button si_boutonseconnecter;

    @FXML
    private TextField si_email;

    @FXML
    private PasswordField si_mdp;

    @FXML
    private Label si_seconnecter;

    @FXML
    private AnchorPane signup_form;


    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_mdp;

    @FXML
    private TextField su_nom;

    @FXML
    private TextField su_prenom;

    @FXML
    private Button su_signupbutton;

    @FXML
    private AnchorPane sub_form;



    @FXML
    private Button sub_loginbutton;

    @FXML
    private Label titreimage;
    @FXML
    private Button su_buttonajoutimage;
    private File selectedImageFile;

    private String captchaToken = null;
    @FXML
    private void ouvrirResetPassword() {
        try {
            // Charger le fichier FXML de la réinitialisation du mot de passe
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reset_password.fxml"));
            BorderPane root = loader.load();

            // Créer une nouvelle scène et appliquer le CSS
            Scene scene = new Scene(root, 400, 300);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/reset_password.css")).toExternalForm());

            // Ouvrir une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setTitle("Réinitialisation du mot de passe");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    @FXML
    private ImageView photo;
    @FXML
    private Button sub_signupbutton;
    @FXML
    private ChoiceBox <String> choicebox;

    @FXML private AnchorPane adherentFields;
    @FXML private TextField poids;
    @FXML private TextField taille;
    @FXML private TextField age;
    @FXML private ComboBox<String> genre;
    @FXML private ComboBox<String> objectifPersonnel;
    @FXML private ComboBox<String> niveauActivite;

    @FXML private AnchorPane coachFields;
    @FXML private TextField anneeExperience;

    @FXML private ComboBox<String> specialite;



    @FXML private AnchorPane investisseurFields;
    @FXML private TextField nomEntreprise;
    @FXML private TextField descriptionInvestisseur;
    @FXML private TextField adresseInvestisseur;
    @FXML private TextField telephoneInvestisseur;


    @FXML private AnchorPane createurEventFields;
    @FXML private TextField nomOrganisation;
    @FXML private TextField descriptionCreateur;
    @FXML private TextField adresseCreateur;
    @FXML private TextField telephoneCreateur;


    // Méthode pour charger reCAPTCHA avec gestion d'erreurs supplémentaires
//    private void loadRecaptcha() {
//        WebEngine webEngine = Captcha.getEngine();
//
//        // Activer JavaScript (important pour le reCAPTCHA)
//        webEngine.setJavaScriptEnabled(true);
//
//        // Modifier le User-Agent pour éviter d’être bloqué
//        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
//
//        String htmlContent = "<html>" +
//                "<head>" +
//                "<script src='https://www.google.com/recaptcha/enterprise.js?render=" + SITE_KEY + "'></script>" +
//                "</head>" +
//                "<body>" +
//                "<script>" +
//                "function onRecaptchaSuccess(token) {" +
//                "   window.java.onRecaptchaSuccess(token);" +
//                "}" +
//                "grecaptcha.enterprise.ready(function() {" +
//                "   grecaptcha.enterprise.execute('" + SITE_KEY + "', {action: 'login'}).then(onRecaptchaSuccess).catch(function(e) {" +
//                "       alert('Erreur reCAPTCHA: ' + e);" +  // Affiche l'erreur si elle se produit
//                "   });" +
//                "});" +
//                "</script>" +
//                "</body>" +
//                "</html>";
//
//        webEngine.loadContent(htmlContent);
//
//        // Ajout d'un pont Java-JavaScript
//        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                JSObject window = (JSObject) webEngine.executeScript("window");
//                window.setMember("java", new JavaBridge());
//                System.out.println("reCAPTCHA prêt");
//            } else if (newState == Worker.State.FAILED) {
//                System.out.println("Erreur de chargement de WebView.");
//            }
//        });
//
//        // Écouter les erreurs JavaScript dans WebView (debug)
//        webEngine.setOnError(event -> System.out.println("Erreur WebView : " + event.getMessage()));
//        webEngine.setOnAlert(event -> System.out.println("Alerte WebView : " + event.getData()));
//    }
//
//    // Vérifier les erreurs JavaScript et affichage d'alertes
//    private void onAlert(String message) {
//        System.out.println("Alert message: " + message);
//    }
//
//    // Méthode pour vérifier les champs et déclencher reCAPTCHA
//    private void checkFieldsAndTriggerRecaptcha() {
//        String email = si_email.getText();
//        String password = si_mdp.getText();
//
//        // Vérifiez que les champs ne sont pas vides
//        if (!email.isEmpty() && !password.isEmpty()) {
//            System.out.println("Champs remplis. Déclenchement de reCAPTCHA...");
//            // Vous pouvez ajouter ici une logique supplémentaire si nécessaire
//        }
//    }
//
//    // Classe interne pour gérer le pont Java-JavaScript
//    public class JavaBridge {
//        public void onRecaptchaSuccess(String token) {
//            System.out.println("Token reCAPTCHA reçu : " + token);
//
//            // Validez le token reCAPTCHA avec la clé secrète
//            validateRecaptchaToken(token, si_email.getText(), si_mdp.getText());
//        }
//    }
//
//    // Méthode pour valider le token reCAPTCHA
//    private void validateRecaptchaToken(String token, String email, String password) {
//        try {
//            // Créez le corps de la requête JSON pour l'API reCAPTCHA
//            JSONObject requestBody = new JSONObject();
//            requestBody.put("secret", SECRET_KEY);
//            requestBody.put("response", token);
//
//            // Envoyez la requête HTTP POST à l'API reCAPTCHA
//            OkHttpClient client = new OkHttpClient();
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//            RequestBody body = RequestBody.create(requestBody.toString(), JSON);
//
//            Request request = new Request.Builder()
//                    .url("https://www.google.com/recaptcha/api/siteverify") // URL de l'API reCAPTCHA
//                    .post(body)
//                    .build();
//
//            Response response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            System.out.println("Réponse de l'API reCAPTCHA : " + responseBody);
//
//            // Analysez la réponse
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            boolean success = jsonResponse.getBoolean("success");
//
//            if (success) {
//                System.out.println("reCAPTCHA validé avec succès.");
//                // Envoyez les données au backend
//                sendDataToBackend(email, password);
//            } else {
//                System.out.println("Échec de la validation reCAPTCHA.");
//                // Gérez l'échec de la validation (par exemple, affichez un message d'erreur)
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Méthode pour envoyer les données au backend
//    private void sendDataToBackend(String email, String password) {
//        try {
//            // Créez le corps de la requête JSON pour votre backend
//            JSONObject requestBody = new JSONObject();
//            requestBody.put("email", email);
//            requestBody.put("password", password);
//
//            // Envoyez la requête HTTP POST à votre backend
//            OkHttpClient client = new OkHttpClient();
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//            RequestBody body = RequestBody.create(requestBody.toString(), JSON);
//
//            Request request = new Request.Builder()
//                    .url("http://localhost:8000") // Remplacez par l'URL de votre backend
//                    .post(body)
//                    .build();
//
//            Response response = client.newCall(request).execute();
//            System.out.println("Réponse du backend : " + response.body().string());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private WebView captchaWebView;



    //    private void setupCaptcha() {
//        WebEngine webEngine = captchaWebView.getEngine();
//        webEngine.setJavaScriptEnabled(true); // Enable JavaScript
//
//        String siteKey = "6LdgZeUqAAAAAOlPg-W703BS7aIJ5skRdwoppZ5M";
//        String captchaHTML = "<html>" +
//                "<head>" +
//                "<script src='https://www.google.com/recaptcha/enterprise.js' async defer></script>" +
//
//                "</head>" +
//                "<body>" +
//                "<form action='?' method='POST'>" +
//                "<div class='g-recaptcha' data-sitekey='" + siteKey + "'></div>" +
//                "<br/>" +
//                "<input type='submit' value='Submit'>" +
//                "</form>" +
//                "</body>" +
//                "</html>";
//
//        webEngine.loadContent(captchaHTML);
//
//        // Debugging: Listen for errors
//        webEngine.setOnError(event -> {
//            System.err.println("WebView Error: " + event.getMessage());
//        });
//
//        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                System.out.println("WebView Loaded Successfully");
//            } else if (newState == Worker.State.FAILED) {
//                System.err.println("WebView Load Failed: " + webEngine.getLoadWorker().getException());
//            }
//        });
//    }
//    private void setupCaptcha() {
//        WebEngine webEngine = captchaWebView.getEngine();
//        webEngine.setJavaScriptEnabled(true); // Enable JavaScript
//
////        String siteKey = "6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD"; // Replace with your actual site key
//        String siteKey = "6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD";
//        String captchaHTML = "<html>" +
//                "<head>" +
//                "<script src='https://www.google.com/recaptcha/enterprise.js' async defer></script>" +
//                "</head>" +
//                "<body>" +
//                "<form action='?' method='POST'>" +
//                "<div class='g-recaptcha' data-sitekey='" + siteKey + "'></div>" +
//                "<br/>" +
//                "<input type='submit' value='Submit'>" +
//                "</form>" +
//                "</body>" +
//                "</html>";
//
//        webEngine.loadContent(captchaHTML);
//
//        // Debugging: Listen for errors
//        webEngine.setOnError(event -> {
//            System.err.println("WebView Error: " + event.getMessage());
//        });
//
//        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                System.out.println("WebView Loaded Successfully");
//            } else if (newState == Worker.State.FAILED) {
//                System.err.println("WebView Load Failed: " + webEngine.getLoadWorker().getException());
//            }
//        });
//    }

//    private void setupCaptcha() {
//        WebEngine webEngine = captchaWebView.getEngine();
//        webEngine.setJavaScriptEnabled(true);
//
//        String siteKey = "6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD";
//        String captchaHTML = "<!DOCTYPE html>"
//                + "<html>"
//                + "<head>"
//                + "    <meta charset='UTF-8'>"
//                + "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
//                + "    <script src='https://www.google.com/recaptcha/api.js' async defer></script>"
//                + "</head>"
//                + "<body>"
//                + "    <form>"
//                + "        <div class='g-recaptcha' data-sitekey='" + siteKey + "'></div>"
//                + "    </form>"
//                + "</body>"
//                + "</html>";
//
//        webEngine.loadContent(captchaHTML);
//
//        // Ensure WebView has proper dimensions
//        captchaWebView.setMinWidth(400);
//        captchaWebView.setMinHeight(180);
//
//        // Error handling
//        webEngine.setOnError(event -> {
//            System.err.println("WebView Error: " + event.getMessage());
//        });
//
//        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                System.out.println("WebView Loaded Successfully");
//            } else if (newState == Worker.State.FAILED) {
//                System.err.println("WebView Load Failed: " + webEngine.getLoadWorker().getException());
//            }
//        });
//    }


//    private void setupCaptcha() {
//        WebEngine webEngine = captchaWebView.getEngine();
//        webEngine.setJavaScriptEnabled(true);
//
////        String siteKey = "6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD";
//        String siteKey = "6LdgZeUqAAAAAOlPg-W703BS7aIJ5skRdwoppZ5M";
//        String captchaHTML = "<!DOCTYPE html>"
//                + "<html>"
//                + "<head>"
//                + "    <meta http-equiv=\"Content-Security-Policy\" content=\"default-src 'self' 'unsafe-inline' 'unsafe-eval' https://www.google.com https://www.gstatic.com;\">"
//                + "    <meta charset='UTF-8'>"
//                + "    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>"
//                + "</head>"
//                + "<body>"
//                + "    <div class='g-recaptcha' data-sitekey='" + siteKey + "' data-theme='light'></div>"
//                + "</body>"
//                + "</html>";
//
//        // Load through a local URL to trigger proper domain validation
//        try {
//            File tempFile = File.createTempFile("captcha", ".html");
//            Files.write(tempFile.toPath(), captchaHTML.getBytes(StandardCharsets.UTF_8));
//            webEngine.load(tempFile.toURI().toURL().toExternalForm());
//            webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//
//    private void setupCaptcha() {
//        WebEngine webEngine = captchaWebView.getEngine();  // Assuming captchaWebView is your WebView component
//        webEngine.setJavaScriptEnabled(true);
//
//        // URL of the captcha page served by XAMPP
//        String captchaUrl = "http://localhost/captcha.html";  // Ensure XAMPP is running
//
//        // Load the captcha page into the WebView
//        webEngine.load(captchaUrl);
//    }


    private CaptchaBridge captchaBridge = new CaptchaBridge();  // Store bridge instance

    private void setupCaptcha() {
        WebEngine webEngine = captchaWebView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        String captchaUrl = "http://localhost/captcha.html";
        webEngine.load(captchaUrl);

        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("javaFX", captchaBridge);  // Store bridge reference
    }




    private UserService userService = new UserService();

    public void login(ActionEvent actionEvent) {
        Alert alert;
        String email = si_email.getText();
        String password = si_mdp.getText();
        if (captchaBridge.isHuman()) {  // Check reCAPTCHA verification
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vérification échouée");
            alert.setHeaderText(null);
            alert.setContentText("❌ Échec de la vérification reCAPTCHA. Veuillez prouver que vous n'êtes pas un robot.");
            alert.showAndWait();
            return;
        }

        if (email.isEmpty() || password.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Champs vides ! veuillez remplir tous les champs !");
            alert.showAndWait();
        } else {
            boolean isAuthenticated = userService.login(email, password);
            if (isAuthenticated) {
                // Set the current user in the session
                User currentUser = userService.getUserByEmailAndPassword(email, password);
                Session.getInstance().setCurrentUser(currentUser);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connexion réussie");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenue, " + currentUser.getNom() + "!");
                alert.showAndWait();

                // Load the appropriate home page based on the user's ID
                if (currentUser.getId() == 1) {
                    GoToHomeAdmin(actionEvent);
                } else {
                    GoToHome(actionEvent);
                }

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Échec de connexion Email ou mot de passe incorrect !");
                alert.showAndWait();
            }
        }
    }
    void GoToHome(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void GoToHomeAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML


    public void signupSlider () {

        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(600);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();
        slider1.setOnFinished((ActionEvent event) -> {
            edit_label1.setText("Accéder à votre compte");
            edit_label2.setText("Se connecter");
            login_form.setVisible(false);
            sub_signupbutton.setVisible(false);
            sub_loginbutton.setVisible(true);
            signup_form.setVisible(true);


        });
    }
//    @FXML
//    private WebView Captcha;

    public void loginSlider() {

        TranslateTransition slider1 = new TranslateTransition () ;
        slider1.setNode(sub_form);
        slider1.setToX(0);
        slider1.setDuration (Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished((ActionEvent event) -> {
            edit_label1.setText("Pemière fois avec nous ?");
            edit_label2.setText("Créer votre compte");
            login_form.setVisible(true);
            sub_signupbutton.setVisible(true);
            sub_loginbutton.setVisible(false);
            login_form.setVisible(true);
            signup_form.setVisible(false);
        });}}

//    @FXML
//    private void handleSelectCV() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
//        titreCV.setVisible(false);
//        // Ouvrir la boîte de dialogue
//        Stage stage = (Stage) su_buttonajoutCV.getScene().getWindow();
//        File file = fileChooser.showOpenDialog(stage);
//
//        if (file != null) {
//            selectedCVFile = file;
//            titreCV.setText(file.getName());
//            titreCV.setVisible(true);
//        } else {
//            titreCV.setVisible(false);
//        }
//    }





//private void loadRecaptcha() {
//    WebEngine webEngine = Captcha.getEngine();
//
//    // Activer JavaScript (important pour le reCAPTCHA)
//    webEngine.setJavaScriptEnabled(true);
//
//    // Modifier le User-Agent pour éviter d’être bloqué
//    webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; Windows 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
//
//    String htmlContent = "<html>" +
//            "<head>" +
//            "<script src='https://www.google.com/recaptcha/enterprise.js?render=" + SITE_KEY + "'></script>" +
//            "</head>" +
//            "<body>" +
//            "<script>" +
//            "function onRecaptchaSuccess(token) {" +
//            "   window.java.onRecaptchaSuccess(token);" +
//            "}" +
//            "grecaptcha.enterprise.ready(function() {" +
//            "   grecaptcha.enterprise.execute('" + SITE_KEY + "', {action: 'login'}).then(onRecaptchaSuccess).catch(function(e) {" +
//            "       alert('Erreur reCAPTCHA: ' + e);" +  // Affiche l'erreur si elle se produit
//            "   });" +
//            "});" +
//            "</script>" +
//            "</body>" +
//            "</html>";
//
//    // Charger le contenu HTML dans WebView
//    webEngine.loadContent(htmlContent);
//
//    // Ajout d'un pont Java-JavaScript
//    JSObject window = (JSObject) webEngine.executeScript("window");
//    window.setMember("java", new JavaBridge());
//
//    System.out.println("reCAPTCHA chargé sans listeners.");
//}
