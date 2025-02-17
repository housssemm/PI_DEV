package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierClient.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.setTitle("Gestion Categorie");
        stage.show();

    }

}
