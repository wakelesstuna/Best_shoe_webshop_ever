module shoeWebshop{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;
    requires org.json;

    opens shoeWebshop;
    opens shoeWebshop.controllers;
    opens shoeWebshop.model;
    opens shoeWebshop.service;
    //opens shoeWebshop.sql;
    opens view;
    opens css;
    opens img;
}