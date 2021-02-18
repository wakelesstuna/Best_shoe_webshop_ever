package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import shoeWebshop.model.Utils.Repository;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPromptTextOnLoggIn();

        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            loginText.setText("Logged in as " + FxmlUtils.whoIsLoggedIn.getFullName());
        }else{
            loggedIn.setText("Logged in: not logged in");
        }
    }

    public void authorizeLogin(){

        if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()){
            FxmlUtils.showMessage("Input", "You need to enter a email\nand a password",null, Alert.AlertType.ERROR);

        } else {
            if(Repository.isAuthorizeLogin(loginEmail.getText(),loginPassword.getText())){

                FxmlUtils.showMessage("Logged in", "You are logged in", null, Alert.AlertType.INFORMATION);

                System.out.println(FxmlUtils.whoIsLoggedIn);

                loginText.setText("Welcome " + FxmlUtils.whoIsLoggedIn.getFullName());
                loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            } else {
                FxmlUtils.showMessage("Warning", "Wrong username or password", null, Alert.AlertType.ERROR);
            }
            setPromptTextOnLoggIn();
        }
    }

    public void setPromptTextOnLoggIn(){
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
        changeToHomeView();
    }

}
