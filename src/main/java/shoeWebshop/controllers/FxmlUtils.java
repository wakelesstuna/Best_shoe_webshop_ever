package shoeWebshop.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import shoeWebshop.model.Customer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FxmlUtils.class.getClassLoader().getResource("view/" + view.getStringValue() + ".fxml")));
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void currentDateTimeSetter(Label label){
        while (true){
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            label.setText(dateTime);
            sleep(1);
        }

    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
