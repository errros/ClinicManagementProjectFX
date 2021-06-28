package logic;

import dao.CnxWithDB;
import gui.MedicamentsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultation {
    static private Connection cnx = CnxWithDB.getConnection();

    public int consultation_id;
    public int patient_id;
    private int height;
    private int weight;
    private int blood_pressure;
    private double blood_glu;

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBlood_pressure(int blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public void setBlood_glu(double blood_glu) {
        this.blood_glu = blood_glu;
    }

    public void setTomperature(double tomperature) {
        this.tomperature = tomperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private double tomperature;
    private String date;

    public Consultation(int patient_id, int height, int weight, int blood_pressure, double blood_glu, double tomperature, Date date) {
        this.patient_id = patient_id;
        this.height = height;
        this.weight = weight;
        this.blood_pressure = blood_pressure;
        this.blood_glu = blood_glu;
        this.tomperature = tomperature;
        this.date = date.toString();
        this.consultation_id = getConsultationId();





    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBlood_pressure() {
        return blood_pressure;
    }

    public double getBlood_glu() {
        return blood_glu;
    }

    public double getTomperature() {
        return tomperature;
    }

    public String getDate() {
        return date;
    }

    public void add() {

        try {
            Connection cnx = CnxWithDB.getConnection();
            Statement statement = cnx.createStatement();
            String sql = "INSERT INTO consultation " +
                    "(patient_id,height,weight,blood_pre,blood_glu,temp,date)" +
                    "VALUES ('" + this.patient_id + "','" + this.height + "','"
                    + this.weight +
                    "','" + this.blood_pressure +
                    "','" + this.blood_glu +
                    "','" + this.tomperature + "','" + Date.valueOf(LocalDate.now()) + "')";
            statement.executeUpdate(sql);
            System.out.println("added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static public ObservableList<Consultation> history(int patient_id) {
        ObservableList<Consultation> ob = FXCollections.observableArrayList();

        try {

            Connection cnx = CnxWithDB.getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("select * from consultation where patient_id =" + patient_id);
            while (rs.next()) {
                ob.add(new Consultation(rs.getInt("patient_id"),
                        rs.getInt("height"),
                        rs.getInt("weight"),
                        rs.getInt("blood_pre"),
                        rs.getDouble("blood_glu"),
                        rs.getDouble("temp"),
                        rs.getDate("date")));
            }

        } catch (SQLException e) {
            Logger.getLogger(MedicamentsController.class.getName()).log(Level.SEVERE, null, e);
        }


        return ob;
    }


    private int getConsultationId() {
        String query = "SELECT id FROM consultation WHERE patient_id = ? AND date = ? ";

        try ( PreparedStatement st = cnx.prepareStatement(query)) {

            st.setInt(1, this.patient_id);
            st.setDate(2,Date.valueOf(this.date));

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                return rs.getInt("id");

            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }


}
