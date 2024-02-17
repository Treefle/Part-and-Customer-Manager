package sample;
import Models.*;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Menu implements Initializable {

    @FXML public TableView<Models.Customer> customerTable;
    @FXML public TitledPane customerPane;
    @FXML public TitledPane addUpdatePane;
    @FXML public TextField customerNameField;
    @FXML public TextField customerAddressField;
    @FXML public TextField customerPostalField;
    @FXML public TextField customerPhoneField;
    @FXML public Label customerIDLabel;
    @FXML public ComboBox<FirstLevelDivision> firstLevelDivisionsBox;
    @FXML public ComboBox<Countries> countryBox;
    @FXML public Button addCustomerBtn;
    @FXML public Button customerSaveBtn;
    @FXML public Button updateCustomerBtn;
    @FXML public Button customerCancelBtn;
    @FXML public TableView<Models.Appointment> appointmentTable;
    @FXML public TitledPane appointmentPane;
    @FXML public TitledPane modAppointments;
    @FXML public Label idLabel;
    @FXML public TextField titleField;
    @FXML public TextField descriptionField;
    @FXML public TextField locationField;
    @FXML public TextField typeField;
    @FXML public ComboBox<Contacts> contactComboBox;
    @FXML public ComboBox<Integer> customerComboBox;
    @FXML public ComboBox<Integer> userComboBox;
    @FXML public DatePicker startDatePicker;
    @FXML public DatePicker endDatePicker;
    @FXML public ComboBox<LocalTime> startHourPicker;
    @FXML public ComboBox<LocalTime> startMinutePicker;
    @FXML public ComboBox<LocalTime> endHourPicker;
    @FXML public ComboBox<LocalTime> endMinutePicker;
    @FXML public TableView<Models.Appointment> scheduleTable;
    @FXML public Tab scheduleTab;
    @FXML public Tab appointmentTab;
    @FXML public Tab customerTab;
    @FXML public Label totalCustomers;
    @FXML public ComboBox monthFilterCombo1;
    @FXML public ComboBox<Contacts> contactFilterCombo;
    @FXML public TitledPane reportPane;
    @FXML public TextField searchField;
    @FXML public TableView<Models.Appointment> reportAppointmentsTable;

    public boolean populated = false;
    public boolean add = false;

    LocalTime localTime;
    LocalDateTime localDateTime;
    LocalDateTime localDateTime2;
    ZonedDateTime startZonedDateTime;
    ZonedDateTime endZonedDateTime;
    ObservableList<LocalTime> hourList = FXCollections.observableArrayList();
    ObservableList<LocalTime> minuteList = FXCollections.observableArrayList();
    ObservableList<Integer> idList = FXCollections.observableArrayList();
    ObservableList<Integer> weekList = FXCollections.observableArrayList();
    ObservableList<Integer> monthList = FXCollections.observableArrayList();
    ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
    ObservableList<Models.Users> users = FXCollections.observableArrayList();
    ObservableList<Integer> UserIDs = FXCollections.observableArrayList();

    private FilteredList<FirstLevelDivision> filteredDivisionlist;
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    ObservableList<Models.Contacts> allContacts = FXCollections.observableArrayList();
    static ObservableList<Models.Customer> allCustomers = FXCollections.observableArrayList();
    static ObservableList<Models.Appointment> allAppointments = FXCollections.observableArrayList();
    public FilteredList<Models.Appointment> filteredAppointments;
    public FilteredList<Models.Appointment> filteredAppointmentsCustomer;
    ObservableList<Countries> allCountries = FXCollections.observableArrayList();


    @Override
    /** Initializes Customer and Appointment tables, and their associated fields and boxes. */
    public void initialize(URL url, ResourceBundle rb) {


        customerTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Phone"));
        appointmentTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));

        appointmentTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentTable.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        appointmentTable.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("User_ID"));

        scheduleTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ID"));
        scheduleTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Title"));
        scheduleTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Type"));
        scheduleTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Description"));
        scheduleTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Start"));
        scheduleTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("End"));
        scheduleTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        reportAppointmentsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Title"));
        reportAppointmentsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Description"));
        reportAppointmentsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Start"));
        reportAppointmentsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("End"));


        weekList.addAll(1, 2, 3, 4, 5);
        monthList.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        if (!populated) {
            try {
                populateComboBox();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        try {
            updateCustomerTable();
            updateAppointmentTable();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Retrieves ALL Customers from the SQL Database. */
    public void updateCustomerTable() throws SQLException {
        allCustomers = FXCollections.observableArrayList();
        customerTable.setItems(null);
        Statement stmt = JDBC.connection.createStatement();
        String sqlstate = "SELECT * FROM customers";
        ResultSet rs = stmt.executeQuery(sqlstate);
        while (rs.next()) {
            Models.Customer customer = new Models.Customer();
            customer.setCustomer_ID(rs.getInt("Customer_ID"));
            customer.setCustomer_Name(rs.getString("Customer_Name"));
            customer.setAddress(rs.getString("Address"));
            customer.setPostal_Code(rs.getString("Postal_Code"));
            customer.setPhone(rs.getString(5));
            customer.setDivision_ID(rs.getInt("Division_ID"));
            allCustomers.addAll(customer);
        }
        customerTable.setItems(allCustomers);
        setCustomerIDList();
        updateUsers();
    }

    /** Retrieves ALL Appointments from the SQL Database. */
    public void updateAppointmentTable() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        appointmentTable.setItems(null);
        Statement stmt = JDBC.connection.createStatement();
        String sqlstate = "SELECT * FROM appointments";
        ResultSet rs = stmt.executeQuery(sqlstate);
        while (rs.next()) {
            Models.Appointment appointment = new Models.Appointment();
            appointment.setID(rs.getInt("Appointment_ID"));
            appointment.setTitle(rs.getString("Title"));
            appointment.setDescription(rs.getString("Description"));
            appointment.setLocation(rs.getString("Location"));
            appointment.setType(rs.getString("Type"));

            System.out.println(rs.getTimestamp("Start").toString());

            ZonedDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC"));
            appointment.setStart(startUTC.withZoneSameInstant(ZoneId.systemDefault()));

            ZonedDateTime endUTC = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC"));
            appointment.setEnd(endUTC.withZoneSameInstant(ZoneId.systemDefault()));


            System.out.println("Zoned Start Time: " + startUTC.toString());
            System.out.println("Zoned End Time: " + endUTC.toString());
            System.out.println("Adjusted Start: " + appointment.getStart().toString());
            System.out.println("Adjusted End Time: " + appointment.getEnd().toString());


            appointment.setCreated_By(rs.getString("Created_By"));
            appointment.setLast_Updated_By(rs.getString("Last_Updated_By"));
            appointment.setCustomer_ID(rs.getInt("Customer_ID"));
            appointment.setUser_ID(rs.getInt("User_ID"));
            appointment.setContact_ID(rs.getInt("Contact_ID"));
            allAppointments.addAll(appointment);
        }
        filteredAppointments = new FilteredList<Appointment>(allAppointments, b -> true);
        filteredAppointmentsCustomer = new FilteredList<Appointment>(allAppointments,b -> true);
        if (appointmentTable.isVisible()) {
            appointmentTable.setItems(filteredAppointments);
        }
        if (scheduleTable.isVisible()) {
            scheduleTable.setItems(filteredAppointmentsCustomer);
        }
        if (reportAppointmentsTable.isVisible()) {
            reportAppointmentsTable.setItems(filteredAppointments);
        }

    }

    /** Retrieves ALL Contacts from the SQL Database. Converts ID to String to display Contact name. */
    public void updateContacts() throws SQLException {
        allContacts = FXCollections.observableArrayList();
        Statement stmt = JDBC.connection.createStatement();
        String sqlstate = "SELECT * FROM contacts";
        ResultSet rs = stmt.executeQuery(sqlstate);
        while (rs.next()) {
            Models.Contacts contacts = new Models.Contacts();
            contacts.setContact_ID(rs.getInt("Contact_ID"));
            contacts.setContact_Name(rs.getString("Contact_Name"));
            contacts.setEmail(rs.getString("Email"));
            allContacts.addAll(contacts);
        }
        contactComboBox.setItems(allContacts);
        contactComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Contacts contacts) {
                if (contacts != null) {
                    return contacts.getContact_Name();
                } else return null;
            }

            @Override
            public Contacts fromString(String s) {
                return null;
            }
        });

        contactFilterCombo.setItems(allContacts);
        contactFilterCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Contacts contacts) {
                if (contacts != null) {
                    return contacts.getContact_Name();
                } else return null;
            }

            @Override
            public Contacts fromString(String s) {
                return null;
            }
        });
    }

    public void updateUsers() throws SQLException {
        users = FXCollections.observableArrayList();
        Statement stmt = JDBC.connection.createStatement();
        String sqlstate = "SELECT User_ID FROM users";
        ResultSet rs = stmt.executeQuery(sqlstate);
        while(rs.next()){
            Models.Users users = new Models.Users();
            users.setUser_ID(rs.getInt("User_ID"));
            this.users.addAll(users);
        }
        for(int x = 0; x< users.size();x++){
            UserIDs.add(x,users.get(x).getUser_ID());
        }
        userComboBox.setItems(UserIDs);
    }

    /** This filters ALL Customers. */
    public void updateCustomer() {

    }

    public void setCustomerIDList(){
        for(int x = 0; x < allCustomers.size();x++){
            customerIDList.add(x,allCustomers.get(x).getCustomer_ID());
        }
        customerComboBox.setItems(customerIDList);
    }

    /** On successful Login, Shows main Menu. */
    public static FXMLLoader showMenu() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Menu.class.getResource("Views/Menu.fxml"));
        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            return null;
        }
        stage.show();
        return loader;
    }

    /** When Customer is clicked, Shows Customer Table, populated. */
    public void customersBtnHandler() {
        Open(customerPane);
        Close(appointmentPane);
        Close(reportPane);
    }

    /** WHen Report is clicked, Shows Report Table Tab */
    public void reportBtnHandler() {
        updateCustomer();
        Open(reportPane);
        Close(customerPane);
        Close(appointmentPane);
    }

    /** When Appointment is clicked, Shows Appointment Table, populated. */
    public void appointmentsBtnHandler() throws Exception {
        Open(appointmentPane);
        Close(customerPane);
        Close(reportPane);
    }

    /** Add is clicked on the Customer Pane, opens clear add window. */
    public void addCustomerBtnHandler() throws Exception {
        int id = 1;
        for (int x = 0; x < allCustomers.size(); x++) {
            if (id == allCustomers.get(x).getCustomer_ID()) {
                id++;
            }
            customerIDLabel.setText(String.valueOf(id));
        }
        if (customerIDLabel.getText().isEmpty()) {
            customerIDLabel.setText(String.valueOf(Integer.valueOf((id))));
        }
        add = true;
        Open(addUpdatePane);
        populateBoxes();
    }

    /** Add is clicked on the Appointment Pane, opens clear add window. */
    public void addAppointmentBtnHandler() throws SQLException {
        int id = 1;
        for (int x = 0; x < allAppointments.size(); x++) {
            if (id == allAppointments.get(x).getID()) {
                id++;
            }
        }
        idLabel.setText(String.valueOf(id));

        add = true;
        Open(modAppointments);
        wipe();



    }

    /** Update is clicked on the Customer Pane, Opens update window, saves to existing customer object. */
    public void updateCustomerBtnHandler() throws Exception {

        if (!customerTable.getSelectionModel().isEmpty()) {
            add = false;
            Open(addUpdatePane);
            populateBoxes();
            Models.Customer customer = customerTable.getSelectionModel().getSelectedItem();

            customerIDLabel.setText(String.valueOf(customer.getCustomer_ID()));
            customerNameField.setText(customer.getCustomer_Name());
            customerAddressField.setText(customer.getAddress());
            customerPostalField.setText(customer.getPostal_Code());
            customerPhoneField.setText(customer.getPhone());
            FirstLevelDivision temp = null;
            Countries temp2 = null;
            for (FirstLevelDivision division : Customer.Divisions) {
                division.getDivision_ID();
                System.out.println(division.getDivision_ID());
                if (division.getDivision_ID() == customer.getDivision_ID()) {
                    temp = division;
                }
            }
            for (Countries countries : Customer.Countries) {
                countries.getCountry_ID();
                System.out.println(customer.getDivision_ID());
                if (countries.getCountry_ID() == temp.getCountry_ID()) {
                    temp2 = countries;
                }
            }
            firstLevelDivisionsBox.getSelectionModel().select(temp);
            countryBox.getSelectionModel().select(temp2);
        } else {
            Error.Selection();
            return;
        }

    }

    /** Update is clicked on the Appointment Pane, Opens update window, saves to existing appointment object. */
    public void updateAppointmentBtnHandler() throws SQLException {

        if (!appointmentTable.getSelectionModel().isEmpty()) {
            if (!populated) {
                populateComboBox();
            }
            add = false;
            Open(modAppointments);
            Models.Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

            idLabel.setText(String.valueOf(appointment.getID()));
            titleField.setText(appointment.getTitle());
            descriptionField.setText(appointment.getDescription());
            locationField.setText(appointment.getLocation());
            typeField.setText(appointment.getType());

            for (Contacts contacts : allContacts) {
                contacts.getContact_ID();
                if (contacts.getContact_ID() == appointmentTable.getSelectionModel().getSelectedItem().getContact_ID()) {
                    contactComboBox.getSelectionModel().select(contacts);
                }
            }
            customerComboBox.getSelectionModel().select(Integer.valueOf(appointment.getCustomer_ID()));
            userComboBox.getSelectionModel().select(Integer.valueOf(appointment.getUser_ID()));
            startDatePicker.setValue(appointment.getStart().toLocalDate());
            startHourPicker.getSelectionModel().select(appointment.getStart().toLocalTime().getHour());
            startMinutePicker.getSelectionModel().select(appointment.getStart().toLocalTime().getMinute());
            endDatePicker.setValue(appointment.getEnd().toLocalDate());
            endHourPicker.getSelectionModel().select(appointment.getEnd().toLocalTime().getHour());
            endMinutePicker.getSelectionModel().select(appointment.getEnd().toLocalTime().getMinute());

        } else {
            Error.Selection();
            return;
        }

    }

    /** Delete is clicked on the Customer Pane, checks to confirm then deletes and updates. */
    public void deleteCustomerBtnHandler() throws SQLException {
        if (!customerTable.getSelectionModel().isEmpty()) {
            Models.Customer customer = customerTable.getSelectionModel().getSelectedItem();
            Error.Confirm(" a Customer");
            if (Error.b) {
                if(!Models.Customer.hasAppointments(customerTable.getSelectionModel().getSelectedItem().getCustomer_ID())){
                    String delete = customer.getDeleteString();
                    Statement stmt = JDBC.connection.createStatement();
                    stmt.execute(delete);
                    updateCustomerTable();
                    Error.b = false;
               }
               else{
                   Error.Dependency();
                }
            }
        } else {
            Error.Selection();
        }
    }

    /** Delete is clicked on the Appointment Pane, checks to confirm then deletes and updates. */
    public void deleteAppointmentBtnHandler() throws SQLException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            Models.Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            Error.Confirm(appointment);
            if (Error.b) {
                String delete = appointment.getDeleteString();
                Statement stmt = JDBC.connection.createStatement();
                stmt.execute(delete);
                updateAppointmentTable();
                Error.b = false;
            }
        } else {
            Error.Selection();
        }
    }

    /** Save is clicked on the Customer Pane, saves input vales into new Customer object. */
    public void saveCustomerBtnHandler() throws SQLException {

        if (valid()) {
            Models.Customer customer = new Models.Customer();
            if (customerIDLabel.getText().length() < 1) {
                customer.setCustomer_ID(0);
            } else {
                customer.setCustomer_ID(Integer.valueOf(customerIDLabel.getText()));
            }
            customer.setCustomer_Name(customerNameField.getText());
            customer.setAddress(customerAddressField.getText());
            customer.setPostal_Code(customerPostalField.getText());
            customer.setPhone(customerPhoneField.getText());
            customer.setDivision_ID(firstLevelDivisionsBox.getSelectionModel().getSelectedItem().getDivision_ID());
            String update = customer.getUpdateString(customer.getCreated_By());
            String insert = customer.getInsertString(customer.getCreated_By());
            Statement stmt = JDBC.connection.createStatement();
            if (add) {
                stmt.execute(insert);
            } else {
                stmt.execute(update);
            }

            Close(addUpdatePane);
        }
        updateCustomerTable();
    }

    /** Save is clicked on the Appointment Pane, saves input vales into new Appointment object. */
    public void saveAppointmentBtnHandler() throws SQLException {

        localDateTime = LocalDateTime.of(startDatePicker.getValue(), startHourPicker.getSelectionModel().getSelectedItem().plusMinutes(startMinutePicker.getSelectionModel().getSelectedItem().getMinute()));
        startZonedDateTime = ZonedDateTime.of(localDateTime,ZoneId.systemDefault());
        LocalDateTime instant = LocalDateTime.ofInstant(startZonedDateTime.toInstant(),ZoneOffset.UTC);

        localDateTime2 = LocalDateTime.of(endDatePicker.getValue(), endHourPicker.getSelectionModel().getSelectedItem().plusMinutes(endMinutePicker.getSelectionModel().getSelectedItem().getMinute()));
        endZonedDateTime = ZonedDateTime.of(localDateTime2,ZoneId.systemDefault());
        LocalDateTime instant2 = LocalDateTime.ofInstant(endZonedDateTime.toInstant(),ZoneOffset.UTC);

        System.out.println("Local Start Time: " + localDateTime.toString());
        System.out.println("Local End Time: " + localDateTime2.toString());

        System.out.println("Zoned Start Time: " + startZonedDateTime.toString());
        System.out.println("Zoned End Time: " + endZonedDateTime.toString());

        System.out.println("Adjusted Start Time: " + instant.toString());
        System.out.println("Adjusted End TIme: " + instant2.toString());

        Models.Appointment appointment = new Models.Appointment();
        appointment.setID(Integer.valueOf(idLabel.getText()));
        appointment.setTitle(titleField.getText());
        appointment.setDescription(descriptionField.getText());
        appointment.setLocation(locationField.getText());
        appointment.setType(typeField.getText());
        appointment.setStart(instant.atZone(ZoneId.of("UTC")));
        appointment.setEnd(instant2.atZone(ZoneId.of("UTC")));
        appointment.setContact_ID(contactComboBox.getSelectionModel().getSelectedItem().getContact_ID());
        appointment.setCustomer_ID(customerComboBox.getSelectionModel().getSelectedItem());
        appointment.setUser_ID(userComboBox.getSelectionModel().getSelectedItem());

        System.out.println(appointment.getStart().toString());

        String update = appointment.getUpdateString(appointment.getCreated_By());
        String insert = appointment.getInsertString(appointment.getCreated_By());
        Statement stmt = JDBC.connection.createStatement();

        if (authentication()) {
            if (isBusinessHours(appointment)) {
                if (add) {
                    stmt.execute(insert);
                } else {
                    stmt.execute(update);
                    System.out.println(appointment.getUpdateString("user"));
                }
                Close(modAppointments);
                updateAppointmentTable();
            }

        }
    }

    /** public void testAdd() throws SQLException {
        Models.Customer customer = new Models.Customer();
        customer.setCustomer_ID(99);
        customer.setCustomer_Name("Name");
        customer.setAddress("Address");
        customer.setPostal_Code("Postal Code");
        customer.setPhone("Phone");
        customer.setDivision_ID(0);
        JDBC.connection.createStatement().execute(customer.getInsertString(customer.getCreated_By()));
        updateCustomerTable();
    }*/

    /** Cancel is clicked, Closes pane, wipes fields. */
    public void cancelCustomerBtnHandler() {
        customerIDLabel.setText("");
        customerNameField.setText("");
        customerAddressField.setText("");
        customerPostalField.setText("");
        customerPhoneField.setText("");

        Close(addUpdatePane);
    }

    /** Cancel is clicked, Closes pane, wipes fields. */
    public void cancelAppointmentBtnHandler() {
        Close(modAppointments);
    }

    /** Fills combo boxes with valid SQL data. */
    public void populateBoxes() throws Exception {

        {
            if (firstLevelDivisionsBox.getItems().isEmpty()) {
                Customer.populateDivisions();
            }

            filteredDivisionlist = new FilteredList(Customer.Divisions, p -> true);
            firstLevelDivisionsBox.setItems(filteredDivisionlist);
            firstLevelDivisionsBox.setConverter(new StringConverter<FirstLevelDivision>() {
                @Override
                public String toString(FirstLevelDivision firstLevelDivision) {
                    return firstLevelDivision.getDivision();
                }

                @Override
                public FirstLevelDivision fromString(String s) {
                    return null;
                }
            });
        }

        if (countryBox.getItems().isEmpty()) {
            Customer.populateCountry();
            allCountries.setAll(Customer.Countries);
            countryBox.setItems(allCountries);
            countryBox.setConverter(new StringConverter<Countries>() {
                @Override
                public String toString(Countries countries) {
                    return countries.getCountry();
                }

                @Override
                public Countries fromString(String s) {
                    return null;
                }
            });
        }
    }

    /** Opens pane. */
    public void Open(TitledPane pane) {
        pane.setDisable(false);
        pane.setVisible(true);
        pane.setExpanded(true);
    }

    /** Closes pane. */
    public void Close(TitledPane pane) {
        pane.setDisable(true);
        pane.setVisible(false);
        pane.setExpanded(false);
        add = false;
    }

    /** LAMBDA #1! Filters divisions to only compatible according to Combo Box. */
    public void filterDivisions() {
        filteredDivisionlist.setPredicate(allDivisions -> {
            return allDivisions.getCountry_ID() == (countryBox.getSelectionModel().getSelectedItem().getCountry_ID());
        });
    }

    /** Checks is inputs meet basic requirements. */
    public boolean valid() {
        if (customerNameField.getText().length() < 1) {
            Error.Empty("Customer Name");
            return false;
        }
        else if (customerAddressField.getText().length() < 1) {
            Error.Empty("Address");
            return false;
        }
        else if (customerPostalField.getText().length() < 1) {
            Error.Empty("Postal Code");
            return false;
        }
        else if (customerPhoneField.getText().length() < 1) {
            Error.Empty("Phone Number");
            return false;
        }
        else if (countryBox.getSelectionModel().isEmpty()) {
            Error.Empty("Country");
            return false;
        }
        else if (firstLevelDivisionsBox.getSelectionModel().isEmpty()) {
            Error.Empty("First Level Division");
            return false;
        }
        return true;
    }

    /** Populates selection boxes for times. */
    public void populateComboBox() throws SQLException {
        updateContacts();

        for (int x = 0; x <= 23; x++) {
            hourList.add(LocalTime.of(x, 0));
        }

        minuteList.addAll(LocalTime.of(0, 0),
                LocalTime.of(0, 15),
                LocalTime.of(0, 30),
                LocalTime.of(0, 45));

        startHourPicker.setItems(hourList);
        endHourPicker.setItems(hourList);
        startMinutePicker.setItems(minuteList);
        endMinutePicker.setItems(minuteList);
        monthFilterCombo1.setItems(monthList);
        populated = true;
    }

    /** Clears appointment window. */
    public void wipe() {
        titleField.setText("");
        descriptionField.setText("");
        locationField.setText("");
        typeField.setText("");
        contactComboBox.getSelectionModel().clearSelection();
        customerComboBox.getSelectionModel().clearSelection();
        userComboBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        startHourPicker.getSelectionModel().clearSelection();
        startMinutePicker.getSelectionModel().clearSelection();
        endHourPicker.getSelectionModel().clearSelection();
        endMinutePicker.getSelectionModel().clearSelection();
    }

    public boolean authentication() {

        for (int x = 0; x < allAppointments.size(); x++) {

                LocalDateTime allStarts = (allAppointments.get(x).getStart().toLocalDateTime());
                LocalDateTime allEnds = (allAppointments.get(x).getEnd().toLocalDateTime());
                LocalDateTime checkStart = (startDatePicker.getValue().atTime(startHourPicker.getValue().plusMinutes(startMinutePicker.getValue().getMinute())));
                LocalDateTime checkEnd = (endDatePicker.getValue().atTime(endHourPicker.getValue().plusMinutes(endMinutePicker.getValue().getMinute())));

                if (isOverlapping(allStarts, allEnds, checkStart, checkEnd)) {
                    Error.Overlap();
                    return false;
                }
                if(checkStart.isAfter(checkEnd)){
                    Error.Overlap();
                    return false;
                }
        }
        return true;
    }

    /** Checks if there are any upcoming appointments. */
    public static boolean isUpcoming() {
        LocalDateTime now15 = LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(15));
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        for (int x = 0; x < allAppointments.size(); x++) {
            LocalDateTime start = (allAppointments.get(x).getStart().toLocalDateTime());

            if(start.isAfter(now)){
                if (start.isBefore(now15)) {
                    Error.Upcoming(allAppointments.get(x).getID(), start);
                    return true;
                }
            }
        }
        Error.NotUpcoming();
        return false;
    }

    /** Checks if appointments are overlapping. */
    public static boolean isOverlapping(LocalDateTime allStarts, LocalDateTime allEnds, LocalDateTime checkStart, LocalDateTime checkEnd) {

        return !checkStart.isAfter(allEnds) && !allStarts.isAfter(checkEnd);
    }

    /** Checks if the new appointment is within business hours. */
    public boolean isBusinessHours(Appointment appointment) {
        ZonedDateTime startUTC = appointment.getStart();
        startUTC = startUTC.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = appointment.getEnd();
        endUTC = endUTC.withZoneSameInstant(ZoneId.of("UTC"));
        if (endUTC.toLocalTime().getHour() < 8 || endUTC.toLocalTime().getHour() > 20) {
            Error.Hours();
            return false;
        }
        if (startUTC.toLocalTime().getHour() < 8 || startUTC.toLocalTime().getHour() > 20) {
            Error.Hours();

            return false;
        }
        if (startUTC.getDayOfWeek().getValue() == 6 || startUTC.getDayOfWeek().getValue() == 7) {
            Error.Weekend();

            return false;
        }
        if (endUTC.getDayOfWeek().getValue() == 6 || endUTC.getDayOfWeek().getValue() == 7) {
            Error.Weekend();
            return false;
        }
        return true;
    }

    private boolean searchFindsAppointment(Appointment appointment, String searchText){
        return(appointment.getTitle().toLowerCase(Locale.ROOT).contains(searchText) ||
                appointment.getType().toLowerCase(Locale.ROOT).contains(searchText) ||
                appointment.getDescription().contains(searchText));
    }

    @FXML private void filterAppointments() {
        filteredAppointments.setPredicate(appointmentPredicate(searchField.getText()));
        System.out.println("searching");
    }
    private Predicate<Appointment> appointmentPredicate(String searchText){
            return appointment ->{
                if(searchText == null || searchText.isEmpty()){
                    System.out.println("not found");
                    return true;
                }
                    System.out.println("found");
                    return searchFindsAppointment(appointment,searchText);
            };
        }

    /** LAMBDA #2! Filters appointments according to Week and Month Combo Box.
    public void filterTable() {
             boolean week = true;
            boolean month = true;
            if (!weekCombo.getSelectionModel().isEmpty()) {
                if (appointment.getStart().toLocalDate().getDayOfMonth() / 7 + 1 != weekCombo.getSelectionModel().getSelectedItem()) {
                    week = false;
                }
            }
            if (!monthCombo.getSelectionModel().isEmpty()) {
                if (appointment.getStart().toLocalDate().getMonthValue() != monthCombo.getSelectionModel().getSelectedItem()) {
                    month = false;
                }
            }
            return week && month;
        } */
    /** LAMBDA #3! Filters appointments according to Customer ID. */
    public void filterSchedule() {

            updateCustomer();
        filteredAppointmentsCustomer.setPredicate(appointment -> {
                boolean customer = true;
                if (!contactFilterCombo.getSelectionModel().isEmpty()) {
                    if (reportPane.isVisible()) {
                        if (appointment.getContact_ID() != contactFilterCombo.getValue().getContact_ID()) {
                            customer = false;
                        }
                    }
                }
                return customer;
            });
    }

    /** Creates reports for appointments.
    public void filterAppointmentReport() throws SQLException {
        int total = 0;


            for (int x = 0; x < allAppointments.size(); x++)
            {
                if (!monthFilterCombo.getSelectionModel().isEmpty() || !typeFilterField.getText().isEmpty())
                {

                if (getAllAppointments().get(x).getStart().getMonth().getValue() == Integer.parseInt(monthFilterCombo.getSelectionModel().getSelectedItem().toString()) || (allAppointments.get(x).getType().contains(typeFilterField.getText())))
                {
                    total = total +1;

                }
            }
        }
            totalAppointments.setText(String.valueOf(total));
    }*/
    /** Creates report for final report. */
    public void filterCustomerReport() throws IOException {
        int total = 0;
        FileReader fr = new FileReader("login_activity.txt");
        BufferedReader br = new BufferedReader(fr);
        String string = null;
        for(int x = 0; br.ready(); x++){
            string = String.valueOf(br.readLine().charAt(11)) + br.readLine().charAt(12);

            if(!monthFilterCombo1.getSelectionModel().isEmpty()){
                    if(string.contains(monthFilterCombo1.getSelectionModel().getSelectedItem().toString())){
                        total=total+1;
                }
            }
        }
        totalCustomers.setText(String.valueOf(total));
    }

}


