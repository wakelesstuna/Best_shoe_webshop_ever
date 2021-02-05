module shoeWebshop{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.mail;

    opens shoeWebshop;
    opens shoeWebshop.controllers;
    opens shoeWebshop.model;
    opens shoeWebshop.model.Utils;
    opens view;
    opens css;
    opens img;
}