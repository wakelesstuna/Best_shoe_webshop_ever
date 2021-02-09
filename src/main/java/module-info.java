module shoeWebshop{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;

    opens shoeWebshop;
    opens shoeWebshop.controllers;
    opens shoeWebshop.model;
    opens shoeWebshop.model.Utils;
    //opens shoeWebshop.sql;
    opens view;
    opens css;
    opens img;
}