module YoutubeAndChill {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.jsobject;
    requires java.net.http;
    requires com.google.gson;
    requires org.json;

    opens gui;
    opens gui.controllers;
    opens domain;
    opens domain.video;
}