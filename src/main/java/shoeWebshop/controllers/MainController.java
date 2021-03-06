package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import shoeWebshop.service.DateClock;
import shoeWebshop.dao.Repository;

import java.net.URL;
import java.util.ResourceBundle;

import static shoeWebshop.controllers.FxmlUtils.View.*;

public class MainController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private Label dateTimeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
        }else{
            loggedIn.setText("Logged in: not logged in");
        }
        new DateClock(dateTimeLabel);
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
        loggedIn.setText("");
        FxmlUtils.whoIsLoggedIn = null;
        Repository.discardOrder(FxmlUtils.currentCustomerOrder);
        changeToHomeView();
    }
}