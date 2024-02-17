package sample;

import Models.Countries;
import Models.FirstLevelDivision;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Customer
{
    public static ObservableList<FirstLevelDivision> Divisions = FXCollections.observableArrayList();
    public static ObservableList<Countries> Countries = FXCollections.observableArrayList();
    /** Selects ALL from First Level Divisions SQL Table aka States. */
    public static void populateDivisions() throws SQLException, Exception
    {

        String stmt = "Select * FROM first_level_divisions";
        PreparedStatement pstmt = JDBC.connection.prepareStatement(stmt);
        ResultSet rs = pstmt.executeQuery(stmt);

        while(rs.next())
        {
            FirstLevelDivision fld = new FirstLevelDivision();
            fld.setDivision(rs.getString("Division"));
            fld.setCountry_ID(rs.getInt("Country_ID"));
            fld.setDivision_ID(rs.getInt("Division_ID"));
            fld.setCreated_By(rs.getString("Created_By"));
            fld.setLast_Updated_By(rs.getString("Last_Updated_By"));
            fld.setLast_Update(rs.getTimestamp("Last_Update"));
            Divisions.add(fld);
        }




    }
    /** Selects ALL from Country SQL Table. */
    public static void populateCountry() throws SQLException, Exception
    {
        String stmt = "Select * FROM countries";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(stmt);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next())
        {
            Countries countries = new Countries();
            countries.setCountry(rs.getString("Country"));
            countries.setCountry_ID(rs.getInt("Country_ID"));
            countries.setCreated_By(rs.getString("Created_By"));
            countries.setLast_Updated_By(rs.getString("Last_Updated_By"));
            countries.setLast_Update(rs.getTimestamp("Last_Update"));
            Countries.add(countries);
        }
    }

}
