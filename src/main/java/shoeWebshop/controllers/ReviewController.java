package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewController implements Initializable {

    @FXML
    private RadioButton reviewOne;

    @FXML
    private RadioButton reviewTwo;

    @FXML
    private RadioButton reviewThree;

    @FXML
    private RadioButton reviewFour;

    @FXML
    private RadioButton reviewFive;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup reviewGroup = new ToggleGroup();
        reviewOne.setToggleGroup(reviewGroup);
        reviewTwo.setToggleGroup(reviewGroup);
        reviewThree.setToggleGroup(reviewGroup);
        reviewFour.setToggleGroup(reviewGroup);
        reviewFive.setToggleGroup(reviewGroup);
    }




    //---- Nav Links ----\\

    public void changeToHomeView(){
        ChangeView.changeScenes(ChangeView.homeView());
    }

    public void changeToProductView(){
        ChangeView.changeScenes(ChangeView.productView());
    }

    public void changeToReviewView() {
        ChangeView.changeScenes(ChangeView.reviewView());
    }

    public void changeToOrderView(){
        ChangeView.changeScenes(ChangeView.orderView());
    }

    public void changeToLoginView(){
        ChangeView.changeScenes(ChangeView.loginView());
    }


}
