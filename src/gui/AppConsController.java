package gui;

import dao.CnxWithDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Consultation;
import logic.Patient;
import logic.WaitingRoom;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class AppConsController implements Initializable {


    static private Connection cnx = CnxWithDB.getConnection();

    @FXML
    private Label personalInformationsLabel;

    @FXML
    private Label firstNameLabel;
@FXML
private Button refresh;

    @FXML
    private TextField sName;

    @FXML
    private Label secondNameLabel;

    @FXML
    private TextField sFameName;

    @FXML
    private Label sexLabel;

    @FXML
    private TextField sSex;

    @FXML
    private Label ageLabel;

    @FXML
    private TextField sAge;

    @FXML
    private Label consultationInformationsLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private TextField height;

    @FXML
    private Label weightLabel;

    @FXML
    private TextField weight;

    @FXML
    private Label blood_pressureLabel;

    @FXML
    private TextField blood_pressure;

    @FXML
    private Label blood_gluLabel;

    @FXML
    private TextField blood_glu;

    @FXML
    private Label temperatureLabel;

    @FXML
    private TextField tomperature;

    @FXML
    private TableView<Consultation> history;

    @FXML
    private TableColumn<Consultation, String> consultations;

    @FXML
    private Button save_New;

    @FXML
    private Button View_Medicaments;

    @FXML
    private Button new_consultation;

    @FXML
    private Button prescription;

    @FXML
    private Label emptyLabel;


    private ObservableList<Consultation> oblist = FXCollections.observableArrayList();

    private Patient patient;


    public void View_Medicaments(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Medicaments.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("cant load the window");
        }
    }

    @FXML
    void addinfos(ActionEvent event) {
        setEditableConsultationInfos(true);
        new_consultation.setVisible(false);
        View_Medicaments.setVisible(true);
        save_New.setVisible(true);
    }

    @FXML
    void saveNewValue(ActionEvent event) {
        LocalDate dte = LocalDate.now();
        Date dt = Date.valueOf(dte);

    }

    @FXML
    void refresh(ActionEvent event){

        WaitingRoom.patientPushedFromPatientsScene = null;
        init();
    }

    @FXML
    void showinfos(MouseEvent event) {
        Consultation consultation = history.getSelectionModel().getSelectedItem();
        if (consultation != null) {

            height.setText(String.valueOf(consultation.getHeight()));
            weight.setText(String.valueOf(consultation.getWeight()));
            blood_pressure.setText(String.valueOf(consultation.getBlood_pressure()));

            blood_glu.setText(String.valueOf(consultation.getBlood_glu()));
            tomperature.setText(String.valueOf(consultation.getTomperature()));
            setEditableConsultationInfos(false);
            prescription.setVisible(true);

        }


    }


    private void setEditableConsultationInfos(boolean b) {
        height.setEditable(b);
        weight.setEditable(b);
        blood_pressure.setEditable(b);
        blood_glu.setEditable(b);
        tomperature.setEditable(b);


    }



    //make all elements in the scene invisible/visible
    private void setVisibleConsultationElements(Boolean b) {
        height.setVisible(b);
        weight.setVisible(b);
        blood_pressure.setVisible(b);
        blood_glu.setVisible(b);
        tomperature.setVisible(b);
        sName.setVisible(b);
        sFameName.setVisible(b);
        sSex.setVisible(b);
        sAge.setVisible(b);
        history.setVisible(b);
        consultations.setVisible(b);
        save_New.setVisible(b);
        View_Medicaments.setVisible(b);
        new_consultation.setVisible(b);
        prescription.setVisible(b);
        heightLabel.setVisible(b);
        weightLabel.setVisible(b);
        blood_pressureLabel.setVisible(b);
        blood_gluLabel.setVisible(b);
        temperatureLabel.setVisible(b);
        firstNameLabel.setVisible(b);
        secondNameLabel.setVisible(b);
        ageLabel.setVisible(b);
        sexLabel.setVisible(b);
        personalInformationsLabel.setVisible(b);
        consultationInformationsLabel.setVisible(b);
        emptyLabel.setVisible(!b);
        emptyLabel.setLayoutX(480);
        emptyLabel.setLayoutY(320);
    }


     public void initWaitingRoomStaticFields(){


        if(AppController.user_id == 2){
            int a = WaitingRoom.getCurrentPatientIdFromDB();
            if(a!=0) {
                WaitingRoom.currentPatient1 = Patient.getPatientById(a);
            }

        } else if (AppController.user_id == 3){
            int a = WaitingRoom.getCurrentPatientIdFromDB();
            if(a!=0) {
                WaitingRoom.currentPatient2 = Patient.getPatientById(a);
            }

        }

    }


    // a way to be able to call initialize everytime a new patient is pushed
    public void init() {

       setEditableConsultationInfos(false);

        initWaitingRoomStaticFields();

        if(WaitingRoom.patientPushedFromPatientsScene != null){

            patient = WaitingRoom.patientPushedFromPatientsScene;
        }
        else if(AppController.user_id == 2){
        patient = WaitingRoom.currentPatient1;
        } else if(AppController.user_id == 3){
            patient = WaitingRoom.currentPatient2;
        }

        if (patient != null) {

            sName.setText(patient.getFirst_name());
            sFameName.setText(patient.getSecond_name());
            sSex.setText(patient.getSex());

            LocalDate date = patient.getDateOfbirth().toLocalDate();
            Period diff = Period.between(date, LocalDate.now());
            int y = diff.getYears();
            sAge.setText(Integer.toString(y));

            oblist.addAll(Consultation.history(patient.getId()));
            System.out.println(oblist);
            consultations.setCellValueFactory(new PropertyValueFactory<>("date"));

            history.setItems(oblist);
                 setVisibleConsultationElements(true);
                 new_consultation.setVisible(true);
        } else {
            setVisibleConsultationElements(false);
        }


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        {

            init();
        }
    }


}