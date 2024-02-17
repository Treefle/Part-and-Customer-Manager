package Models;

import sample.Menu;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer {
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Division_ID;
    public Customer(int Customer_ID,
                    String Customer_Name,
                    String Address,
                    String Postal_Code,
                    String Phone,
                    String Created_By,
                    Timestamp Last_Update,
                    String Last_Updated_By,
                    int Division_ID)
    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;
    }

    public Customer() {

    }

    public Customer(Customer selectedItem) {
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /** Sends the SQL Update command to the server with an using getters for the Customer object. */
    public String getUpdateString(String created_By){
        return "UPDATE customers SET " +
                "Customer_Name = '" + getCustomer_Name() + "'," +
                "Address = '" + getAddress() + "', " +
                "Postal_Code = '" + getPostal_Code() + "', " +
                "Phone = '" + getPhone() + "', " +
                "Last_Update = " + "NOW()" + ", " +
                "Last_Updated_By = '" + created_By + "', " +
                "Division_ID = " + getDivision_ID() + " " +
                "WHERE Customer_ID = " + getCustomer_ID();
    }

    /** Sends the SQL Insert command to the server with the new data passed an as argument. */
    public String getInsertString(String created_By){
        return "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) values(" +
                "'" + getCustomer_Name() + "', " +
                "'" + getAddress() + "', " +
                "'" + getPostal_Code() + "', " +
                "'" + getPhone()+ "', " +
                "NOW()" + ", '" + created_By + "', " +
                "NOW()" + ", '" + created_By + "', " +
                getDivision_ID() + ")";

    }

    /** Sends the SQL Delete command to the server with the selected Customer ID. */
    public String getDeleteString() {return "DELETE FROM customers WHERE Customer_ID=" + getCustomer_ID(); }

    /** Checks List of all Appointment Customer ID's, checks selected Customer ID as an argument. */
   public static boolean hasAppointments(int id) {
           ArrayList<Integer> appointmentCustIDArray = new ArrayList<>();
           for (int x =0;x<Menu.getAllAppointments().size();x++){
                   appointmentCustIDArray.add(x,Menu.getAllAppointments().get(x).getCustomer_ID());
            }
           System.out.println(appointmentCustIDArray.toString());

           if (appointmentCustIDArray.contains(id)) {
               return true;
           }
           System.out.println(appointmentCustIDArray.toString());
       return false;
   }
}
