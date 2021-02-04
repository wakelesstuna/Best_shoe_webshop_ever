package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label loginText;


    String emailCheck = "oscar";
    String passwordCheck = "1234";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginEmail.setPromptText("Email");
        loginPassword.setPromptText("Password");
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.howIsLoggedIn);
        }else{
            loggedIn.setText("Logged in: not logged in");
        }
    }

    public void authorizeLogin(){
        // TODO: 2021-02-03 check with database if email and password is right

        if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()){
            FxmlUtils.showMessage("Input",
                    "Input","You need to enter a email\nand a password",
                    Alert.AlertType.ERROR);
        } else if(loginEmail.getText().equals(emailCheck) && loginPassword.getText().equals(passwordCheck)){
            loginText.setText("Welcome " + emailCheck);
            FxmlUtils.isLoggedIn = true;
            FxmlUtils.howIsLoggedIn = emailCheck;
            loggedIn.setText("Logged in: " + FxmlUtils.howIsLoggedIn);
            loginEmail.setText("");
            loginPassword.setText("");

        }else {
            FxmlUtils.showMessage("Warning",
                    "Couldn't find any user","Wrong email or password, try again",
                    Alert.AlertType.ERROR);
        }
    }

    //---- Nav Links ----\\

    public void changeToHomeView(){
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }

    public void changeToProductView(){
        FxmlUtils.changeScenes(FxmlUtils.productView());
    }

    public void changeToReviewView() { FxmlUtils.changeScenes(FxmlUtils.reviewView()); }

    public void changeToOrderView(){
        FxmlUtils.changeScenes(FxmlUtils.orderView());
    }

    public void changeToLoginView(){
        FxmlUtils.changeScenes(FxmlUtils.loginView());
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        FxmlUtils.howIsLoggedIn = "not logged in";
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }

}
