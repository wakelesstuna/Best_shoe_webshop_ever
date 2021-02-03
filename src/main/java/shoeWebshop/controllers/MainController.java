package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private Label navHome;

    @FXML
    private Label navProduct;

    @FXML
    private Label navReview;

    @FXML
    private Label navOrders;

    @FXML
    private Label navLogin;

    public void changeToLoginView(){
        ChangeView.changeScenes(ChangeView.loginView());
    }




}
