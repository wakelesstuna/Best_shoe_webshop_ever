package shoeWebshop;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shoeWebshop.service.Credentials;
import shoeWebshop.controllers.*;
import java.util.Objects;

public class Main extends Application {

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

        new Credentials();
        FxmlUtils.setCurrentStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
