package sample;


import helper.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML private TitledPane loginLabel;
    @FXML private Label userLabel;
    @FXML private TextField userField;
    @FXML private Label passLabel;
    @FXML private TextField passField;
    @FXML private Label zoneID;
    @FXML private Button logBtn;
    private ZoneId localZoneId = ZoneId.systemDefault();




    @Override
    /** Set language values to match system language on Initialize. */
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("sample.Properties.login", Locale.getDefault());
        zoneID.setText(localZoneId.toString());
        loginLabel.setText(rb.getString("title"));
        userLabel.setText(rb.getString("username"));
        userField.setPromptText(rb.getString("username"));
        passLabel.setText(rb.getString("password"));
        passField.setPromptText(rb.getString("password"));
        logBtn.setText(rb.getString("btn"));
    }

    /** Finds zone ID and sets it to the proper value. */
    public void setZone(){

    }
    /** Checks Username and Password, only works for the case test, test */
    public boolean validateLogin(int userID, String pass) throws SQLException {
        Statement stmt = JDBC.connection.createStatement();
        String sqlstate = "SELECT Password FROM users WHERE User_ID = '" + userID + "'";
        ResultSet rs = stmt.executeQuery(sqlstate);
        while(rs.next()){
            if (rs.getString("Password").equals(pass)){
                setZone();
                return true;
            }
        }
        return  false;
    }

    private int getUserID(String username) throws SQLException {
        int userID = -1;
        Statement stmt = JDBC.connection.createStatement();
        String sqlstate = "SELECT User_ID FROM users WHERE User_Name = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sqlstate);
        while (rs.next()){
            userID = rs.getInt("User_ID");
        }
        return userID;
    }

    @FXML
    /** Throws an error is one of the text fields for Login are empty. */
    private void LoginHandler() throws Exception {
        String user = userField.getText();
        String pass = passField.getText();
        if(user.equals(""))
        {
            ResourceBundle rb = ResourceBundle.getBundle("sample.Properties.login", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("empty"));
            alert.setContentText(rb.getString("ta"));
            alert.show();
            logger(false);
        }
        else{
            int userID = getUserID(user);

            if(validateLogin(userID, pass))
                {
                    Menu.showMenu();
                    Menu.isUpcoming();
                    logger(true);
                }

            else{
                ResourceBundle rb = ResourceBundle.getBundle("sample.Properties.login", Locale.getDefault());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("error"));
                alert.setHeaderText(rb.getString("incorrect"));
                alert.setContentText(rb.getString("ta"));
                alert.show();
                logger(false);
                }

            }
    }

    /** Amends the text file that stores the log of logins. */
    public void logger(boolean success){
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final String time = formatter.format(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        final String username = userField.getText();
        try{
            final FileWriter fw = new FileWriter("login_activity.txt", true);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Time: " + time + "\t");
            bw.write("Username: " + username + "\t");
            bw.write("Success: " + success + "\t");
            bw.newLine();
            bw.close();
        } catch (IOException exception){
            System.out.println("Failed to log invalid login");
            System.out.println(exception.getMessage());
        }
    }

}
