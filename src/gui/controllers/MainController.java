package gui.controllers;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MainController {
    private WebEngine engine;
    private JSObject page;

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
        page = (JSObject)engine.executeScript("window");
        page.setMember("app", this);
    }

    @FXML
    private void toggleVideo() {
        engine.executeScript("toggleVideo();");
        if((int)page.getMember("state") == 2) {//playing
            play.setStyle("-fx-graphic: 'resources/images/PauseButton.png'");
        } else if ((int)page.getMember("state") == 1) {//paused
            play.setStyle("-fx-graphic: 'resources/images/PlayButton.png'");
        }
    }

}
