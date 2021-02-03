package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label loginText;


    String emailCheck = "oscar";
    String passwordCheck = "1234";

    public void authorizeLogin(){
        if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()){
            FxmlUtils.showMessage("You need to enter a email\nand a password",
                    "Input",
                    Alert.AlertType.ERROR);
        } else if(loginEmail.getText().equals(emailCheck) && loginPassword.getText().equals(passwordCheck)){
            loginText.setText("Welcome " + emailCheck);
        }else {
            FxmlUtils.showMessage("Wrong email or password, try again",
                    "Input",
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

}
