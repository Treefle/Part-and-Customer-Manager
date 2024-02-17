package sample;

import Models.Appointment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.lang.reflect.Constructor;
import java.sql.Time;
import java.time.*;
import java.util.Locale;
import java.util.Optional;

public class Error {
    public static boolean b = false;
    /** If there isn't a value selected for deletion, this error shows*/
    public static void Selection(){
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Selection Error");
        alert.setContentText("Please make a selection then try again.");
        alert.show();
    }
    /** Confirms that you want to delete a Customer. */
    public static void Confirm(String string){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("You are you going to delete" + string);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            b = true;
        }
    }
    /** Confirms that you want to delete an Appointment*/
    public static void Confirm(Appointment appointment){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("You are going to delete Appointment: " + appointment.getID() + "\nType: " + appointment.getType());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            b = true;
        }
   }
    /** Confirms that the fields required are not empty. */
    public static void Empty(String fieldName){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Not a valid " + fieldName);
        alert.show();
    }
    /** Confirms that the new appointment doesn't overlap with any existing appointments. */
    public static void Overlap(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Overlapping appointment");
        alert.setContentText("This appointment time overlaps with an existing appointment.");
        alert.show();
    }
    /** Checks to see if there are any upcoming appointments, shows error is there are. */
    public static void Upcoming(int id, LocalDateTime localDateTime){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointment");
        alert.setHeaderText("There is an appointment scheduled in the next 15 minutes.");
        alert.setContentText("Appointment ID: " + id + "\nDate: "+ localDateTime.toLocalDate() +"\nTime: " + localDateTime.toLocalTime());
        alert.show();
    }
    /** Checks to see if there are any upcoming appointments shows error if there are not. */
    public static void NotUpcoming(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Status");
        alert.setHeaderText("There are no appointments scheduled in the next 15 minutes.");
        alert.setContentText("Please expect a notification 15 minutes before the next scheduled meeting.");
        alert.show();
    }
    /** Checks to see if a scheduled appointment is withing business hours */
    public static void Hours(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Scheduling Error");
        alert.setHeaderText("You cannot schedule an appointment outside of business hours");
        alert.setContentText("Business hours are 8 AM to 10 PM EST");
        alert.show();
    }
    /** Checks to see if a scheduled appointment is during the weekend. */
    public static  void Weekend(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Scheduling Error");
        alert.setHeaderText("You cannot schedule appointments during the weekend.");
        alert.show();
    }
    /** Shows if you attempt with delete a customer with an existing appointment*/
    public static void Dependency(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle ("Dependency Error");
        alert.setHeaderText("You cannot delete a Customer with an existing Appointment");
        alert.setContentText("  Please Try Again. Thank you.");
        alert.show();
    }

}
