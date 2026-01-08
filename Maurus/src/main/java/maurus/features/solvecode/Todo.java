package maurus.features.solvecode;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Todo extends Application {


    @Override
    public void start (Stage primaryStage) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        //input stream for file
        InputStream inputFile = getClass().getResourceAsStream("/solvecode/exercise.json");
        // Read JSON file and map to User class
        List<Exercise> exercises = mapper.readValue(inputFile, new TypeReference<List<Exercise>>() {});


        //choose random exercise
        Exercise exercise1 = exercises.get((int) (Math.random()*exercises.size()));


        System.out.println(exercise1.id);
        System.out.println(exercise1.solution);


        //design
        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        VBox vbox3 = new VBox();
        Label label = new Label("Complete the following exercise to continue");
        label.setStyle("-fx-font-size: 20px; -fx-padding: 10;-fx-font-weight: bold;");
        Label aufgabe  = new Label(exercise1.title + "\n");
        aufgabe.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        aufgabe.setAlignment(Pos.CENTER);
        vbox2.setMargin(aufgabe, new Insets(0,0,0,0)); // optional for spacing
        aufgabe.setMaxWidth(Double.MAX_VALUE);
        aufgabe.setAlignment(Pos.CENTER);

        vbox2.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-padding: 10;");
        Label aufgabentext =  new Label(exercise1.text + "\n");
        vbox2.getChildren().addAll(aufgabe,aufgabentext);

        vbox.setAlignment(Pos.TOP_CENTER);

        TextArea textarea = new TextArea();
        textarea.setMaxWidth(Double.MAX_VALUE);
        textarea.setWrapText(true);
        textarea.setMaxHeight(200);



        Button button = new Button("Confirm");
        button.setOnAction(e -> {
            //checks if answer is right
            //also replaces any spaces (leerzeichen) with "", (it just ignores spaces)
            if(textarea.getText().replaceAll("\\s+", "").equalsIgnoreCase(exercise1.solution.replaceAll("\\s+", ""))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completed");
                alert.setHeaderText(null);
                alert.setContentText("You have completed the exercise!");
                // Ensure the alert is on top
                alert.initOwner(primaryStage);
                alert.initModality(javafx.stage.Modality.WINDOW_MODAL);//blocks input to the parent window (primarystage)
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();//get stage of alert
                stage.setOnCloseRequest(event -> {
                    event.consume(); // prevents the alert from closing
                });
                alert.showAndWait(); // waits for user to close
                // Close current window, so app is called again
                primaryStage.close();
            }
            else {
                //alert if answer is wrong
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You have not completed the exercise!");
                alert.initOwner(primaryStage);
                alert.initModality(javafx.stage.Modality.WINDOW_MODAL);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();//get stage of alert
                stage.setOnCloseRequest(event -> {
                    event.consume(); // prevents the alert from closing
                });
                alert.showAndWait();
            }
        });
        vbox3.getChildren().addAll(textarea,button);
        vbox.getChildren().addAll(label,vbox2);
        root.setCenter(vbox3);
        root.setTop(vbox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            // Prevent the PrimaryStage from closing
            event.consume();
        });
        primaryStage.show();
    }
}
