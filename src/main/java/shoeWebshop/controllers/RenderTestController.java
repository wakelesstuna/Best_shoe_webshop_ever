package shoeWebshop.controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import shoeWebshop.model.Category;
import shoeWebshop.model.City;
import shoeWebshop.model.Product;
import shoeWebshop.model.Utils.Database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static shoeWebshop.controllers.FxmlUtils.View.*;
import static shoeWebshop.controllers.FxmlUtils.View.RENDER_TEST;

public class RenderTestController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label loggedIn;

    @FXML
    private GridPane addTo = new GridPane();

    @FXML
    private ComboBox<String> sortCategory;

    @FXML
    private ComboBox<String> sortPrice;

    @FXML
    private ComboBox<String> sortSize;

    @FXML
    private ComboBox<String> sortBrand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Product> products = Database.getAllProducts();
        renderAllProducts(products);
        fillCategory();
        fillBrand();
        fillSizes();
        fillPrice();

    }


    public void fillCategory() {
        System.out.println("fill category");
        ObservableList<String> categories = FXCollections.observableList(Database.getAllCategories().stream().map(Category::getCategoryName).collect(Collectors.toList()));
        categories.add(0, "Categories");
        sortCategory.setItems(categories.sorted());
        filterByCategory();
    }

    public void filterByCategory(){
        sortCategory.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    System.out.println(sortCategory.getItems().get(new_val.intValue()));
                    setComboBoxToFirstValue(sortBrand,sortPrice,sortSize);
                    List<Product> categories = Database.getProductsOfCategory(sortCategory.getItems().get(new_val.intValue()));
                    addTo.getChildren().clear();
                    renderAllProducts(categories);

                });
    }

    public void fillBrand() {
        List<Product> products = Database.getAllProducts();
        System.out.println("fill brand");
        ObservableList<String> brands = FXCollections.observableList(products.stream().map(Product::getBrand).distinct().collect(Collectors.toList()));
        brands.add(0, "Brands");
        sortBrand.setItems(brands.sorted());
        filterByBrand();
    }

    public void filterByBrand() {
        sortBrand.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    setComboBoxToFirstValue(sortCategory, sortPrice, sortSize);
                    List<Product> brands = Database.getAllProducts().stream()
                            .filter(e -> e.getBrand().equalsIgnoreCase(sortBrand.getItems().get(new_val.intValue())))
                            .collect(Collectors.toList());
                    addTo.getChildren().clear();
                    renderAllProducts(brands);
                });
    }


    public void fillSizes() {
        List<Product> products = Database.getAllProducts();
        System.out.println("fill sizes");
        ObservableList<String> sizes = FXCollections.observableList(products.stream().map(e -> e.getSize() + "").distinct().collect(Collectors.toList()));
        sizes.add(0, "Sizes");
        sortSize.setItems(sizes);
        filterBySizes();
    }

    public void filterBySizes() {
        sortSize.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    setComboBoxToFirstValue(sortCategory, sortPrice, sortBrand);
                    try {
                        List<Product> sizes = Database.getAllProducts().stream()
                                .filter(e -> e.getSize() == Double.parseDouble(sortSize.getItems().get(new_val.intValue())))
                                .collect(Collectors.toList());
                        addTo.getChildren().clear();
                        renderAllProducts(sizes);
                    } catch (NumberFormatException e) {
                        System.out.println();
                    }
                });
    }

    public void fillPrice() {
        List<Product> products = Database.getAllProducts();
        System.out.println("fill price");
        ObservableList<String> prices = FXCollections.observableList(products.stream().map(e -> e.getPriceSek() + "").distinct().collect(Collectors.toList()));
        prices.add(0, "Prices");
        sortPrice.setItems(prices);
        filterByPrice();
    }

    public void filterByPrice() {
        sortPrice.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    setComboBoxToFirstValue(sortCategory, sortSize, sortBrand);
                    try {
                        List<Product> prices = Database.getAllProducts().stream()
                                .filter(e -> e.getPriceSek() == Double.parseDouble(sortPrice.getItems().get(new_val.intValue())))
                                .collect(Collectors.toList());
                        addTo.getChildren().clear();
                        renderAllProducts(prices);
                    } catch (NumberFormatException e) {
                        System.out.println();
                    }
                });
    }

    public void setComboBoxToFirstValue(ComboBox<?> comboBox1, ComboBox<?> comboBox2, ComboBox<?> comboBox3) {
        comboBox1.getSelectionModel().selectFirst();
        comboBox2.getSelectionModel().selectFirst();
        comboBox3.getSelectionModel().selectFirst();
    }

    public void renderAllProducts(List<Product> products) {
        int counter1 = 0;
        int counter2 = 1;
        addTo.setVisible(true);
        addTo.setStyle("-fx-background-color: #B0B9C8;");
        scrollPane.setStyle("-fx-background-color:transparent;");
        System.out.println(products.size());

        for (Product p : products) {
            System.out.println("1" + p);
            Pane pane = new Pane();
            pane.setMinWidth(250);
            pane.setMinHeight(350);
            pane.setPrefWidth(250);
            pane.setPrefHeight(350);
            pane.setVisible(true);

            String productName = p.getProductName();
            String brand = p.getBrand();
            String color = p.getColor();
            double size = p.getSize();
            double price = p.getPriceSek();


            Label info = new Label();
            info.setText("Model: " + brand + " Color: " + color + "\nSize: " + size + " Price: " + price + " sek");
            info.setFont(new Font(15));
            info.setStyle("-fx-padding: 5px;");
            Label title = new Label();
            title.setText(productName);
            title.setFont(Font.font("Verdana", FontWeight.BOLD, 20.0));
            info.setStyle("-fx-padding: 2px;");
            Button button = new Button();
            button.setStyle("-fx-background-color: linear-gradient(to right top, #B37E39, #E7B13A); " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0); " +
                    "-fx-padding: 10px; " +
                    "-fx-font-weight: BOLD; " +
                    "-fx-font-size: 12;");


            EventHandler<ActionEvent> buttonHandler = event -> {
                System.out.println(p.getId());
            };
            button.setOnAction(buttonHandler);

            button.setText("Add to cart");
            button.setPadding(new Insets(3, 2, 3, 2));


            ImageView productImage = null;
            try {
                productImage = getImageOfProduct(p);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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

            pane.getChildren().add(vBox);
            addTo.add(pane, counter1, counter2++);
            if (counter1 == 1) {
                counter1--;
                counter2++;
            } else {
                counter1++;
                counter2--;
            }
            System.out.println(counter1);
        }

    }

    public ImageView getImageOfProduct(Product p) throws FileNotFoundException {
        ImageView imageView = new ImageView();
        String product = "";
        switch (p.getProductName()) {
            case "flex runner" -> product = "flexrunner";
            case "air max fusion" -> product = "airmaxfusion";
            case "solar boost" -> product = "solarboost";
            case "offroad" -> product = "offroad";
            case "questar" -> product = "questar";
            case "pegasus" -> product = "pegasus";
            case "keke" -> product = "keke";
            case "kaptir" -> product = "kaptir";
            case "Vomero" -> product = "Vomero";
            case "lace-up" -> product = "laceup";
            case "lace-up-b" -> product = "laceupb";
            case "tanjun" -> product = "tanjun";

            default -> product = "noimage";
        }

        try {
            FileInputStream file = new FileInputStream("./src/main/resources/img/product/" + product + ".png");
            Image img = new Image(file);
            imageView.setX(30);
            imageView.setY(50);
            imageView.setImage(img);
            imageView.setVisible(true);
        } catch (FileNotFoundException EX) {
            FileInputStream file = new FileInputStream("./src/main/resources/img/product/noimage.png");
            Image img = new Image(file);
            imageView.setX(30);
            imageView.setY(50);
            imageView.setImage(img);
            imageView.setVisible(true);
            return imageView;
        }

        return imageView;

    }


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

    public void changeToRenderTest() {
        FxmlUtils.changeView(RENDER_TEST);
    }

    public void loggOut() {
        FxmlUtils.isLoggedIn = false;
        loggedIn.setText("");
        FxmlUtils.whoIsLoggedIn = null;
        changeToHomeView();
    }
}
