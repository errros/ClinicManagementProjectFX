package logic;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import dao.CnxWithDB;
import javafx.scene.control.CheckBox;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Medicament {
    static public Connection cnx = CnxWithDB.getConnection();
    private String name;
    private String form;
    private String dosage;
    private CheckBox checkBox_matin;
    private CheckBox checkBox_midi;
    private CheckBox checkBox_soir;
    public int id = getId(this.name, this.form, this.dosage);


    public CheckBox getCheckBox_matin() {
        return checkBox_matin;
    }

    public CheckBox getCheckBox_midi() {
        return checkBox_midi;
    }

    public CheckBox getCheckBox_soir() {
        return checkBox_soir;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setCheckBox_matin(CheckBox checkBox_matin) {
        this.checkBox_matin = checkBox_matin;
    }

    public void setCheckBox_midi(CheckBox checkBox_midi) {
        this.checkBox_midi = checkBox_midi;
    }

    public void setCheckBox_soir(CheckBox checkBox_soir) {
        this.checkBox_soir = checkBox_soir;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public String getDosage() {
        return dosage;
    }

    public Medicament(String name, String form, String dosage) {
        this.name = name;
        this.form = form;
        this.dosage = dosage;
        this.checkBox_matin = new CheckBox();
        this.checkBox_midi = new CheckBox();
        this.checkBox_soir = new CheckBox();
    }

    static int getId(String a, String b, String c) {

        String query = "SELECT id FROM medicaments WHERE name = ? AND form = ? AND dosage = ?";
        int medId = 0;
        try {
            PreparedStatement pr = cnx.prepareStatement(query);
            pr.setString(1, a);
            pr.setString(2, b);
            pr.setString(3, c);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                medId = rs.getInt("id");


            }


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return medId;
    }

    public void Add() {
        try {
            int c1, c2, c3;
            c1 = ((checkBox_matin.isSelected()) ? 1 : 0);
            c2 = ((checkBox_midi.isSelected()) ? 1 : 0);
            c3 = ((checkBox_soir.isSelected()) ? 1 : 0);
            System.out.println(this.id);
            Statement statement = cnx.createStatement();
            String sql = "INSERT INTO prescription " +
                    "(consult_id,med_id,matin,midi,soir)" +
                    "VALUES ('" + Consultation.id + "','" + this.id + "','" + c1 + "','" + c2 + "','" + c3 + "')";
            statement.executeUpdate(sql);
            System.out.println("added to database");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
