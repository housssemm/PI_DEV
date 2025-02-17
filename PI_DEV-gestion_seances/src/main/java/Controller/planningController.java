package Controller;

import Models.Seance;
import Services.SeanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.URL;
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
          //Calcule le décalage entre le premier jour du mois et le lundi de la semaine. Cela permet de déterminer où commencer à afficher les dates sur le calendrier.
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue() - 1;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
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
                    double textTranslationY = - (rectangleHeight / 2) * 0.75;
                    date.setTranslateY(textTranslationY);
                    stackPane.getChildren().add(date);

                    // Event handler for date click
                    stackPane.setOnMouseClicked(event -> {
                        showSessionsForDate(calculatedDate);  // Display sessions for the clicked date
                    });

                    List<Seance> calendrierSeances = getSeancesForMonth(dateFocus, 1).get(calculatedDate);
                    if (calendrierSeances != null) {
                        createCalendarSeances(calendrierSeances, rectangleHeight, rectangleWidth, stackPane);
                    }
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

    private void showSessionsForDate(int dayOfMonth) {
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

    private List<Seance> getSessionsForSelectedDay(int dayOfMonth, int idPlanning) {
        Map<Integer, List<Seance>> seanceMap = getSeancesForMonth(dateFocus, idPlanning);
        return seanceMap.getOrDefault(dayOfMonth, new ArrayList<>());
    }

    private VBox createSeanceCard(Seance seance) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
        card.setMaxWidth(400);
        card.setMinHeight(400);
        gridSeance.setVgap(10);

        Text title = new Text("Titre: " + seance.getTitre());
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Text id = new Text("ID: " + seance.getId());
        id.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text description = new Text("Description: " + seance.getDescription());
        description.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text date = new Text("Date: " + seance.getDate().toLocalDate());
        date.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text coachId = new Text("Coach ID: " + seance.getIdCoach());
        coachId.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text adherentId = new Text("Adherent ID: " + seance.getIdAdherent());
        adherentId.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text type = new Text("Type: " + seance.getType());
        type.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text videoLink = new Text("Lien Video: " + seance.getLienVideo());
        videoLink.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text planningId = new Text("Planning ID: " + seance.getPlanningId());
        planningId.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text startTime = new Text("Heure Début: " + seance.getHeureDebut().toString());
        startTime.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        Text endTime = new Text("Heure Fin: " + seance.getHeureFin().toString());
        endTime.setStyle("-fx-font-size: 12; -fx-fill: #555;");

        card.getChildren().addAll(title, id, description, date, coachId, adherentId, type, videoLink, planningId, startTime, endTime);

        return card;
    }

    private Map<Integer, List<Seance>> createSeanceMap(List<Seance> seances) {
        Map<Integer, List<Seance>> SeanceMap = new HashMap<>();
        for (Seance se : seances) {
            int SeanceDate = se.getDate().toLocalDate().getDayOfMonth();
            SeanceMap.computeIfAbsent(SeanceDate, k -> new ArrayList<>()).add(se);
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
}
