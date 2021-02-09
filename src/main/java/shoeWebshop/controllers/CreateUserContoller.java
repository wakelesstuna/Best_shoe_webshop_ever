package shoeWebshop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shoeWebshop.model.City;
import shoeWebshop.model.Utils.Database;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateUserContoller implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField socialSecurityNumber;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField Address;

    @FXML
    private TextField zipCode;

    @FXML
    private TextField city;

    @FXML
    private ComboBox cityBox;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMaxTextFieldCount(socialSecurityNumber,10);
        setMaxTextFieldCount(phoneNumber,10);
        setMaxTextFieldCount(zipCode,5);
        fillComboBox(cityBox);
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
        }else{
            loggedIn.setText("Logged in: not logged in");
        }
        eraseAllTextFields();
    }

    public void createUser(){

        String customerFirstName = firstName.getText();
        String customerLastName = lastName.getText();
        String customerSocialSecurityNumber = socialSecurityNumber.getText();
        String customerEmail = email.getText();
        String customerPhoneNumber = phoneNumber.getText();
        String customerAddress = Address.getText();
        int customerZipCode = Integer.parseInt(zipCode.getText());
        // String customerCity = city.getText();
        String customerCity = cityBox.getSelectionModel().toString();
        String customerPassword = password.getText();

        Database.createNewCustomer(customerFirstName,customerLastName, customerPhoneNumber, customerEmail, customerPassword, customerSocialSecurityNumber, customerAddress, customerCity, customerZipCode);

        eraseAllTextFields();

        FxmlUtils.changeScenes(FxmlUtils.loginView());
        FxmlUtils.showMessage("Logg in", "Please logg in to \nstart shopping", null, Alert.AlertType.INFORMATION);
    }

    public void eraseAllTextFields(){
        firstName.setText("");
        lastName.setText("");
        socialSecurityNumber.setText("");
        email.setText("");
        phoneNumber.setText("");
        Address.setText("");
        zipCode.setText("");
        city.setText("");
        password.setText("");

        firstName.setPromptText("first name");
        lastName.setPromptText("last name");
        socialSecurityNumber.setPromptText("social security number");
        email.setPromptText("email");
        phoneNumber.setPromptText("phone number");
        Address.setPromptText("address");
        zipCode.setPromptText("zip code");
        city.setPromptText("city");
        password.setPromptText("password");
    }

    public void setMaxTextFieldCount(TextField textField, int maxLength){
        textField.setOnKeyTyped(t -> {
            if (textField.getText().length() > maxLength) {
                int pos = textField.getCaretPosition();
                textField.setText(textField.getText(0, maxLength));
                textField.positionCaret(pos);
            }
        });
    }

    public void fillComboBox(ComboBox<String> comboBox){
        ObservableList<String> list = FXCollections.observableList(Database.getAllCities().stream().map(City::getCountyName).collect(Collectors.toList()));
        comboBox.setItems(list);
    }


    //---- Nav Links ----\\

    public void changeToHomeView(){
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }

    public void changeToProductView(){
        FxmlUtils.changeScenes(FxmlUtils.productView());
    }

    public void changeToReviewView() { FxmlUtils.changeScenes(FxmlUtils.reviewView());}

    public void changeToOrderView(){
        FxmlUtils.changeScenes(FxmlUtils.orderView());
    }

    public void changeToLoginView(){
        FxmlUtils.changeScenes(FxmlUtils.loginView());
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        FxmlUtils.whoIsLoggedIn = null;
        loggedIn.setText("");
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }
}
