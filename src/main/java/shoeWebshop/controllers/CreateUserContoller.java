package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shoeWebshop.model.Customer;
import shoeWebshop.model.Utils.SendEmail;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn);
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
        String customerCity = city.getText();
        String customerPassword = password.getText();
        SendEmail.sendCreateUserMail(customerEmail, "New Customer", customerFirstName + " " + customerLastName, customerPassword);

        eraseAllTextFields();
        FxmlUtils.showMessage(null,null,"user created!", Alert.AlertType.INFORMATION);


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

    private void buildCreateUserMessage(){

    }

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
        FxmlUtils.whoIsLoggedIn = "not logged in";
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }
}
