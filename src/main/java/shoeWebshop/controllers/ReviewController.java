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
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }

    public void changeToProductView(){
        FxmlUtils.changeScenes(FxmlUtils.productView());
    }

    public void changeToReviewView() {
        FxmlUtils.changeScenes(FxmlUtils.reviewView());
    }

    public void changeToOrderView(){
        FxmlUtils.changeScenes(FxmlUtils.orderView());
    }

    public void changeToLoginView(){
        FxmlUtils.changeScenes(FxmlUtils.loginView());
    }


}
