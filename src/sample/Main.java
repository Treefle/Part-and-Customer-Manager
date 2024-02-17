package sample;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /** Loads Login window */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
        primaryStage.setTitle("Appointment Database");
        primaryStage.setScene(new Scene(root, 320, 193));
        primaryStage.show();
    }

    /** Open and Closes connection to SQL server while Scene in active.*/
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
        }
}
