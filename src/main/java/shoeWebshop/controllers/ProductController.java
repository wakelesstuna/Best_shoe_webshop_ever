package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import shoeWebshop.model.Utils.SendEmail;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private ImageView addToCart;

    @FXML
    private ImageView removeFromCart;

    @FXML
    private TableView<?> cartTable;

    @FXML
    private HBox totalPrice;

    @FXML
    private HBox cartBox;

    @FXML
    private Button sendOrder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.howIsLoggedIn);
            addToCart.setDisable(false);
            removeFromCart.setDisable(false);
            cartTable.setDisable(false);
            totalPrice.setDisable(false);
            cartBox.setDisable(false);
            sendOrder.setDisable(false);
        }else{
            loggedIn.setText("Logged in: not logged in");
            addToCart.setDisable(true);
            removeFromCart.setDisable(true);
            cartTable.setDisable(true);
            totalPrice.setDisable(true);
            cartBox.setDisable(true);
            sendOrder.setDisable(true);
        }

        // TODO: 2021-02-03 fill the table with all shoes in the database
    }

    public void sendOrder(){
        SendEmail email = new SendEmail("nackademinJava20A@gmail.com", "Shoe Order", null);
        System.out.println("sending");
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

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        FxmlUtils.howIsLoggedIn = "not logged in";
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }
}
