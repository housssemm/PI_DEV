package org.example;//package org.example;//package org.example;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.StageStyle;
//
////public class MainFX extends Application {
//public class MainFX extends Application {
//private double xOffset = 0;
//    private double yOffset = 0;
//
//    @Override
//    public void start(Stage stage) {
//       // Parent root = FXMLLoader.load(getClass().getResource("/SigninUp.fxml"));
//        Pane root = new Pane(); // Conteneur principal
//        Scene scene = new Scene(root, 400, 300);
//
//        // Gestion du déplacement de la fenêtre
//        root.setOnMousePressed((MouseEvent event) -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        });
//
//        root.setOnMouseDragged((MouseEvent event) -> {
//            stage.setX(event.getScreenX() - xOffset);
//            stage.setY(event.getScreenY() - yOffset);
//            stage.setOpacity(0.8);
//        });
//
//        root.setOnMouseReleased((MouseEvent event) -> {
//            stage.setOpacity(1.0);
//        });
//        stage.initStyle(StageStyle.TRANSPARENT);
//
//        stage.setScene(scene);
//        stage.setTitle("Déplacement de fenêtre JavaFX");
//        stage.show();
//    }
//
//    public void start(Stage primaryStage) throws Exception {
//        // Load FXML file (adjust path to match your FXML location)
//
//        Parent root = FXMLLoader.load(getClass().getResource("/SigninUp.fxml"));
////        Parent root = FXMLLoader.load(getClass().getResource("/Events.fxml"));
//        // Set up the scene and stage
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Coachini");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

public class MainFX extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
//        // Charger l'interface FXML
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SigninUp.fxml")); // Assure-toi du bon chemin
//
//        // Gestion du déplacement de la fenêtre
//        root.setOnMousePressed((MouseEvent event) -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        });
//
//        root.setOnMouseDragged((MouseEvent event) -> {
//            stage.setX(event.getScreenX() - xOffset);
//            stage.setY(event.getScreenY() - yOffset);
//            stage.setOpacity(0.8);
//        });
//
//        root.setOnMouseReleased((MouseEvent event) -> {
//            stage.setOpacity(1.0);
//        });
//
//        // Appliquer un style transparent pour une meilleure intégration
//        stage.initStyle(StageStyle.UNDECORATED); // Ou TRANSPARENT si nécessaire
//
//        // Création de la scène avec le FXML chargé
//        Scene scene = new Scene(root);
//        stage.setTitle("Coachini");
//        stage.setScene(scene);
//        stage.show();
//    }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/invest.fxml"));
        Parent root = loader.load();

         //Configurer la scène
        Scene scene = new Scene(root, 1000, 600);

        // Configurer la fenêtre principale
        stage.setTitle("Tableau de bord");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
