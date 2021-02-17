package shoeWebshop.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import shoeWebshop.model.Customer;
import java.io.IOException;
import java.util.Objects;

public class FxmlUtils {

    public static boolean isLoggedIn = false;
    public static Customer whoIsLoggedIn;
    public static Integer currentCustomerOrder;
    private static Stage currentStage;
    public static boolean orderCreatedButNotSent;

    public static void showMessage(String title, String header, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void setCurrentStage(Stage primaryStage) {
        currentStage = primaryStage;
    }

    public static void changeScenes(Scene scene){
            currentStage.setScene(scene);
            currentStage.show();
    }

    enum View {
        MAIN("main"),
        PRODUCT("product"),
        REVIEW("review"),
        ORDER("order"),
        LOGIN("login"),
        CREATE_USER("createUser"),
        RENDER_TEST("renderTest");

        final String s;

        View(String s) {
            this.s = s;
        }

        public String getStringValue(){
            return s;
        }
    }

    public static void changeView(View view) {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/" + view.getStringValue() + ".fxml")));
            scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Scene homeView() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/main.fxml")));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scene;
    }

    public static Scene productView() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/product.fxml")));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scene;
    }

    public static Scene reviewView() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/review.fxml")));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scene;
    }

    public static Scene orderView() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/order.fxml")));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scene;
    }


    public static Scene loginView() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/login.fxml")));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scene;
    }

    public static Scene createUserView() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/createUser.fxml")));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scene;
    }
}
