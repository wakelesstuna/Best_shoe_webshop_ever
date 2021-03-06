package shoeWebshop.controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import shoeWebshop.model.Category;
import shoeWebshop.model.Orders;
import shoeWebshop.model.Product;
import shoeWebshop.service.DateClock;
import shoeWebshop.dao.Repository;
import shoeWebshop.service.SendEmail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static shoeWebshop.controllers.FxmlUtils.View.*;

public class ProductController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label loggedIn;

    @FXML
    private GridPane productView = new GridPane();

    @FXML
    private ComboBox<String> sortCategory;

    @FXML
    private ComboBox<String> sortPrice;

    @FXML
    private ComboBox<String> sortSize;

    @FXML
    private ComboBox<String> sortBrand;

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
    private HBox cartBox;

    @FXML
    private HBox totalPrice;

    @FXML
    private TextField showTotalPrice;

    @FXML
    private Button sendOrder;

    @FXML
    private Button newOrderBtn;

    @FXML
    private Button removeFromCart;

    @FXML
    private Button discardOrderBtn;

    @FXML
    private Label dateTimeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTotalPrice.setAlignment(Pos.CENTER_RIGHT);
        if (FxmlUtils.isLoggedIn) {
            loggedIn.setText("Logged in: " + FxmlUtils.whoIsLoggedIn.getFullName());
            if (FxmlUtils.orderCreatedButNotSent) {
                fillCartTable(Repository.getSelectedOrder(new Orders(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn)));
            } else {
                customerLoggedInButNoOrderCreated();
            }
        } else {
            loggedIn.setText("Logged in: not logged in");

            removeFromCart.setDisable(true);
            cartTable.setDisable(true);
            totalPrice.setDisable(true);
            cartBox.setDisable(true);
            sendOrder.setDisable(true);
            newOrderBtn.setDisable(true);
            discardOrderBtn.setDisable(true);
        }

        renderAllProducts(Repository.getAllProducts());
        fillCategory();
        fillBrand();
        fillSizes();
        fillPrice();

        new DateClock(dateTimeLabel);
    }

    //---------------------- CART FUNCTIONS ---------------------------------------------------------------------------\\

    private void customerLoggedInButNoOrderCreated() {
        removeFromCart.setDisable(true);
        cartTable.setDisable(true);
        totalPrice.setDisable(false);
        cartBox.setDisable(false);
        newOrderBtn.setDisable(false);
        sendOrder.setDisable(true);
        discardOrderBtn.setDisable(true);
    }

    public void createNewOrder() {
        FxmlUtils.currentCustomerOrder = Repository.createNewOrder(FxmlUtils.whoIsLoggedIn);
        System.out.println("New Order number: " + FxmlUtils.currentCustomerOrder);
        removeFromCart.setDisable(false);
        cartTable.setDisable(false);
        sendOrder.setDisable(false);
        newOrderBtn.setDisable(true);
        discardOrderBtn.setDisable(false);

        FxmlUtils.orderCreatedButNotSent = true;
        renderAllProducts(Repository.getAllProducts());
    }

    public void addToCart(Product p) {

        if (p.getStock() == 0) {
            FxmlUtils.showMessage("Error", "Sorry, selected product is out of stock!", null, Alert.AlertType.ERROR);
        } else {
            if (FxmlUtils.isLoggedIn) {
                Repository.addToCart(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn, p.getId());
                renderAllProducts(Repository.getAllProducts());

                try {
                    fillCartTable(Repository.getSelectedOrder(new Orders(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn)));
                    addToTotalPrice(p);
                } catch (NullPointerException ignored) {
                }
            }
        }
    }

    public void removeFromCart() {
        Product selectedItem = cartTable.getSelectionModel().getSelectedItem();

        if (!(selectedItem.getAmountOrdered() == 0)) {
            Repository.removeFromCart(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn, selectedItem.getId());
            renderAllProducts(Repository.getAllProducts());
            cartTable.getItems().remove(selectedItem);

            Orders tempOrder = new Orders(FxmlUtils.currentCustomerOrder, FxmlUtils.whoIsLoggedIn);
            fillCartTable(Repository.getSelectedOrder(tempOrder));
            substractFromTotalPrice(selectedItem);
        }
    }

    public void sendOrder() {
        SendEmail.sendOrderConfirmMail(FxmlUtils.whoIsLoggedIn.getEmail(), "Shoe Order", cartTable.getItems(), FxmlUtils.whoIsLoggedIn.getFullName());
        FxmlUtils.showMessage("Order", "Order Sent!\nThank you for ordering from\nBest Shoe Shop Ever!", null, Alert.AlertType.INFORMATION);

        FxmlUtils.orderCreatedButNotSent = false;

        cartTable.getItems().clear();
        showTotalPrice.setText("0");
        customerLoggedInButNoOrderCreated();
    }

    public void discardOrder() {
        System.out.println("Order number to discard: " + FxmlUtils.currentCustomerOrder);
        Repository.discardOrder(FxmlUtils.currentCustomerOrder);

        renderAllProducts(Repository.getAllProducts());
        FxmlUtils.orderCreatedButNotSent = false;

        cartTable.getItems().clear();
        showTotalPrice.setText("0");
        customerLoggedInButNoOrderCreated();
    }

    public void fillCartTable(List<Product> list) {
        try {
            cartModelCul.setCellValueFactory(new PropertyValueFactory<>("productName"));
            cartBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("priceSek"));
            cartSizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
            cartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("amountOrdered"));

            cartTable.getItems().setAll(list);
        } catch (NullPointerException ignored) {
        }

    }

    public void addToTotalPrice(Product product) {
        double price = Double.parseDouble(showTotalPrice.getText());
        showTotalPrice.setText(price + product.getPriceSek() + "");
    }

    public void substractFromTotalPrice(Product product) {
        double price = Double.parseDouble(showTotalPrice.getText());
        showTotalPrice.setText(price - product.getPriceSek() + "");
    }

    //---------------------- FILTER BOXES ---------------------------------------------------------------------------\\

    private void fillCategory() {
        ObservableList<String> categories = FXCollections.observableList(Repository.getAllCategories().stream().map(Category::getCategoryName).collect(Collectors.toList()));
        categories.add(0, "Categories");
        sortCategory.setItems(categories.sorted());
        filterByCategory();
    }

    private void fillBrand() {
        List<Product> products = Repository.getAllProducts();
        ObservableList<String> brands = FXCollections.observableList(products.stream().map(Product::getBrand).distinct().collect(Collectors.toList()));
        brands.add(0, "Brands");
        sortBrand.setItems(brands.sorted());
        filterByBrand();
    }

    private void fillSizes() {
        List<Product> products = Repository.getAllProducts();
        ObservableList<String> sizes = FXCollections.observableList(products.stream().map(e -> e.getSize() + "").distinct().collect(Collectors.toList()));
        sizes.add(0, "Sizes");
        sortSize.setItems(sizes);
        filterBySizes();
    }

    private void fillPrice() {
        List<Product> products = Repository.getAllProducts();
        ObservableList<String> prices = FXCollections.observableList(products.stream().map(e -> e.getPriceSek() + "").distinct().collect(Collectors.toList()));
        prices.add(0, "Prices");
        sortPrice.setItems(prices);
        filterByPrice();
    }

    private void filterByCategory() {
        sortCategory.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    System.out.println(sortCategory.getItems().get(new_val.intValue()));
                    setComboBoxToFirstValue(sortBrand, sortPrice, sortSize);
                    List<Product> categories = Repository.getProductsOfCategory(sortCategory.getItems().get(new_val.intValue()));
                    productView.getChildren().clear();
                    renderAllProducts(categories);
                });
    }

    private void filterByBrand() {
        sortBrand.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    setComboBoxToFirstValue(sortCategory, sortPrice, sortSize);
                    List<Product> brands = Repository.getAllProducts().stream()
                            .filter(e -> e.getBrand().equalsIgnoreCase(sortBrand.getItems().get(new_val.intValue())))
                            .collect(Collectors.toList());
                    productView.getChildren().clear();
                    renderAllProducts(brands);
                });
    }

    private void filterBySizes() {
        sortSize.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    setComboBoxToFirstValue(sortCategory, sortPrice, sortBrand);
                    try {
                        List<Product> sizes = Repository.getAllProducts().stream()
                                .filter(e -> e.getSize() == Double.parseDouble(sortSize.getItems().get(new_val.intValue())))
                                .collect(Collectors.toList());
                        productView.getChildren().clear();
                        renderAllProducts(sizes);
                    } catch (NumberFormatException e) {
                        System.out.println();
                    }
                });
    }

    private void filterByPrice() {
        sortPrice.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    setComboBoxToFirstValue(sortCategory, sortSize, sortBrand);
                    try {
                        List<Product> prices = Repository.getAllProducts().stream()
                                .filter(e -> e.getPriceSek() == Double.parseDouble(sortPrice.getItems().get(new_val.intValue())))
                                .collect(Collectors.toList());
                        productView.getChildren().clear();
                        renderAllProducts(prices);
                    } catch (NumberFormatException e) {
                        System.out.println();
                    }
                });
    }

    private void setComboBoxToFirstValue(ComboBox<?> comboBox1, ComboBox<?> comboBox2, ComboBox<?> comboBox3) {
        comboBox1.getSelectionModel().selectFirst();
        comboBox2.getSelectionModel().selectFirst();
        comboBox3.getSelectionModel().selectFirst();
    }

    //---------------------- RENDER PRODUCTS ------------------------------------------------------------------------\\

    private void renderAllProducts(List<Product> products) {
        int counter1 = 0;
        int counter2 = 1;
        productView.setVisible(true);
        productView.setStyle("-fx-background-color: #B0B9C8;");
        scrollPane.setStyle("-fx-background-color:transparent;");

        for (Product p : products) {

            String productName = p.getProductName();
            String brand = p.getBrand();
            String color = p.getColor();
            double size = p.getSize();
            double price = p.getPriceSek();
            int stock = p.getStock();

            Pane pane = createPane();
            Label title = createTitleLabel(productName);
            ImageView productImage = getImageOfProduct(p);
            Label info = createInfoLabel(brand, color, size, price, stock);
            Button button = createButton("Add to cart", p);

            VBox vBox = createVBox(title, productImage, info, button);

            pane.getChildren().add(vBox);
            productView.add(pane, counter1, counter2++);

            // counter for product placement in grid
            if (counter1 == 1) {
                counter1--;
                counter2++;
            } else {
                counter1++;
                counter2--;
            }
        }
    }

    private Pane createPane() {
        Pane pane = new Pane();
        pane.setMinWidth(250);
        pane.setMinHeight(350);
        pane.setPrefWidth(250);
        pane.setPrefHeight(350);
        pane.setVisible(true);
        return pane;
    }

    private Label createTitleLabel(String productName) {
        Label label = new Label();
        label.setText(productName);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 20.0));
        return label;
    }

    private ImageView getImageOfProduct(Product p) {
        String imgPath;
        FileInputStream file = null;
        switch (p.getProductName()) {
            case "flex runner" -> imgPath = "flexrunner";
            case "air max fusion" -> imgPath = "airmaxfusion";
            case "solar boost" -> imgPath = "solarboost";
            case "offroad" -> imgPath = "offroad";
            case "questar" -> imgPath = "questar";
            case "pegasus" -> imgPath = "pegasus";
            case "keke" -> imgPath = "keke";
            case "kaptir" -> imgPath = "kaptir";
            case "Vomero" -> imgPath = "Vomero";
            case "lace-up" -> imgPath = "laceup";
            case "lace-up-b" -> imgPath = "laceupb";
            case "tanjun" -> imgPath = "tanjun";
            case "ultra boost" -> imgPath = "ultraboost";

            default -> imgPath = "noimage";
        }

        try {
            file = new FileInputStream("./src/main/resources/img/product/" + imgPath + ".png");
            return setImageValues(new Image(file));

        } catch (FileNotFoundException EX) {
            try {
                file = new FileInputStream("./src/main/resources/img/product/noimage.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return setImageValues(new Image(file));
        }
    }

    private ImageView setImageValues(Image img) {
        ImageView imageView = new ImageView();
        imageView.setX(30);
        imageView.setY(50);
        imageView.setImage(img);
        imageView.setVisible(true);
        return imageView;
    }

    private Label createInfoLabel(String brand, String color, double size, double price, int stock) {
        Label label = new Label();
        label.setText("" + brand + "     " + color + "   " + size + " \n" + price + " sek\nStock: " + stock);
        label.setFont(new Font(15));
        label.setStyle("-fx-padding: 5px;");
        label.setStyle("-fx-padding: 2px;");
        return label;
    }

    private Button createButton(String s, Product p) {
        Button btn = new Button();
        btn.setText(s);
        btn.setPadding(new Insets(3, 2, 3, 2));
        btn.setStyle("-fx-background-color: linear-gradient(to right top, #B37E39, #E7B13A); " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0); " +
                "-fx-padding: 10px; " +
                "-fx-font-weight: BOLD; " +
                "-fx-font-size: 12;");
        btn.setOnAction(event -> addToCart(p));
        return btn;
    }

    private VBox createVBox(Label title, ImageView productImage, Label info, Button button) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setMinSize(300, 250);
        vBox.setPrefHeight(330);
        vBox.getChildren().add(title);
        vBox.getChildren().add(productImage);
        vBox.getChildren().add(info);
        vBox.getChildren().add(button);
        vBox.setStyle("-fx-background-color: linear-gradient(to left top, #B37E39, #E7B13A); " +
                "-fx-background-radius: 50px;" +
                "-fx-border-radius: 50px; " +
                "-fx-alignment: Center;");
        return vBox;
    }

    //---- Nav Links ----\\

    public void changeToProductView() {
        FxmlUtils.changeView(PRODUCT);
    }

    public void changeToHomeView() {
        FxmlUtils.changeView(MAIN);
    }

    public void changeToReviewView() {
        FxmlUtils.changeView(REVIEW);
    }

    public void changeToOrderView() {
        FxmlUtils.changeView(ORDER);
    }

    public void changeToLoginView() {
        FxmlUtils.changeView(LOGIN);
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        loggedIn.setText("");
        FxmlUtils.whoIsLoggedIn = null;
        Repository.discardOrder(FxmlUtils.currentCustomerOrder);
        changeToHomeView();
    }
}
