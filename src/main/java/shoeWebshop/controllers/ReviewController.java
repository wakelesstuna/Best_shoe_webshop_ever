package shoeWebshop.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import shoeWebshop.Main;
import shoeWebshop.model.Product;
import shoeWebshop.model.Utils.Database;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReviewController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private Label leaveReviewText;

    @FXML
    private TableView<Product> choseShoeToReview;

    @FXML
    private TableColumn<Product, String> modelCol;

    @FXML
    private TableColumn<Product, String> brandCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableColumn<Product, Integer> sizeCol;

    @FXML
    private TableColumn<Product, String> colorCol;

    @FXML
    private HBox radioButtonBox;

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

    @FXML
    private TextArea reviewText;

    @FXML
    private Button leaveReviewButton;

    String selectedShoe = "runner";
    int reviewScore = 8;
    ToggleGroup reviewGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reviewGroup = new ToggleGroup();
        reviewOne.setToggleGroup(reviewGroup);
        reviewTwo.setToggleGroup(reviewGroup);
        reviewThree.setToggleGroup(reviewGroup);
        reviewFour.setToggleGroup(reviewGroup);
        reviewFive.setToggleGroup(reviewGroup);
        fillReviewTable(Database.getAllProducts());

        if (FxmlUtils.isLoggedIn) {
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            leaveReviewText.setText("Fill in the form to leave a review");
            radioButtonBox.setDisable(false);
            reviewText.setDisable(false);
            leaveReviewButton.setDisable(false);
        } else {
            loggedIn.setText("Logged in: not logged in");
            leaveReviewText.setText("You need to be logged in to leave a review");
            radioButtonBox.setDisable(true);
            reviewText.setDisable(true);
            leaveReviewButton.setDisable(true);
        }

        // TODO: 2021-02-03 fill the table with shoes that have a review

    }

    public void showReview(){
        // TODO: 2021-02-03 get review from database based on selecetedShoe

        FxmlUtils.showMessage("Reviews", "Review of " + selectedShoe + "\nReview score: " + reviewScore,
                "Mycket bra sko, fin passform", Alert.AlertType.INFORMATION);
    }

    public void leaveReview(){
        Product selectedProduct = choseShoeToReview.getSelectionModel().getSelectedItem();
        int selectedReviewScore = Integer.parseInt(((RadioButton)reviewGroup.getSelectedToggle()).getText());

        Database.createNewReview(selectedProduct,selectedReviewScore,reviewText.getText());
    }

    public void fillReviewTable(List<Product> list) {

        modelCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        choseShoeToReview.getItems().setAll(list);
    }


    //---- Nav Links ----\\

    public void changeToHomeView() {
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }

    public void changeToProductView() {
        FxmlUtils.changeScenes(FxmlUtils.productView());
    }

    public void changeToReviewView() {
        FxmlUtils.changeScenes(FxmlUtils.reviewView());
    }

    public void changeToOrderView() {
        FxmlUtils.changeScenes(FxmlUtils.orderView());
    }

    public void changeToLoginView() {
        FxmlUtils.changeScenes(FxmlUtils.loginView());
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        loggedIn.setText("");
        FxmlUtils.whoIsLoggedIn = null;
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }

}
