package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FxmlLoader {
    private Pane view;
    public Pane getPage(String fileName) {
        try {
            URL fileUrl = AppController.class.getResource("/gui/" + fileName + ".fxml");
            if (fileUrl == null ) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        }
        catch(Exception e) {
            System.out.println("FXML file not found");
        }
        return view;
    }
}
