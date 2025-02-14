//package Controllers;
//
//import Models.Evenement;
//import Services.EvenementService;
//import javafx.collections.FXCollections;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//
//import java.util.List;
//
//public class EventListController {
//
//    @FXML private TableView<Evenement> eventTable;
//    @FXML private TableColumn<Evenement, String> titleColumn;
//    @FXML private TableColumn<Evenement, String> descriptionColumn;
//    @FXML private TableColumn<Evenement, String> dateDebutColumn;
//    @FXML private TableColumn<Evenement, String> dateFinColumn;
//    @FXML private TableColumn<Evenement, String> lieuColumn;
//    @FXML private TableColumn<Evenement, Double> prixColumn;
//    @FXML private TableColumn<Evenement, String> etatColumn;
//    @FXML private TableColumn<Evenement, String> typeColumn;
//    @FXML private TableColumn<Evenement, String> organisateurColumn;
//    @FXML private TableColumn<Evenement, Integer> capaciteMaximaleColumn;
//
//    private EvenementService evenementService = new EvenementService();
//
//    @FXML
//    public void initialize() {
//        // Set up the table columns
//        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
//        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
//        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
//        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
//        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
//        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
//        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
//        organisateurColumn.setCellValueFactory(new PropertyValueFactory<>("organisateur"));
//        capaciteMaximaleColumn.setCellValueFactory(new PropertyValueFactory<>("capaciteMaximale"));
//
//        // Load the events into the table
//        loadEvents();
//    }
//
//    private void loadEvents() {
//        try {
//            List<Evenement> events = evenementService.getAll();
//            eventTable.setItems(FXCollections.observableArrayList(events));
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(AlertType.ERROR, "Erreur de récupération", "Une erreur est survenue lors de la récupération des événements.");
//        }
//    }
//
//    // Handle back button action
//    @FXML
//    private void handleBackButton() {
//        // Logic to go back to the previous screen
//        // You can implement this based on your application's navigation structure
//    }
//
//    // Utility method to show alert messages
//    private void showAlert(AlertType type, String title, String message) {
//        Alert alert = new Alert(type);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}












package Controllers;

import Models.Evenement;
import Services.EvenementService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.util.List;

public class EventListController {

    @FXML private TableView<Evenement> eventTable;
    @FXML private TableColumn<Evenement, byte[]> imageColumn; // Keep as byte[] for the model
    @FXML private TableColumn<Evenement, String> titleColumn;
    @FXML private TableColumn<Evenement, String> descriptionColumn;
    @FXML private TableColumn<Evenement, String> dateDebutColumn;
    @FXML private TableColumn<Evenement, String> dateFinColumn;
    @FXML private TableColumn<Evenement, String> lieuColumn;
    @FXML private TableColumn<Evenement, Double> prixColumn;
    @FXML private TableColumn<Evenement, String> etatColumn;
    @FXML private TableColumn<Evenement, String> typeColumn;
    @FXML private TableColumn<Evenement, String> organisateurColumn;
    @FXML private TableColumn<Evenement, Integer> capaciteMaximaleColumn;

    private EvenementService evenementService = new EvenementService();

    @FXML
    public void initialize() {
        // Set up the table columns
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image")); // Keep as byte[] for the model
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        organisateurColumn.setCellValueFactory(new PropertyValueFactory<>("organisateur"));
        capaciteMaximaleColumn.setCellValueFactory(new PropertyValueFactory<>("capaciteMaximale"));

        // Set custom cell factory for image column
        imageColumn.setCellFactory(new Callback<TableColumn<Evenement, byte[]>, TableCell<Evenement, byte[]>>() {
            @Override
            public TableCell<Evenement, byte[]> call(TableColumn<Evenement, byte[]> param) {
                return new TableCell<Evenement, byte[]>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(byte[] item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            // Convert byte[] to Image
                            Image image = new Image(new ByteArrayInputStream(item));
                            imageView.setImage(image);
                            imageView.setFitHeight(50); // Set height for the image
                            imageView.setFitWidth(50); // Set width for the image
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });

        // Load the events into the table
        loadEvents();
    }

    private void loadEvents() {
        try {
            List<Evenement> events = evenementService.getAll();
            eventTable.setItems(FXCollections.observableArrayList(events));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de récupération", "Une erreur est survenue lors de la récupération des événements.");
        }
    }

    // Handle back button action
    @FXML
    private void handleBackButton() {
        // Logic to go back to the previous screen
        // You can implement this based on your application's navigation structure
    }

    // Utility method to show alert messages
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}