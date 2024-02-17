package Models;

import java.sql.Timestamp;

public class Countries {
    int Country_ID;
    String Country;
    String Created_By;
    Timestamp Last_Update;
    String Last_Updated_By;

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
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

    public Countries(int Country_ID,
                     String Country,
                     String Created_By,
                     Timestamp Last_Update,
                     String Last_Updated_By
    )
    {
        this.Country_ID = Country_ID;
        this.Country = Country;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    public Countries() {

    }
}
