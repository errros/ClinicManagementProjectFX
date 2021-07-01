package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.Patient;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label assitfullname;

    @FXML
    private Label med2fullname;

    @FXML
    private Label med1fullname;

    @FXML
    private Label patientsnumber;

    @FXML
    private Label todayincome;
    public static String assist;
    public static String med1;
    public static String med2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        assitfullname.setText(assist);
        med1fullname.setText(med1);
        med2fullname.setText(med2);
        patientsnumber.setText(""+Patient.search("",1).size());

    }
}
