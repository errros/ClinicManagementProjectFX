package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.Patient;
import logic.WaitingRoom;

import java.sql.Date;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        //Patient p = new Patient("walid","abdallaoui", Date.valueOf("2001-07-17"));
        WaitingRoom.patientPushedFromPatientsScene = null;
        System.setProperty("prism.lcdtext", "false");
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        Image img = new Image("/gui/resources/Group2.png");
        /*scene.getStylesheets().add(getClass().getResource("LogingStyling.css").toExternalForm());*/
        stage.setScene(scene);
        stage.getIcons().add(img);
        stage.setTitle("Cabinet++");
        stage.setResizable(true);

        stage.show();

    }
}
