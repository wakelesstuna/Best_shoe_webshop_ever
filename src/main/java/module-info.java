module shoeWebshop{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens shoeWebshop;
    opens shoeWebshop.controllers;
    opens shoeWebshop.model;
    opens view;
    opens css;
    opens img;
}