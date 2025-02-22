package Controllers;

import Models.Seance;
import Services.CreateurEvenementService;
import Services.SeanceService;
import Utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class planningController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridSeance;
    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private FlowPane calendar;

    private int idCoach;
    private int idPlanning;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth() {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
    @FXML
    void forwardOneMonth() {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
    public void initData(int idCoach, int idPlanning) {
        this.idCoach = idCoach;
        this.idPlanning = idPlanning;
        System.out.println("ID Coach: " + idCoach);
        System.out.println("ID Planning: " + idPlanning);
    }


    private void drawCalendar() {
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.FRENCH);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);

        year.setText(dateFocus.format(yearFormatter));
        month.setText(dateFocus.format(monthFormatter));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = dateFocus.getMonth().maxLength();
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        // Calcule le décalage entre le premier jour du mois et le lundi de la semaine.
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue() - 1;

        // Vide la grille avant de redessiner
        calendar.getChildren().clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);  // Initialement transparent
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (i * 7) + j + 1 - dateOffset;
                if (calculatedDate > 0 && calculatedDate <= monthMaxDate) {
                    Text date = new Text(String.valueOf(calculatedDate));
                    double textTranslationY = -(rectangleHeight / 2) * 0.75;
                    date.setTranslateY(textTranslationY);
                    stackPane.getChildren().add(date);


                    List<Seance> calendrierSeances = getSeancesForMonth(dateFocus, 1).get(calculatedDate);
                    if (calendrierSeances != null && !calendrierSeances.isEmpty()) {
                        rectangle.setFill(Color.web("#F58400"));
                        createCalendarSeances(calendrierSeances, rectangleHeight, rectangleWidth, stackPane);
                    }

                    // Event handler pour le clic sur la date
                    stackPane.setOnMouseClicked(event -> {
                        showSessionsForDate(calculatedDate);  // Affiche les séances pour la date sélectionnée
                    });
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }


    private void createCalendarSeances(List<Seance> seances, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        for (Seance seance : seances) {
            Text sessionText = new Text(seance.getTitre());
            sessionText.setStyle("-fx-font-size: 12; -fx-fill: #000;");
            sessionText.setTranslateY(rectangleHeight / 4);
            stackPane.getChildren().add(sessionText);
        }
    }

    public void showSessionsForDate(int dayOfMonth) {
        // Vérifie que la date appartient bien au mois et à l'année actuels du calendrier
        if (dateFocus.getMonthValue() == ZonedDateTime.now().getMonthValue() &&
                dateFocus.getYear() == ZonedDateTime.now().getYear()) {

            List<Seance> sessionsForDay = getSessionsForSelectedDay(dayOfMonth, idPlanning);

            gridSeance.getChildren().clear();
            gridSeance.getRowConstraints().clear();

            gridSeance.setVgap(10);
            gridSeance.setPadding(Insets.EMPTY);

            double cardHeight = 300;

            for (int i = 0; i < sessionsForDay.size(); i++) {
                Seance session = sessionsForDay.get(i);
                VBox sessionCard = createSeanceCard(session);

                sessionCard.setAlignment(Pos.CENTER);
                sessionCard.setPadding(new Insets(10));

                gridSeance.add(sessionCard, 0, i);

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.TOP);
                gridSeance.getRowConstraints().add(rowConstraints);
            }

            double gridHeight = cardHeight * sessionsForDay.size() + gridSeance.getVgap() * (sessionsForDay.size() - 1);
            gridSeance.setPrefHeight(gridHeight);
        }
    }

    private List<Seance> getSessionsForSelectedDay(int dayOfMonth, int idPlanning) {
        Map<Integer, List<Seance>> seanceMap = getSeancesForMonth(dateFocus, idPlanning);
        return seanceMap.getOrDefault(dayOfMonth, new ArrayList<>());
    }

    public VBox createSeanceCard(Seance seance) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: #ddd; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 5, 0, 0, 3);");

        // Effet au survol (hover)
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #f8f9fa; " +
                "-fx-border-color: #bbb; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 4);"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: #ddd; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 5, 0, 0, 3);"));

        card.setMaxWidth(400);
        card.setMinHeight(400);
        gridSeance.setVgap(10);

        // Contenu de la carte
        Label title = new Label("📌 " + seance.getTitre());
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #F58400;");

        Label description = new Label("📝 " + seance.getDescription());
        description.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label date = new Label("📅 " + seance.getDate());
        date.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label coachId = new Label("👤 Coach ID: " + seance.getIdCoach());
        coachId.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label adherentId = new Label("👥 Adhérent ID: " + seance.getIdAdherent());
        adherentId.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label type = new Label("📖 Type: " + seance.getType());
        type.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label videoLink = new Label("🎥 Vidéo: " + seance.getLienVideo());
        videoLink.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label startTime = new Label("🕒 Début: " + seance.getHeureDebut().toString());
        startTime.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");

        Label endTime = new Label("🕕 Fin: " + seance.getHeureFin().toString());
        endTime.setStyle("-fx-font-size: 16; -fx-text-fill: #000000;");


        Button btnModifier = new Button("✏ Modifier");
        btnModifier.setStyle("-fx-background-color: #F58400; -fx-text-fill: white; -fx-font-weight: bold;");
        btnModifier.setOnAction(event -> modifierSeance(seance));


        Button btnSupprimer = new Button("🗑 Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #F58400; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSupprimer.setOnAction(event -> supprimerSeance(seance));

        HBox buttonBox = new HBox(10, btnModifier, btnSupprimer);
        buttonBox.setAlignment(Pos.CENTER);

        card.getChildren().addAll(title, description, date, coachId, adherentId, type, videoLink, startTime, endTime, buttonBox);

        return card;
    }

    private void modifierSeance(Seance seance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/popupModifierSeance.fxml"));
            DialogPane dialogPane = loader.load();

            popupModifierSeanceController controller = loader.getController();
            controller.initData(seance);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private void supprimerSeance(Seance seance) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la séance ?");
        alert.setContentText("Voulez-vous vraiment supprimer cette séance ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SeanceService service = new SeanceService();
            service.delete(seance.getId());

            // Remove only the card for the deleted session
            for (Node node : gridSeance.getChildren()) {
                if (node instanceof VBox) {
                    VBox card = (VBox) node;
                    Label title = (Label) card.getChildren().get(0); // assuming title is the first label

                    // If the title matches the session being deleted, remove the card
                    if (title.getText().contains(seance.getTitre())) {
                        gridSeance.getChildren().remove(card);
                        break; // Exit after removing the matching card
                    }}}}}

    private Map<Integer, List<Seance>> createSeanceMap(List<Seance> seances) {
        Map<Integer, List<Seance>> SeanceMap = new HashMap<>();
        for (Seance se : seances) {
            LocalDate localDate = se.getDate().toLocalDate();
            int seanceDate = localDate.getDayOfMonth();
            SeanceMap.computeIfAbsent(seanceDate, k -> new ArrayList<>()).add(se);
        }
        return SeanceMap;
    }

    private Map<Integer, List<Seance>> getSeancesForMonth(ZonedDateTime dateFocus, int idPlanning) {
        SeanceService sc = new SeanceService();
        List<Seance> seances = sc.getSeancesByPlanningId(idPlanning);
        return createSeanceMap(seances);
    }

    public void ajouterSeance(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/popupSeance.fxml"));
        DialogPane dialogPane = loader.load();

        popupSeanceController controller = loader.getController();
        controller.initData(idCoach, idPlanning);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }
    //ROOT

    @FXML
    void GoToEvent(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Events.fxml"));
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
