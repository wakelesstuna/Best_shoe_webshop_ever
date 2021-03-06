package shoeWebshop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shoeWebshop.service.DateClock;
import shoeWebshop.dao.Repository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static shoeWebshop.controllers.FxmlUtils.View.*;

public class CreateUserController implements Initializable {

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
    private ComboBox cityBox;

    @FXML
    private TextField password;

    @FXML
    private Label dateTimeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMaxTextFieldCount(socialSecurityNumber,10);
        setMaxTextFieldCount(phoneNumber,10);
        setMaxTextFieldCount(zipCode,5);
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
        }else{
            loggedIn.setText("Logged in: not logged in");
        }
        eraseAllTextFields();

        zipCode.setOnKeyTyped(event -> {
            List<String> temp = new ArrayList<>();
            if (zipCode.getText().length() > 4) {
                int pos = zipCode.getCaretPosition();
                zipCode.setText(zipCode.getText(0, 5));
                zipCode.positionCaret(pos);
            }
            temp.add(Repository.getCitiesOfSweden(zipCode.getText().toLowerCase()));
            fillComboBox(temp);

        });

        new DateClock(dateTimeLabel);
    }

    public void createUser(){
        String customerFirstName = firstName.getText();
        String customerLastName = lastName.getText();
        String customerSocialSecurityNumber = socialSecurityNumber.getText();
        String customerEmail = email.getText();
        String customerPhoneNumber = phoneNumber.getText();
        String customerAddress = Address.getText();
        int customerZipCode = Integer.parseInt(zipCode.getText());
        String customerCity = (String) cityBox.getValue();
        String customerPassword = password.getText();

        Repository.createNewCustomer(customerFirstName,customerLastName, customerPhoneNumber, customerEmail, customerPassword, customerSocialSecurityNumber, customerAddress, customerCity, customerZipCode);
        eraseAllTextFields();

        FxmlUtils.changeView(LOGIN);
        FxmlUtils.showMessage("Logg in", "Please logg in to \nstart shopping", null, Alert.AlertType.INFORMATION);
    }

    private void eraseAllTextFields(){
        firstName.setText("");
        lastName.setText("");
        socialSecurityNumber.setText("");
        email.setText("");
        phoneNumber.setText("");
        Address.setText("");
        zipCode.setText("");
        password.setText("");

        firstName.setPromptText("first name");
        lastName.setPromptText("last name");
        socialSecurityNumber.setPromptText("social security number");
        email.setPromptText("email");
        phoneNumber.setPromptText("phone number");
        Address.setPromptText("address");
        zipCode.setPromptText("zip code");
        password.setPromptText("password");
    }

    private void setMaxTextFieldCount(TextField textField, int maxLength){
        textField.setOnKeyTyped(t -> {
            if (textField.getText().length() > maxLength) {
                int pos = textField.getCaretPosition();
                textField.setText(textField.getText(0, maxLength));
                textField.positionCaret(pos);
            }
        });
    }

    private void fillComboBox(List<String> input){
        ObservableList<String> list = FXCollections.observableList(input);
        cityBox.setItems(list);
    }

    //---- Nav Links ----\\

    public void changeToProductView(){
        FxmlUtils.changeView(PRODUCT);
    }

    public void changeToHomeView(){
        FxmlUtils.changeView(MAIN);
    }

    public void changeToReviewView() {
        FxmlUtils.changeView(REVIEW);
    }

    public void changeToOrderView(){
        FxmlUtils.changeView(ORDER);
    }

    public void changeToLoginView(){
        FxmlUtils.changeView(LOGIN);
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        FxmlUtils.whoIsLoggedIn = null;
        loggedIn.setText("");
        Repository.discardOrder(FxmlUtils.currentCustomerOrder);
        changeToHomeView();
    }
}
