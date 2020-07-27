module CS3230Assignments {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.jsobject;
    requires java.net.http;

    opens gui;
    opens gui.controllers;
}