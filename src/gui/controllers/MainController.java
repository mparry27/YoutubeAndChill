package gui.controllers;

import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class MainController {
    @FXML
    private WebView video;

    @FXML
    private void initialize()
    {
        WebEngine engine = video.getEngine();
        engine.load(getClass().getResource(("../Video.html")).toString());
    }

    @FXML
    private void toggleVideo() {
        video.getEngine().executeScript("stopVideo()");
    }

}
