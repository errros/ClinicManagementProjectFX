package logic;


import dao.CnxWithDB;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// this class in only used to return the results of appointment search
public class AppointmentSearchResult {
    private int rdv_id;
    private String firstName;
    private String secondName;
    private String doctor;
    private String adr;
    private String num;
    static private Connection cnx = CnxWithDB.getConnection();

    public int getRdv_id() {
        return rdv_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getAdr() {
        return adr;
    }

    public String getNum() {
        return num;
    }

    public AppointmentSearchResult(int rdv_id , String firstName, String secondName, String doctor, String adr, String num) {
      this.rdv_id = rdv_id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.doctor = doctor;
        this.adr = adr;
        this.num = num;
    }

    public int getPatientId(){

        String query = "SELECT patient_id FROM waitingroom WHERE rdv_id = "+ this.rdv_id;

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);

            if(rs.next()){
                return rs.getInt("patient_id");
            }

        } catch (SQLException E){
            E.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return
                "{firstName='" + firstName + '\'' +
                        ", secondName='" + secondName + '\'' +
                        ", doctor=" + doctor +
                        ", adr='" + adr + '\'' +
                        ", num='" + num + "}";


    }
}
