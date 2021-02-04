package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.howIsLoggedIn);
            addToCart.setDisable(false);
            removeFromCart.setDisable(false);
            cartTable.setDisable(false);
            totalPrice.setDisable(false);
            cartBox.setDisable(false);
        }else{
            loggedIn.setText("Logged in: not logged in");
            addToCart.setDisable(true);
            removeFromCart.setDisable(true);
            cartTable.setDisable(true);
            totalPrice.setDisable(true);
            cartBox.setDisable(true);
        }

        // TODO: 2021-02-03 fill the table with all shoes in the database
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
