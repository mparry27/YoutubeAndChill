module CS3230Assignments {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.jsobject;

    opens gui;
    opens gui.controllers;
}