package Controllers;

import Models.Reclamation;
import Models.typeR;
import Services.ReclamationService;
import Utils.MyDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import Utils.ValidationUtils;

public class UserReclamationController implements Initializable {
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<typeR> typeComboBox;
    @FXML private Button submitButton;
    @FXML private Button clearButton;
    @FXML private TextField searchField;
    @FXML private Button refreshButton;
    @FXML private ListView<Reclamation> reclamationTable;
    @FXML private Button profileButton;
    @FXML private HBox searchContainer;

    private final ReclamationService reclamationService = new ReclamationService();
    private ObservableList<Reclamation> reclamationsList = FXCollections.observableArrayList();
    private FilteredList<Reclamation> filteredReclamations;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize ComboBox
        typeComboBox.getItems().addAll(typeR.values());
        
        // Setup search field with icon
        FontIcon searchIcon = new FontIcon(FontAwesomeSolid.SEARCH);
        searchIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        searchIcon.setIconSize(16);
        
        // Add search icon to search container
        searchContainer.getChildren().add(0, searchIcon);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setPadding(new Insets(0, 10, 0, 10));
        
        // Style the search field
        searchField.getStyleClass().add("search-box");
        searchField.setPromptText("Rechercher dans vos réclamations...");
        
        // Setup refresh button with icon
        FontIcon refreshIcon = new FontIcon(FontAwesomeSolid.SYNC_ALT);
        refreshIcon.setIconColor(javafx.scene.paint.Color.web("#3498db"));
        refreshButton.setGraphic(refreshIcon);
        refreshButton.getStyleClass().add("modern-refresh-button");
        
        // Setup profile button with icon
        FontIcon profileIcon = new FontIcon(FontAwesomeSolid.USER_CIRCLE);
        profileIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        profileButton.setGraphic(profileIcon);
        profileButton.getStyleClass().add("profile-button");
        
        // Setup custom cell factory for ListView
        reclamationTable.setCellFactory(lv -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create the main container
                    VBox container = new VBox();
                    container.getStyleClass().add("reclamation-item");
                    container.setSpacing(15);

                    // Header with type and date
                    HBox header = new HBox();
                    header.getStyleClass().add("reclamation-header");
                    header.setAlignment(Pos.CENTER_LEFT);
                    header.setSpacing(15);

                    // Type with icon
                    HBox typeBox = new HBox(8);
                    typeBox.setAlignment(Pos.CENTER_LEFT);
                    
                    FontIcon typeIcon = new FontIcon(FontAwesomeSolid.TAG);
                    typeIcon.setIconColor(javafx.scene.paint.Color.web("#3498db"));
                    
                    Label typeLabel = new Label(reclamation.getType().toString());
                    typeLabel.getStyleClass().add("reclamation-type");
                    typeBox.getChildren().addAll(typeIcon, typeLabel);

                    // Date with icon
                    HBox dateBox = new HBox(8);
                    dateBox.setAlignment(Pos.CENTER_LEFT);
                    
                    FontIcon dateIcon = new FontIcon(FontAwesomeSolid.CALENDAR_ALT);
                    dateIcon.setIconColor(javafx.scene.paint.Color.web("#6c757d"));
                    
                    Label dateLabel = new Label(dateFormat.format(reclamation.getDate()));
                    dateLabel.getStyleClass().add("reclamation-date");
                    dateBox.getChildren().addAll(dateIcon, dateLabel);

                    header.getChildren().addAll(typeBox, new Region(), dateBox);
                    HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS);

                    // Description with icon
                    HBox descBox = new HBox(8);
                    descBox.setAlignment(Pos.TOP_LEFT);
                    
                    FontIcon descIcon = new FontIcon(FontAwesomeSolid.COMMENT_ALT);
                    descIcon.setIconColor(javafx.scene.paint.Color.web("#4a4a4a"));
                    
                    Label descriptionLabel = new Label(reclamation.getDescription());
                    descriptionLabel.getStyleClass().add("reclamation-description");
                    descriptionLabel.setWrapText(true);
                    descBox.getChildren().addAll(descIcon, descriptionLabel);

                    // Footer with status and actions
                    HBox footer = new HBox();
                    footer.getStyleClass().add("reclamation-footer");
                    footer.setAlignment(Pos.CENTER_LEFT);
                    footer.setSpacing(10);

                    // Status badge with icon
                    HBox statusBox = new HBox(6);
                    statusBox.setAlignment(Pos.CENTER_LEFT);
                    
                    FontIcon statusIcon = new FontIcon(FontAwesomeSolid.CLOCK);
                    statusIcon.setIconColor(javafx.scene.paint.Color.web("#856404"));
                    
                    Label statusLabel = new Label("En attente");
                    statusLabel.getStyleClass().addAll("status-badge", "status-pending");
                    statusBox.getChildren().addAll(statusIcon, statusLabel);

                    // Delete button with icon
                    Button deleteButton = new Button();
                    deleteButton.getStyleClass().add("delete-button");
                    
                    FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);
                    deleteIcon.setIconColor(javafx.scene.paint.Color.web("#dc3545"));
                    deleteButton.setGraphic(deleteIcon);
                    
                    deleteButton.setOnAction(event -> handleDeleteReclamation(reclamation));

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    footer.getChildren().addAll(statusBox, spacer, deleteButton);

                    // Add all elements to the container
                    container.getChildren().addAll(header, descBox, footer);
                    setGraphic(container);
                }
            }
        });

        // Load reclamations
        loadReclamations();

        // Setup search functionality
        setupSearch();
        
        // Setup buttons
        setupButtons();

        // Ajouter les validations en temps réel
        setupValidations();
    }

    private void setupButtons() {
        refreshButton.setOnAction(event -> loadReclamations());
        submitButton.setOnAction(event -> handleSubmitReclamation());
        clearButton.setOnAction(event -> clearForm());
        profileButton.setOnAction(event -> handleProfile());
    }

    private void handleProfile() {
        // Implement profile action here
        System.out.println("Profile clicked");
    }

    private void loadReclamations() {
        reclamationsList.clear();
        try {
            reclamationsList.addAll(reclamationService.getAll());
            reclamationTable.setItems(reclamationsList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des réclamations: " + e.getMessage());
        }
    }

    private void setupSearch() {
        filteredReclamations = new FilteredList<>(reclamationsList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredReclamations.setPredicate(reclamation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return reclamation.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                       reclamation.getType().toString().toLowerCase().contains(lowerCaseFilter);
            });
            reclamationTable.setItems(filteredReclamations);
        });
    }

    private void handleSubmitReclamation() {
        if (validateForm()) {
            try {
                // Get valid coach and adherent IDs from the database
                int coachId = getValidCoachId();
                int adherentId = getValidAdherentId();
                
                if (coachId == -1 || adherentId == -1) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", 
                        "Impossible de créer la réclamation: Veuillez vérifier que les coaches et adhérents existent dans la base de données.");
                    return;
                }
                
                // Create the reclamation object
                Reclamation newReclamation = new Reclamation(
                    0, // ID will be set by the database
                    descriptionField.getText().trim(),
                    typeComboBox.getValue(),
                    coachId,
                    adherentId,
                    java.sql.Date.valueOf(java.time.LocalDate.now())
                );
                
                System.out.println("Attempting to create reclamation: " + newReclamation);
                
                boolean created = reclamationService.create(newReclamation);
                if (created) {
                    clearForm();
                    loadReclamations(); // Reload the list after adding
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation soumise avec succès!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La réclamation n'a pas pu être ajoutée. Veuillez vérifier les données et réessayer.");
                }
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur de base de données", 
                    "Erreur lors de l'ajout de la réclamation: " + e.getMessage() + 
                    "\nCode: " + e.getErrorCode());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", 
                    "Erreur lors de l'ajout de la réclamation: " + e.getMessage());
            }
        }
    }

    private int getValidCoachId() {
        try {
            // Query to get a valid coach ID from the database
            String sql = "SELECT id FROM coach LIMIT 1";
            try (Statement stmt = MyDb.getInstance().getConn().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting valid coach ID: " + e.getMessage());
        }
        return -1;
    }

    private int getValidAdherentId() {
        try {
            // Query to get a valid adherent ID from the database
            String sql = "SELECT id FROM adherent LIMIT 1";
            try (Statement stmt = MyDb.getInstance().getConn().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting valid adherent ID: " + e.getMessage());
        }
        return -1;
    }

    private void handleDeleteReclamation(Reclamation reclamation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer la réclamation");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réclamation ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    reclamationService.delete(reclamation.getIdReclamation());
                    loadReclamations();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation supprimée avec succès!");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la réclamation: " + e.getMessage());
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;
        String description = descriptionField.getText();
        typeR type = typeComboBox.getValue();

        // Validation de la description
        if (description == null || description.isEmpty()) {
            descriptionField.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else if (description.length() < 10) {
            descriptionField.setStyle("-fx-border-color: #ffa500; -fx-border-width: 2px;");
            isValid = false;
        }

        // Validation du type
        if (type == null) {
            typeComboBox.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        }

        return isValid;
    }

    private void clearForm() {
        descriptionField.clear();
        typeComboBox.setValue(null);
        // Réinitialiser les styles
        descriptionField.setStyle("");
        typeComboBox.setStyle("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setupValidations() {
        // Validation de la description
        descriptionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                descriptionField.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            } else if (newValue.length() < 10) {
                descriptionField.setStyle("-fx-border-color: #ffa500; -fx-border-width: 2px;");
            } else {
                descriptionField.setStyle("-fx-border-color: #28a745; -fx-border-width: 2px;");
            }
        });

        // Validation du type
        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                typeComboBox.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            } else {
                typeComboBox.setStyle("-fx-border-color: #28a745; -fx-border-width: 2px;");
            }
        });
    }
    @FXML
    void GoTorecc(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gestion_Rec.fxml"));
            Parent root = loader.load();
            ((Button) actionEvent.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 