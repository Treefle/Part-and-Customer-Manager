package Models;

import com.mysql.cj.result.Field;
import helper.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Appointment {

    private int iD;
    private String title;
    private String description;
    private String location;
    private String type;
    private String created_by;
    private String last_updated_by;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;
    private ZonedDateTime start;
    private ZonedDateTime end;
    public static List<String> Appointments = new ArrayList<>();


    public Appointment  (int iD,
                         String title,
                         String description,
                         String location,
                         String type,
                         String created_by,
                         String last_updated_by,
                         int customer_ID,
                         int user_ID,
                         int contact_ID)
    {
        this.iD = iD;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.created_by = created_by;
        this.last_updated_by = last_updated_by;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        }
    /** Empty Constructor */
    public Appointment() {

    }

    public List<String> getAppointments(){
        return Appointments;
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_By() {
        return created_by;
    }

    public void setCreated_By(String created_by) {
        this.created_by = created_by;
    }

    public String getLast_Updated_By() {
        return last_updated_by;
    }

    public void setLast_Updated_By(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getContact_ID() {
        return contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
    /** returns SQL Update String */
    public String getUpdateString(String created_by){

        System.out.println("Zoned Date Time: " + getStart().toString());

        LocalDateTime instant = LocalDateTime.ofInstant(getStart().toInstant(), ZoneId.of("UTC"));
        LocalDateTime instant2 = LocalDateTime.ofInstant(getEnd().toInstant(), ZoneId.of("UTC"));

        System.out.println("UTC instant: " + instant.toString());

        Timestamp timestamp = Timestamp.valueOf(instant);
        Timestamp timestamp2 = Timestamp.valueOf(instant2);

        System.out.println("Local instant: " + timestamp.toString());


        return "UPDATE appointments SET " +
                "Appointment_ID = " + getID() + ", " +
                "Title = '" + getTitle() + "', " +
                "Description = '" + getDescription() + "', " +
                "Location = '" + getLocation() + "', " +
                "Type = '" + getType() + "', " +
                "Created_By = '" + getCreated_By() + "', " +
                "Last_Updated_By = '" + getLast_Updated_By() + "', " +
                "Customer_ID = "+ getCustomer_ID() + ", " +
                "User_ID = " + getUser_ID() + ", " +
                "Contact_ID = " + getContact_ID() + ", " +
                "Start = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp) + "', " +
                "End = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp2) + "' " +
                "WHERE Appointment_ID = " + getID();

    }
    /** returns SQL Insert String */
    public String getInsertString(String created_by){
        LocalDateTime instant = LocalDateTime.ofInstant(getStart().toInstant(), ZoneId.of("UTC"));
        LocalDateTime instant2 = LocalDateTime.ofInstant(getEnd().toInstant(),  ZoneId.of("UTC"));
       Timestamp timestamp =  Timestamp.valueOf(instant);
        Timestamp timestamp2 = Timestamp.valueOf(instant2);

        return "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Created_By," +
                " Last_Updated_By, Customer_ID, User_ID, Contact_ID, Start, End) values(" +
                "" +getID() + ", " +
                "'" +getTitle() + "', " +
                "'" +getDescription() + "', " +
                "'" +getLocation() + "', " +
                "'" +getType() + "', " +
                "'" +getCreated_By() + "', " +
                "'" +getLast_Updated_By() + "', " +
                "" +getCustomer_ID() + ", " +
                "" +getUser_ID() + ", " +
                "" +getContact_ID() + ", " +
                "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp) + "', " +
                "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp2) + "')";
    }
    /** returns SQL Delete String */
    public String getDeleteString(){
        return "DELETE FROM appointments WHERE Appointment_ID =" + getID();
    }
}
