module YoutubeAndChill {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.jsobject;
    requires java.net.http;
    requires org.json;
    requires com.google.gson;

    opens domain;
    opens domain.video;
}