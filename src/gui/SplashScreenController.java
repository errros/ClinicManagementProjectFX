package gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashScreenController implements Initializable {
    @FXML
    private StackPane rootPane;
    @FXML
    private Label label;
    @FXML
    private Label label2;
    @FXML
    private ProgressBar progress = new ProgressBar(0);
    private MedicamentsController medocsLoader;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progress.setProgress(0);
        medocsLoader = new MedicamentsController();
        progress.progressProperty().unbind();
        progress.progressProperty().bind(medocsLoader.progressProperty());
        label.textProperty().unbind();
        label.textProperty().bind(medocsLoader.messageProperty());
        String doctor;
        if (AppController.user_id == 2) {
            doctor="Ayoub";
        }
        else doctor="Houcine";
        label2.setText(label2.getText()+doctor);
        medocsLoader.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
                new EventHandler<WorkerStateEvent>() {

                    @Override
                    public void handle(WorkerStateEvent t) {
                        try {
                            Parent appParent = FXMLLoader.load(getClass().getResource("App.fxml"));
                            Scene appScene = new Scene(appParent, 1280, 720);
                            appScene.getStylesheets().add(getClass().getResource("AppStyling.css").toExternalForm());
                            Stage window = new Stage();
                            window.setTitle("Cabinet++");
                            window.setScene(appScene);
                            window.setResizable(true);
                            window.show();
                            rootPane.getScene().getWindow().hide();
                        }
                        catch (IOException e) {
                            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE,null,e);
                        }
                    }
                });
        new Thread(medocsLoader).start();
    }
}
