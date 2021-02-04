package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable {



    @FXML
    private Label loggedIn;

    @FXML
    private Label ordersText;

    @FXML
    private VBox selectOrderText;

    @FXML
    private TableView<?> orderTable;

    @FXML
    private Button showOrderButton;

    @FXML
    private TableView<?> selectedOrderTable;

    @FXML
    private HBox totalPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.howIsLoggedIn);
            ordersText.setText("Your orders");
            selectOrderText.setVisible(true);
            orderTable.setDisable(false);
            showOrderButton.setDisable(false);
            selectedOrderTable.setDisable(false);
            totalPrice.setDisable(false);

            // TODO: 2021-02-03 as database for the loggedin customers orders and fill the table

        }else{
            loggedIn.setText("Logged in: not logged in");
            ordersText.setText("You must be logged in to select a order");
            selectOrderText.setVisible(false);
            orderTable.setDisable(true);
            showOrderButton.setDisable(true);
            selectedOrderTable.setDisable(true);
            totalPrice.setDisable(true);
        }
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
