package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import shoeWebshop.model.Orders;
import shoeWebshop.model.Product;
import shoeWebshop.model.Utils.Repository;
import shoeWebshop.model.Utils.SendEmail;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static shoeWebshop.controllers.FxmlUtils.View.*;

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

    @FXML
    private Button newOrderBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTotalPrice.setAlignment(Pos.CENTER_RIGHT);
        if (FxmlUtils.isLoggedIn){
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            if (FxmlUtils.orderCreatedButNotSent){
                fillCartTable(Repository.getSelectedOrder(new Orders(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn)));
            }else {
                customerLoggedInButNoOrderCreated();
            }
        }else{
            loggedIn.setText("Logged in: not logged in");
            addToCart.setDisable(true);
            removeFromCart.setDisable(true);
            cartTable.setDisable(true);
            totalPrice.setDisable(true);
            cartBox.setDisable(true);
            sendOrder.setDisable(true);
            newOrderBtn.setDisable(true);
        }
        fillProductTable(Repository.getAllProducts());
    }



    public void createNewOrder(){
        FxmlUtils.currentCustomerOrder = Repository.createNewOrder(FxmlUtils.whoIsLoggedIn);
        addToCart.setDisable(false);
        removeFromCart.setDisable(false);
        cartTable.setDisable(false);
        sendOrder.setDisable(false);
        newOrderBtn.setDisable(true);

        FxmlUtils.orderCreatedButNotSent = true;
    }

    public void addToCart(){
        Product selectedItem = productTable.getSelectionModel().getSelectedItem();

        if (selectedItem.getStock() == 0){
            FxmlUtils.showMessage("Error", "Sorry, selected product is out of stock!", null, Alert.AlertType.ERROR);
        }else{
            Repository.addToCart(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn, selectedItem.getId());
            FxmlUtils.showMessage("Added to cart", "New item added to your cart", null, Alert.AlertType.INFORMATION);

            fillProductTable(Repository.getAllProducts());
            fillCartTable(Repository.getSelectedOrder(new Orders(FxmlUtils.currentCustomerOrder,FxmlUtils.whoIsLoggedIn)));
            addToTotalPrice(selectedItem);
        }
    }

    public void removeFromCart(){
        Product selectedItem = cartTable.getSelectionModel().getSelectedItem();

        if (!(selectedItem.getAmountOrdered() == 0)){
            Repository.removeFromCart(FxmlUtils.currentCustomerOrder,FxmlUtils.whoIsLoggedIn,selectedItem.getId());
            fillProductTable(Repository.getAllProducts());
            cartTable.getItems().remove(selectedItem);

            Orders tempOrder = new Orders(FxmlUtils.currentCustomerOrder,FxmlUtils.whoIsLoggedIn);
            fillCartTable(Repository.getSelectedOrder(tempOrder));
            substractFromTotalPrice(selectedItem);
        }
    }

    public void sendOrder(){
        SendEmail.sendOrderConfirmMail(FxmlUtils.whoIsLoggedIn.getEmail(), "Shoe Order", cartTable.getItems(), FxmlUtils.whoIsLoggedIn.getFullName());
        FxmlUtils.showMessage("Order", "Order Sent!\nThank you for ordering from\nBest Shoe Shop Ever!", null, Alert.AlertType.INFORMATION);

        FxmlUtils.orderCreatedButNotSent = false;

        cartTable.getItems().clear();
        showTotalPrice.setText("0");
        customerLoggedInButNoOrderCreated();
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

    public void fillCartTable(List<Product> list){
        cartModelCul.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cartBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
        cartSizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        cartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("amountOrdered"));

        cartTable.getItems().setAll(list);
    }

    private void customerLoggedInButNoOrderCreated(){
        addToCart.setDisable(true);
        removeFromCart.setDisable(true);
        cartTable.setDisable(true);
        totalPrice.setDisable(false);
        cartBox.setDisable(false);
        newOrderBtn.setDisable(false);
        sendOrder.setDisable(true);
    }

    public void addToTotalPrice(Product product){
        double price = Double.parseDouble(showTotalPrice.getText());
        showTotalPrice.setText(price + product.getPriceSek() + "");
    }

    public void substractFromTotalPrice(Product product){
        double price = Double.parseDouble(showTotalPrice.getText());
        showTotalPrice.setText(price - product.getPriceSek() + "");
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
