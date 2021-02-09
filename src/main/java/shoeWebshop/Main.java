package shoeWebshop;

import javafx.scene.image.Image;
import shoeWebshop.controllers.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shoeWebshop.model.Product;
import shoeWebshop.model.Utils.Credentials;
import shoeWebshop.model.Utils.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    public static List<Product> list = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/main.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("img/iconmonstr-shop-4-96.png"));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        Credentials c = new Credentials();
        FxmlUtils.setCurrentStage(primaryStage);
        Database.getAllCustomers().forEach(System.out::println);


        //list.add(new Product("runner",399, 3, "blue", 36, "Nike"));
        //list.add(new Product("not runner",499, 5, "red", 37, "Nike"));
    }

    public static void main(String[] args) {
        launch(args);
    }


}
