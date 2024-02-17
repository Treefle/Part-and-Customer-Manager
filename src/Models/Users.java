package Models;

import javafx.scene.text.Text;

import java.sql.Date;



public class Users {
    private Integer User_ID;
    private String User_Name;

    public Users() {

    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    private Text Password;

    public Users(Integer User_ID,
                 String User_Name,
                 Text Password,
                 Date Create_Date)
    {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
    }
}
