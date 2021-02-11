package shoeWebshop.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import shoeWebshop.Main;
import shoeWebshop.model.Product;
import shoeWebshop.model.Utils.Database;
import shoeWebshop.model.Utils.SendEmail;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private ImageView addToCart;

    @FXML
    private ImageView removeFromCart;

    @FXML
    private HBox totalPrice;

    @FXML
    private HBox cartBox;

    @FXML
    private Button sendOrder;

    @FXML
    private TextField showTotalPrice;

    //----- product table ------\\

    @FXML
    private TableView<Product> productTable;

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
    private TableColumn<Product, Integer> stockCol;

    //---- Cart Table ---- \\

    @FXML
    private TableView<Product> cartTable;

    @FXML
    private TableColumn<Product, String> cartModelCul;

    @FXML
    private TableColumn<Product, String> cartBrandCol;

    @FXML
    private TableColumn<Product, Double> cartPriceCol;

    @FXML
    private TableColumn<Product, Integer> cartSizeCol;

    @FXML
    private TableColumn<Product, Integer> cartQuantityCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTotalPrice.setText("0");
        showTotalPrice.setAlignment(Pos.CENTER_RIGHT);
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
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
        fillProductTable(Database.getAllProducts());
    }

    public void sendOrder(){
        List<Product> products = cartTable.getItems();

        // TODO: 2021-02-11 AddToCart funktion

        SendEmail.sendOrderConfirmMail("nackademinJava20A@gmail.com", "Shoe Order", products, FxmlUtils.whoIsLoggedIn.getFullName());
        FxmlUtils.showMessage("Order", "Order Sent!", "Thank you for ordering from\nBest Shoe Shop Ever!", Alert.AlertType.INFORMATION);
        cartTable.getItems().clear();
        showTotalPrice.setText("0");
    }

    public void fillProductTable(List<Product> list){
            modelCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
            brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
            sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

            productTable.getItems().setAll(list);
    }

    public void addToCart(){
        System.out.println("+");

        Product selectedItem = productTable.getSelectionModel().getSelectedItem();

        cartModelCul.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cartBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
        cartSizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        cartQuantityCol.setCellValueFactory(e -> new SimpleIntegerProperty(1).asObject());

        cartTable.getItems().add(selectedItem);

        // add to price
        double price = Double.parseDouble(showTotalPrice.getText());
        showTotalPrice.setText(price + selectedItem.getPriceSek() + "");
    }

    public void removeFromCart(){
        System.out.println("-");

        Product selectedItem = cartTable.getSelectionModel().getSelectedItem();
        cartTable.getItems().remove(selectedItem);

        // remove from price
        double price = Double.parseDouble(showTotalPrice.getText());
        showTotalPrice.setText(price - selectedItem.getPriceSek() + "");
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
        loggedIn.setText("");
        FxmlUtils.whoIsLoggedIn = null;
        FxmlUtils.changeScenes(FxmlUtils.homeView());
    }
}
