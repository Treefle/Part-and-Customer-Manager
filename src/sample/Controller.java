package sample;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller {

    @FXML private TextField addPartOptionsPartIDField;
    @FXML private TextField addPartOptionsPartNameField;
    @FXML private TextField addPartOptionsInventoryLevelField;
    @FXML private TextField addPartOptionsPriceField;
    @FXML private TextField addPartOptionsMinField;
    @FXML private TextField addPartOptionsMaxField;
    @FXML private TextField addPartOptionsInHouseMachineIDField;

    @FXML private TextField modPartOptionsPartIDField;
    @FXML private TextField modPartOptionsPartNameField;
    @FXML private TextField modPartOptionsInventoryLevelField;
    @FXML private TextField modPartOptionsPriceField;
    @FXML private TextField modPartOptionsMinField;
    @FXML private TextField modPartOptionsMaxField;
    @FXML private TextField modPartOptionsInHouseMachineIDField;

    @FXML private TextField addProductOptionsProductIDField;
    @FXML private TextField addProductOptionsProductNameField;
    @FXML private TextField addProductOptionsInventoryLevelField;
    @FXML private TextField addProductOptionsPriceField;
    @FXML private TextField addProductOptionsMinField;
    @FXML private TextField addProductOptionsMaxField;

    @FXML private TextField modProdOptionsProdID;
    @FXML private TextField modProdOptionsProdName;
    @FXML private TextField modProdOptionsInvLvl;
    @FXML private TextField modProdOptionsPrice;
    @FXML private TextField modProdOptionsMin;
    @FXML private TextField modProdOptionsMax;

    @FXML private Label inHouseMachineIDLabel;
    @FXML private Label modPartMachineID;
    @FXML private TextField partsSearchBar;
    @FXML private TextField prodSearchBar;
    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private RadioButton modInHouseRadio;
    @FXML private RadioButton modOutsourcedRadio;
    @FXML private TitledPane addPartWindow;
    @FXML private TitledPane modPartWindow;
    @FXML private TitledPane addProductWindow;
    @FXML private TitledPane modProdWindow;

    @FXML private TableView<Part> partsTable;
    @FXML private TableView<Product> prodTable;
    @FXML private TableView<Part> addProdPartsTable;
    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableView<Part> modProdOptionsPartsTable;
    @FXML private TableView<Part> modProdOptionsAssociatedParts;
    @FXML private TextField addProdSearch;
    @FXML private TextField modProdOptionsSearch;
    @FXML private TextArea errorText;
    @FXML private TitledPane entryError;
    @FXML private Button entryErrorOK;
    @FXML private Button ConNo;
    @FXML private Button ConYes;
    @FXML private TitledPane ConWindow;

    int option = 0;
    private FilteredList<Part> searchPartList;
    private FilteredList<Product> searchProductList;
    private Product addProduct;
    private Product modProduct;

    /** Initialize Part and Product lists. Assigns table columns. */
    public void initialize() {
        searchPartList = new FilteredList<>(Inventory.getAllParts(), b -> true);
        searchProductList = new FilteredList<>(Inventory.getAllProducts(), b -> true);

        partsTable.setItems(searchPartList);
        prodTable.setItems(searchProductList);

        partsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        partsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        partsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("stock"));
        partsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));

        prodTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        prodTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        prodTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("stock"));
        prodTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));

        Inventory.addPart(new Outsourced(0, "Tire", 20.00, 5, 1, 100, "We Make Cool Stuff Inc."));
        Inventory.addPart(new InHouse(1, "Chain", 12.00, 5, 1, 100, 1));

        Inventory.addProduct(new Product(1, "bike", 199.99, 5, 1, 100));
    }

    /** Finds ID for new Part. Shows the Add Part Window.
    * RUNTIME ERROR: I had a lot of trouble setting up the auto-generated ID, choosing between the ID of the selected item, the index value, and the value to check against to find if available. The Error was a NullPointerException related to searching at invalid indices. */
    public void AddPartButtonClickHandler() {
        addPartOptionsPartIDField.setDisable(false);

        int idFinder = 0;
        for (int i = 0; Inventory.getAllParts().size() > i; i++) {

            if (Inventory.getAllParts().get(i).getId() == idFinder) {
                idFinder++;
            }
        }
        addPartOptionsPartIDField.setText(String.valueOf(idFinder));

        if (Inventory.getAllParts().isEmpty()) {
            addPartOptionsPartIDField.setText("0");
        }
        addPartOptionsPartIDField.setDisable(true);
        addPartWindow.setVisible(true);
        addPartWindow.setExpanded(true);
        addPartWindow.setDisable(false);
    }

    /** Verifies inputs. Adds new part to List. Resets text fields on successful part addition. */
    public void AddPartOptionsConfirmButtonClickHandler() {
         String ErrorText,
         name,
         company,
         nameField = addPartOptionsPartNameField.getText(),
         invField = addPartOptionsInventoryLevelField.getText(),
         priceField = addPartOptionsPriceField.getText(),
         minField = addPartOptionsMinField.getText(),
         maxField = addPartOptionsMaxField.getText(),
         machineIDField = addPartOptionsInHouseMachineIDField.getText();

        int id = Integer.parseInt(addPartOptionsPartIDField.getText()),
            inv,
            min,
            max,
            machineID;

        Double price;

        ErrorText = PartVerify(nameField,invField,priceField,minField,maxField,machineIDField);

        if (ErrorText != ""){
            ErrorHandler("Add Part Error", ErrorText);
        }

        if(ErrorText == ""){
            name = nameField;
            price = Double.parseDouble(priceField);
            inv = Integer.parseInt(invField);
            min = Integer.parseInt(minField);
            max = Integer.parseInt(maxField);

            if (outsourcedRadio.isSelected()){
                company = machineIDField;
                Inventory.addPart(new Outsourced(id,name,price,inv,min,max,company));
            }
            else if (inHouseRadio.isSelected()){
                machineID = Integer.parseInt(machineIDField);
                Inventory.addPart( new InHouse(id,name,price,inv,min,max,machineID));
            }
                addPartOptionsPartIDField.setText("");
                addPartOptionsPartNameField.setText("");
                addPartOptionsInventoryLevelField.setText("");
                addPartOptionsPriceField.setText("");
                addPartOptionsMinField.setText("");
                addPartOptionsMaxField.setText("");
                addPartOptionsInHouseMachineIDField.setText("");
                addPartWindow.setVisible(false);
                addPartWindow.setExpanded(false);
                addPartWindow.setDisable(true);
        }
    }

    /** Resets text fields. Hides Add Part Window. */
    public void AddPartOptionsCancelButtonClickHandler() {

        addPartWindow.setVisible(false);
        addPartWindow.setExpanded(false);
        addPartWindow.setDisable(true);
        addPartOptionsPartIDField.setText("");
        addPartOptionsPartNameField.setText("");
        addPartOptionsInventoryLevelField.setText("");
        addPartOptionsPriceField.setText("");
        addPartOptionsMinField.setText("");
        addPartOptionsMaxField.setText("");
        addPartOptionsInHouseMachineIDField.setText("");


    }

    /** Shows the Mod Part Window. Parses InHouse and Outsourced Parts. */
    public void ModPartButtonClickHandler() {
        InHouse modInHouse;
        Outsourced modOutsourced;

        if (Inventory.getAllParts().isEmpty()) {
            ErrorHandler("Modify Part Error", "Please add a part, then select it to modify");
        }
        else if (partsTable.getSelectionModel().getSelectedItem() == null ) {
            ErrorHandler("Modify Part Error", "Please select a part to modify");
        } else {
            modPartWindow.setVisible(true);
            modPartWindow.setExpanded(true);
            modPartWindow.setDisable(false);
        }

        if (partsTable.getSelectionModel().getSelectedItem() instanceof InHouse) {
            modInHouse = (InHouse) partsTable.getSelectionModel().getSelectedItem();

            modPartOptionsPartIDField.setText(String.valueOf(modInHouse.getId()));
            modPartOptionsPartIDField.setDisable(true);
            modPartOptionsPartNameField.setText(String.valueOf(modInHouse.getName()));
            modPartOptionsInventoryLevelField.setText(String.valueOf(modInHouse.getStock()));
            modPartOptionsPriceField.setText(String.valueOf(modInHouse.getPrice()));
            modPartOptionsMinField.setText(String.valueOf(modInHouse.getMin()));
            modPartOptionsMaxField.setText(String.valueOf(modInHouse.getMax()));
            modPartOptionsInHouseMachineIDField.setText(String.valueOf(modInHouse.getMachineId()));
            inHouseMachineIDLabel.setText("Machine ID :");

        } else if (partsTable.getSelectionModel().getSelectedItem() instanceof Outsourced) {
            modOutsourced = (Outsourced) partsTable.getSelectionModel().getSelectedItem();

            modPartOptionsPartIDField.setText(String.valueOf(modOutsourced.getId()));
            modPartOptionsPartIDField.setDisable(true);
            modPartOptionsPartNameField.setText(String.valueOf(modOutsourced.getName()));
            modPartOptionsInventoryLevelField.setText(String.valueOf(modOutsourced.getStock()));
            modPartOptionsPriceField.setText(String.valueOf(modOutsourced.getPrice()));
            modPartOptionsMinField.setText(String.valueOf(modOutsourced.getMin()));
            modPartOptionsMaxField.setText(String.valueOf(modOutsourced.getMax()));
            modPartOptionsInHouseMachineIDField.setText(String.valueOf(modOutsourced.getCompanyName()));
            inHouseMachineIDLabel.setText("Company Name :");
        }
    }

    /** Verifies inputs. Modifies selected part. Resets text fields on successful part modification. */
    public void ModPartOptionsConfirmButtonHandler() {
        String ErrorText,
                name,
                company,
                nameField = modPartOptionsPartNameField.getText(),
                invField = modPartOptionsInventoryLevelField.getText(),
                priceField = modPartOptionsPriceField.getText(),
                minField = modPartOptionsMinField.getText(),
                maxField = modPartOptionsMaxField.getText(),
                machineIDField = modPartOptionsInHouseMachineIDField.getText();

        int id = Integer.parseInt(modPartOptionsPartIDField.getText()),
                inv,
                min,
                max,
                machineID;

        double price;

        ErrorText = PartVerify(nameField,invField,priceField,minField,maxField,machineIDField);

        if (ErrorText != ""){
            ErrorHandler("Modify Part Error", ErrorText);
        }
        if (ErrorText == ""){
            name = nameField;
            price = Double.parseDouble(priceField);
            inv = Integer.parseInt(invField);
            min = Integer.parseInt(minField);
            max = Integer.parseInt(maxField);

            if (modOutsourcedRadio.isSelected()) {
                company = machineIDField;
                Inventory.updatePart(partsTable.getSelectionModel().getSelectedItem().getId(),
                        new Outsourced(id,name,price,inv,min,max,company));
            }
            else if (modInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(machineIDField);
                Inventory.updatePart(partsTable.getSelectionModel().getSelectedItem().getId(),
                        new InHouse(id,name,price,inv,min,max,machineID)); }

            modPartOptionsPartIDField.setText("");
            modPartOptionsPartNameField.setText("");
            modPartOptionsPriceField.setText("");
            modPartOptionsInventoryLevelField.setText("");
            modPartOptionsMinField.setText("");
            modPartOptionsMaxField.setText("");
            modPartOptionsInHouseMachineIDField.setText("");
            modPartWindow.setVisible(false);
            modPartWindow.setExpanded(false);
            modPartWindow.setDisable(true);

        }
    }

    /** Resets text fields. Hides Mod Part Window. */
    public void ModPartOptionsCancelButtonClickHandler() {
        modPartOptionsPartIDField.setText("");
        modPartOptionsPartNameField.setText("");
        modPartOptionsPriceField.setText("");
        modPartOptionsInventoryLevelField.setText("");
        modPartOptionsMinField.setText("");
        modPartOptionsMaxField.setText("");
        modPartOptionsInHouseMachineIDField.setText("");
        modPartWindow.setVisible(false);
        modPartWindow.setExpanded(false);
        modPartWindow.setDisable(true);

    }

    /** Shows Confirmation Window. Assigns delete function to Yes button. */
    public void PartsDeleteButtonClickHandler() {

        if (partsTable.getSelectionModel().getSelectedItem() == null || partsTable.getSelectionModel().isEmpty()) {
            ErrorHandler("Delete Selection Error", "Please select a Part to delete");
        }

        else {
                ConHandler();
                option = 1;
        }
    }

    /** Shows Add Product Window. Assigns table Columns in Product Window.
     *  FUTURE ENHANCEMENT: There's a fair amount of repetitive code, such as completely separate text fields and add and mod product windows. There could be better re-use of existing fields ans structure. */
    public void AddProductClickHandler() {
        addProductWindow.setVisible(true);
        addProductWindow.setExpanded(true);
        addProductWindow.setDisable(false);

        int idFinder = 0;
        for (int i = 0; Inventory.getAllProducts().size() > i; i++) {

            if (Inventory.getAllProducts().get(i).getId() == idFinder) {
                idFinder++;
            }
            addProductOptionsProductIDField.setText(String.valueOf(idFinder));
        }

        if (Inventory.getAllProducts().isEmpty()) {
            addProductOptionsProductIDField.setText("0");
        }

        addProdPartsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        addProdPartsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        addProdPartsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("stock"));
        addProdPartsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));

        associatedPartsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        associatedPartsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        associatedPartsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("stock"));
        associatedPartsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));

        addProdPartsTable.setItems(searchPartList);
        addProduct = new Product(0, "0", 0, 0, 0, 0);
        associatedPartsTable.setItems(addProduct.getAllAssociatedParts());


        addProductOptionsProductNameField.setText("zero");
        addProductOptionsPriceField.setText("0.0");
        addProductOptionsInventoryLevelField.setText("0");
        addProductOptionsMinField.setText("0");
        addProductOptionsMaxField.setText("0");

    }

    /** Add Associated Part Function for Add Part Window. */
    public void AddAssociatedPart() {
        Part selectedPart = addProdPartsTable.getSelectionModel().getSelectedItem();
        addProduct.addAssociatedPart(selectedPart);
    }

    /** Add Associated Part Function for Mod Part Window. */
    public void AddAssociatedPart2() {
        Part selectedPart = modProdOptionsPartsTable.getSelectionModel().getSelectedItem();
        modProduct.addAssociatedPart(selectedPart);
    }

    /** Remove Associated Part Function for Add Part Window. */
    public void RemoveAssociatedPart() {
        ConHandler();
        option = 2;
    }

    /** Remove Associated Part Function for Mod Part Window. */
    public void RemoveAssociatedPart2() {
        ConHandler();
        option = 3;
    }

    /** Assigns default values, and confirms new values are entered. */
    public void AddProductOptionsConfirmButtonClickHandler() {
        String ErrorText = "",
        nameField = addProductOptionsProductNameField.getText(),
        priceField = addProductOptionsPriceField.getText(),
        invField = addProductOptionsInventoryLevelField.getText(),
        minField = addProductOptionsMinField.getText(),
        maxField = addProductOptionsMaxField.getText();

        int id = Integer.parseInt(addProductOptionsProductIDField.getText());
        String name = "zero";
        Double price = 0.0;
        int inv = 0;
        int min = 0;
        int max = 0;
        Product newProd;

      ErrorText = PartVerify(nameField,priceField,invField,minField,maxField,"0");
        if (!ErrorText.equals("")){
            ErrorHandler("Add Product Error", ErrorText);
        }
        else if (ErrorText.equals("")) {
            name = nameField;
            price = Double.parseDouble(priceField);
            inv = Integer.parseInt(invField);
            min = Integer.parseInt(minField);
            max = Integer.parseInt(maxField);
            newProd = new Product(id,name,price,inv,min,max);

            for (int i = 0; addProduct.getAllAssociatedParts().size() > i; i++) {
                newProd.addAssociatedPart(addProduct.getAllAssociatedParts().get(i));
            }

            Inventory.addProduct(newProd);

            addProductOptionsProductIDField.setText("");
            addProductOptionsProductNameField.setText("zero");
            addProductOptionsPriceField.setText("0.0");
            addProductOptionsInventoryLevelField.setText("0");
            addProductOptionsMinField.setText("0");
            addProductOptionsMaxField.setText("0");
            searchPartList.setPredicate(part -> {return true;});
            addProdSearch.setText("");
            addProductWindow.setExpanded(false);
            addProductWindow.setVisible(false);
            addProductWindow.setDisable(true);
        }

    }

    /** Resets and hides Add Product Window. */
    public void AddProductOptionsCancelButtonClickHandler() {
        addProductOptionsProductIDField.setText("");
        addProductOptionsProductNameField.setText("zero");
        addProductOptionsPriceField.setText("0.0");
        addProductOptionsInventoryLevelField.setText("0");
        addProductOptionsMinField.setText("0");
        addProductOptionsMaxField.setText("0");
        searchPartList.setPredicate(part -> {return true;});
        addProdSearch.setText("");
        addProductWindow.setExpanded(false);
        addProductWindow.setVisible(false);
        addProductWindow.setDisable(true);

    }

    /** Shows Mod Product Window. */
    public void ModProductButtonClickHandler() {

        Product prod = prodTable.getSelectionModel().getSelectedItem();

        if (Inventory.getAllProducts().isEmpty()) {
            ErrorHandler("Modify Product Error", "Please add a product, then select it to modify");
        }

        else if ( prod == null ) {
            ErrorHandler("Modify Product Error", "Please select a product to modify");
        } else {
            modProdWindow.setVisible(true);
            modProdWindow.setExpanded(true);
            modProdWindow.setDisable(false);

        modProdOptionsProdID.setText(String.valueOf(prod.getId()));
        modProdOptionsProdName.setText(String.valueOf(prod.getName()));
        modProdOptionsInvLvl.setText(String.valueOf(prod.getStock()));
        modProdOptionsPrice.setText(String.valueOf(prod.getPrice()));
        modProdOptionsMin.setText(String.valueOf(prod.getMin()));
        modProdOptionsMax.setText(String.valueOf(prod.getMax()));

        modProdOptionsPartsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        modProdOptionsPartsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        modProdOptionsPartsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("stock"));
        modProdOptionsPartsTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));

        modProdOptionsAssociatedParts.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        modProdOptionsAssociatedParts.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        modProdOptionsAssociatedParts.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("stock"));
        modProdOptionsAssociatedParts.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));

        modProdOptionsPartsTable.setItems(searchPartList);
        modProduct = new Product(0, "0", 0, 0, 0, 0);

        for (int i = 0; prod.getAllAssociatedParts().size() > i; i++) {
            modProduct.addAssociatedPart(prod.getAllAssociatedParts().get(i));
        }
        modProdOptionsAssociatedParts.setItems(modProduct.getAllAssociatedParts());
        }
    }

    /** Verifies proper inputs and modifies selected Product. */
    public void ModProductOptionsConfirmButtonHandler() {
        String ErrorText = "",
                nameField = modProdOptionsProdName.getText(),
                invField = modProdOptionsInvLvl.getText(),
                priceField = modProdOptionsPrice.getText(),
                minField = modProdOptionsMin.getText(),
                maxField = modProdOptionsMax.getText();

        Product prod = prodTable.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(modProdOptionsProdID.getText());
        String name = prod.getName();
        Double price = prod.getPrice();
        int inv = prod.getStock();
        int min = prod.getMin();
        int max = prod.getMax();

        ErrorText = PartVerify(nameField,invField,priceField,minField,maxField,"0");

        if (ErrorText !=""){
            ErrorHandler("Modify Product Error", ErrorText);
        }
        else if (ErrorText == ""){
            name = nameField;
            inv = Integer.parseInt(invField);
            price = Double.parseDouble(priceField);
            min = Integer.parseInt(minField);
            max = Integer.parseInt(maxField);

            Product newProd = new Product(id, name, price, inv, min, max);

            for (int i = 0; modProduct.getAllAssociatedParts().size() > i; i++) {
                newProd.addAssociatedPart(modProduct.getAllAssociatedParts().get(i));
            }

            Inventory.updateProduct(newProd.getId(), newProd);

            modProdOptionsProdID.setText("");
            modProdOptionsProdName.setText("");
            modProdOptionsPrice.setText("");
            modProdOptionsInvLvl.setText("");
            modProdOptionsMin.setText("");
            modProdOptionsMax.setText("");
            searchPartList.setPredicate(part -> {return true;});
            modProdOptionsSearch.setText("");
            modProdWindow.setExpanded(false);
            modProdWindow.setVisible(false);
            modProdWindow.setDisable(true);
        }
    }

    /** Remove Associated Part Function for Mod Part Window. */
    public void ModProductOptionsCancelButtonClickHandler() {
        modProdOptionsProdID.setText("");
        modProdOptionsProdName.setText("");
        modProdOptionsPrice.setText("");
        modProdOptionsInvLvl.setText("");
        modProdOptionsMin.setText("");
        modProdOptionsMax.setText("");
        searchPartList.setPredicate(part -> {return true;});
        modProdOptionsSearch.setText("");
        modProdWindow.setExpanded(false);
        modProdWindow.setVisible(false);
        modProdWindow.setDisable(true);

    }

    /** Removes Product from Product List. */
    public void ProductDeleteButtonClickHandler() {
        if(prodTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){

            if (prodTable.getSelectionModel().getSelectedItem() == null){
                ErrorHandler("Delete Error", "Please select a Product to delete");
            }
                else{
                        ConHandler();
                        option = 4;
                    }
        }
        else{
            ErrorHandler("Delete Error","This item cannot be\n deleted because there\n are associated parts");
        }

    }

    /** Closes the Scene / Program.  */
    public void exitButtonHandler() {
        Stage stage = (Stage) partsTable.getScene().getWindow();
        stage.close();
    }

    /** Shows Error Window and OK button, assigning text to argument text */
    public void ErrorHandler(String Header, String Text) {
        if (entryError.isDisable()) {
            entryError.setDisable(false);
            entryErrorOK.setDisable(false);
            entryError.setVisible(true);
            entryErrorOK.setVisible(true);
            entryError.setText(Header);
            errorText.setText(Text);
        }
    }

    /** hides and resets Error Window. */
    public void ErrorOKHandler() {
        entryError.setVisible(false);
        entryErrorOK.setVisible(false);
        entryError.setText("");
        errorText.setText("");
        entryError.setDisable(true);
        entryErrorOK.setDisable(true);
    }

    /** Changes Text field and label, and associated boolean. */
    public void inHouseRadioButtonClickHandler() {
        inHouseMachineIDLabel.setText("Machine ID :");
        modPartMachineID.setText("Machine ID");
        addPartOptionsInHouseMachineIDField.setPromptText("Enter Machine ID");
        modPartOptionsInHouseMachineIDField.setPromptText("Enter Machine ID");

    }

    /** Changes Text field and label, and associated boolean. */
    public void outsourcedRadioButtonClickHandler() {
        inHouseMachineIDLabel.setText("Company Name :");
        modPartMachineID.setText("Company Name");
        addPartOptionsInHouseMachineIDField.setPromptText("Enter Company Name");
        modPartOptionsInHouseMachineIDField.setPromptText("Enter Company Name");
    }

    /** Searches through Parts list on Main. */
    public void partsSearchBarHandler() {
        String searchTerm = partsSearchBar.getText().toLowerCase();

        searchPartList.setPredicate(part -> {

            if (searchTerm == null || searchTerm.isBlank()) {
                return true;
            }

            if (searchTerm.charAt(0) >= '0' && searchTerm.charAt(0) <= '9') {
                if ((part.getId() + "").contains(searchTerm)) {
                    return true;
                }
            }

            if (part.getName().toLowerCase().contains(searchTerm)) {
                return true;
            }

            return false;
        });

        if (searchPartList.size() < 1) {
            ErrorHandler("Part Search Error", "Nothing found\nPlease try again");
            searchPartList.setPredicate(product -> {return true;});
            partsSearchBar.setText("");
        }
    }

    /** Searches through Product list on Main. */
    public void prodSearchBarHandler() {
        String searchTerm = prodSearchBar.getText().toLowerCase();

        searchProductList.setPredicate(product -> {
            if (searchTerm == null || searchTerm.isBlank()) {
                return true;
            }

            if (searchTerm.charAt(0) >= '0' && searchTerm.charAt(0) <= '9') {
                if ((product.getId() + "").contains(searchTerm)) {
                    return true;
                }
            }

            if (product.getName().toLowerCase().contains(searchTerm)) {
                return true;
            }

            return false;
        });

        if (searchProductList.size() < 1) {
            ErrorHandler("Product Search Error", "Nothing found\n Please try again");
            searchProductList.setPredicate(product -> {return true;});
            prodSearchBar.setText("");
        }
    }

    /** Searches through Part list on Add Product Window. */
    public void addProdSearchHandler() {
        String searchTerm = addProdSearch.getText().toLowerCase();

        searchPartList.setPredicate(part -> {

            if (searchTerm == null || searchTerm.isBlank()) {
                return true;
            }
            if (searchTerm.charAt(0) >= '0' && searchTerm.charAt(0) <= '9') {
                if ((part.getId() + "").contains(searchTerm)) {
                    return true;
                }
            }

            if (part.getName().toLowerCase().contains(searchTerm)) {
                return true;
            }
            return false;
        });

        if (searchPartList.size() < 1){
            ErrorHandler("Part Search Error","Nothing found \n Please try again");
            searchPartList.setPredicate(product -> {return true;});
            addProdSearch.setText("");
        }

    }

    /** Searches through Part list on Mod Product Window. */
    public void modProdOptionsSearchHandler(){
        String searchTerm = modProdOptionsSearch.getText().toLowerCase();

        searchPartList.setPredicate(part -> {

            if(searchTerm == null || searchTerm.isBlank()){
                return true;
            }
            if (searchTerm.charAt(0) >= '0' && searchTerm.charAt(0) <= '9') {
                if ((part.getId() + "").contains(searchTerm)) {
                    return true;
                }
            }

            if (part.getName().toLowerCase().contains(searchTerm)) {
                return true;
            }
            return false;
        });

        if (searchPartList.size() < 1){
            ErrorHandler("Part Search Error","Nothing found \n Please try again");
            searchPartList.setPredicate(product -> {return true;});
            modProdOptionsSearch.setText("");
        }

    }

    /** Shows Confirmation Window and Buttons. */
    public void ConHandler(){
            ConWindow.setVisible(true);
            ConYes.setVisible(true);
            ConNo.setVisible(true);
    }

    /** Assigns Yes button to associated function. */
    public void ConYesHandler(){
        ConWindow.setVisible(false);
        ConYes.setVisible(false);
        ConNo.setVisible(false);
        DeletePart(option);
        option = 0;
    }

    /** Hides and resets Confirmation Window. */
    public void ConNoHandler(){
        ConWindow.setVisible(false);
        ConYes.setVisible(false);
        ConNo.setVisible(false);
    }

    /** Switch statement to parse Yes button function. */
    public void DeletePart(int option){
        switch (option) {
            case 1:
                Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
                break;
            case 2:
                Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
                addProduct.deleteAssociatedParts(selectedPart);
                break;
            case 3:
                Part selectedPart1 = modProdOptionsAssociatedParts.getSelectionModel().getSelectedItem();
                modProduct.deleteAssociatedParts(selectedPart1);
                break;
            case 4:
                Inventory.deleteProduct(prodTable.getSelectionModel().getSelectedItem());
                break;
        }
    }

    /** Checks a String for valid inputs, each char between 0 and 9. */
    public static boolean OnlyInt(String str, int n) {
        boolean isInt = false;
        for (int i = 0; i < n; i++) {
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                isInt = true;
            }
            else{
                isInt = false;
            }
        }
        return isInt;
    }

    /** Checks a Double for valid inputs, that it can be parsed. */
    public static boolean OnlyDouble(String str){
        double dub;
        try {
            dub = Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException ignore){
            return false;
        }

    }

    /** Checks each text field for appropriate values, depending on the field. */
    public String PartVerify(String nameField, String invField, String priceField, String minField, String maxField, String machineIDField){
        String errorText = "";

        if (nameField.isEmpty()) {
            errorText += "needs Part Name\n";
        }
        else if(nameField.length() > 20){
            errorText += "Name must be less than 20 characters\n";
        }

        if (invField.isEmpty()) {
            errorText += "needs Inventory Level\n";
        }
        else if(!OnlyInt(invField,invField.length())){
            errorText += "Inventory may only contain numbers";
        }

        if (priceField.isEmpty()) {
            errorText += "needs Price\n";
        }
        else if(!OnlyDouble(priceField)){
            errorText += "Price may contain only numbers and a decimal\n";
        }
        else if(priceField.length() > 10){
            errorText += "Price must be less than 10 digits\n";
        }

        if (minField.isEmpty()) {
            errorText += "needs Minimum \n";
        }
        else if(!OnlyInt(minField,minField.length())){
            errorText += "Minimum may contain only numbers\n";
        }

        if (maxField.isEmpty()) {
            errorText += "needs Maximum\n";
        }
        else if(!OnlyInt(maxField,maxField.length())) {
            errorText += "Maximum may contain only numbers\n";
        }
        else if (maxField.length() > 9){
            errorText += "Maximum too high";
        }

        if (machineIDField.isEmpty()) {
            if (modOutsourcedRadio.isSelected()) {
                errorText += "needs Company Name\n";
            }
            else if (modInHouseRadio.isSelected()){
                errorText += "needs Machine ID\n";
            }
        }
        else if((modInHouseRadio.isSelected()) && !OnlyInt(machineIDField,machineIDField.length())){
            errorText += "Machine ID must contain only numbers\n";
        }
        else if (modOutsourcedRadio.isSelected() && machineIDField.length() > 20){
            errorText += "Company Name must be less than 20 characters\n";
        }

    if (errorText == "") {
        if (Integer.parseInt(minField) > Integer.parseInt(maxField)) {
            errorText += "Minimum is greater than Maximum\n";
        }
        else if (Integer.parseInt(minField) < 0) {
            errorText += "Minimum must be greater than zero\n";
        }
        else if (Integer.parseInt(invField) < Integer.parseInt(minField)) {
            errorText += "Inventory less than Minimum\n";
        }
        else if (Integer.parseInt(invField) > Integer.parseInt(maxField)) {
            errorText += "Inventory greater than Maximum\n";
        }
    }
        return errorText;

    }
}

