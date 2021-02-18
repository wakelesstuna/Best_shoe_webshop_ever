package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import shoeWebshop.model.Product;
import shoeWebshop.model.ReviewObject;
import shoeWebshop.model.Utils.Repository;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static shoeWebshop.controllers.FxmlUtils.View.*;

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

    private TableColumn<Product, Double> avgScoreCol;

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

    ToggleGroup reviewGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reviewGroup = new ToggleGroup();
        reviewOne.setToggleGroup(reviewGroup);
        reviewTwo.setToggleGroup(reviewGroup);
        reviewThree.setToggleGroup(reviewGroup);
        reviewFour.setToggleGroup(reviewGroup);
        reviewFive.setToggleGroup(reviewGroup);
        reviewText.setText("");
        fillReviewTable(Repository.getAllProducts());

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
    }

    public void showReview(){
        // TODO: 2021-02-13 fråga sigrun om detta är regelrätt?
        Product selectedItem = choseShoeToReview.getSelectionModel().getSelectedItem();
        String message;
        try{
            message = buildReviewString(Repository.getReviewObject(selectedItem));
        } catch (IndexOutOfBoundsException e){
            message = "No review yet on the selected product";
        }

        FxmlUtils.showMessage("Review", message, null, Alert.AlertType.INFORMATION);
    }

    public void leaveReview(){
        Product selectedProduct = choseShoeToReview.getSelectionModel().getSelectedItem();
        int selectedReviewScore = Integer.parseInt(((RadioButton)reviewGroup.getSelectedToggle()).getText());

        Repository.createNewReview(selectedProduct,selectedReviewScore,reviewText.getText());
        reviewGroup.getSelectedToggle().setSelected(false);
        reviewText.setText("");

    }

    public void fillReviewTable(List<Product> list) {

        modelCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        choseShoeToReview.getItems().setAll(list);
    }

    public void setAvgScore(List<ReviewObject> list){
        double averageReviewScore = list.stream().mapToDouble(ReviewObject::getRating).average().orElseThrow();
        avgScoreCol.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        Product tempProduct = new Product(averageReviewScore);
        choseShoeToReview.getItems().set(6,tempProduct);

    }

    private String buildReviewString(List<ReviewObject> list){
        StringBuilder sb = new StringBuilder();
        String productName = list.get(0).getProductName();
        double size = list.get(0).getSize();
        double averageReviewScore = list.stream().mapToDouble(ReviewObject::getRating).average().orElseThrow();

        sb.append("Review of shoe: ")
                .append(productName)
                .append("   Product size: ")
                .append(size)
                .append("\n")
                .append("Average product score: ")
                .append(averageReviewScore)
                .append("\n")
                .append("\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append("Review nr: ")
                    .append(i+1)
                    .append("   Review by: ")
                    .append(list.get(i).getCustomerName())
                    .append("\n")
                    .append("Score: ")
                    .append(list.get(i).getRating())
                    .append(" Date: ")
                    .append(list.get(i).getDate())
                    .append("\n")
                    .append("Comment: ")
                    .append(list.get(i).getReview())
                    .append("\n")
                    .append("\n");
        }

        return sb.toString();
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
        changeToHomeView();
    }

}
