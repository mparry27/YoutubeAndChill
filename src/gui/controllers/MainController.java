package gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController {
    private WebEngine engine;

    @FXML
    private WebView video;

    @FXML
    private Label label;

    @FXML
    private Button play;

    @FXML
    private void initialize()
    {
        engine = video.getEngine();
        engine.load(getClass().getResource(("../Video.html")).toString());
//        engine.getLoadWorker().stateProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                engine.getHistory().go(-1);
//            }
//        });
    }

    @FXML
    private void toggleVideo() {
        engine.executeScript("toggleVideo();");
    }

}
