package Models;

import java.sql.Timestamp;

public class FirstLevelDivision {
    private int Country_ID;
    private int Division_ID;
    private String Division;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;

    public FirstLevelDivision(int Division_ID,
                              String Division,
                              String Created_By,
                              Timestamp Last_Update,
                              String Last_Updated_By,
                              int Country_ID
    ){
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Country_ID = Country_ID;
    }

    public FirstLevelDivision() {

    }
    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public Timestamp getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }




}
