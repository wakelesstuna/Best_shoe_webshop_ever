package shoeWebshop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import shoeWebshop.model.Orders;
import shoeWebshop.model.Product;
import shoeWebshop.utils.DateClock;
import shoeWebshop.utils.Repository;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static shoeWebshop.controllers.FxmlUtils.View.*;

public class OrderController implements Initializable {

    @FXML
    private Label loggedIn;

    @FXML
    private Label ordersText;

    @FXML
    private VBox selectOrderText;

    @FXML
    private TableView<Orders> ordersTable;

    @FXML
    private TableColumn<Orders, Integer> ordersOrderIdCol;

    @FXML
    private TableColumn<Orders, LocalDate> ordersDateCol;

    @FXML
    private TableColumn<Orders, Double> ordersTotalPriceCol;

    @FXML
    private TableColumn<Orders, Integer> ordersTotalProductsCol;

    @FXML
    private Button showOrderButton;

    @FXML
    private TableView<Product> selectedOrderTable;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> brandNameCol;

    @FXML
    private TableColumn<Product, Integer> sizeCol;

    @FXML
    private TableColumn<Product, String> colorCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableColumn<Product, Integer> quantityCol;

    @FXML
    private TextField totalPrice;

    @FXML
    private Label dateTimeLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalPrice.setAlignment(Pos.CENTER_RIGHT);
        if (FxmlUtils.isLoggedIn) {
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            ordersText.setText("Your orders");
            selectOrderText.setVisible(true);
            ordersTable.setDisable(false);
            showOrderButton.setDisable(false);
            selectedOrderTable.setDisable(false);
            totalPrice.setDisable(false);
            fillOrdersTable(Repository.getCustomerOrders(FxmlUtils.whoIsLoggedIn));

        } else {
            loggedIn.setText("Logged in: not logged in");
            ordersText.setText("You must be logged in to select a order");
            selectOrderText.setVisible(false);
            ordersTable.setDisable(true);
            showOrderButton.setDisable(true);
            selectedOrderTable.setDisable(true);
            totalPrice.setDisable(true);
        }
        new DateClock(dateTimeLabel);
    }

    public void fillSelectedOrderToTable() {
        Orders selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        brandNameCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("amountOrdered"));

        List<Product> products = Repository.getSelectedOrder(selectedOrder);
        selectedOrderTable.getItems().setAll(products);
        sumAllPricesInTable(products);
    }

    public void fillOrdersTable(List<Orders> list) {
        ordersOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ordersDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        ordersTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        ordersTotalProductsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfProducts"));

        ordersTable.getItems().setAll(list);
    }

    public void sumAllPricesInTable(List<Product> list) {
        double totalSum = list.stream().mapToDouble(e -> e.getPriceSek() * e.getAmountOrdered()).sum();

        totalPrice.setText(String.valueOf(totalSum));
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
