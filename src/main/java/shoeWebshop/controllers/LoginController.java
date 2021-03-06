package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import shoeWebshop.service.DateClock;
import shoeWebshop.dao.Repository;
import java.net.URL;
import java.util.ResourceBundle;

import static shoeWebshop.controllers.FxmlUtils.View.*;

public class LoginController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label loginText;

    @FXML
    private Label dateTimeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPromptTextOnLoggIn();

        if (FxmlUtils.isLoggedIn){

            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            loginText.setText("Logged in as " + FxmlUtils.whoIsLoggedIn.getFullName());
        }else{

            loggedIn.setText("Logged in: not logged in");
        }
        new DateClock(dateTimeLabel);
    }

    public void authorizeLogin(){

        String tempUser = loginEmail.getText().trim();
        String tempPass = loginPassword.getText().trim();

        if (tempUser.isEmpty() || tempPass.isEmpty()){

            FxmlUtils.showMessage("Input", "You need to enter a email\nand a password",null, Alert.AlertType.ERROR);
        } else {
            System.out.println("Innan if" + tempUser + tempPass);
            if(Repository.isAuthorizeLogin(tempUser,tempPass)){

                FxmlUtils.showMessage("Logged in", "You are logged in", null, Alert.AlertType.INFORMATION);
                loginText.setText("Welcome " + FxmlUtils.whoIsLoggedIn.getFullName());
                loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            } else {
                FxmlUtils.showMessage("Warning", "Wrong username or password", null, Alert.AlertType.ERROR);
            }
            setPromptTextOnLoggIn();
        }
    }

    private void setPromptTextOnLoggIn(){
        loginEmail.setText("");
        loginPassword.setText("");
        loginEmail.setPromptText("Email");
        loginPassword.setPromptText("Password");
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

    public void changeToCreateUserView(){
        FxmlUtils.changeView(CREATE_USER);
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        loggedIn.setText("");
        FxmlUtils.whoIsLoggedIn = null;
        Repository.discardOrder(FxmlUtils.currentCustomerOrder);
        changeToHomeView();
    }

}
