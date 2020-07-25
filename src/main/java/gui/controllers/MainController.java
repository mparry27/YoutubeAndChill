package gui.controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MainController {
    private WebEngine engine;
    private JSObject page;

    @FXML
    private WebView video;

    @FXML
    private Slider timeLine;

    @FXML
    private Button play;
    private ImageView playIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/PlayButton.png")));
    private ImageView pauseIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/PauseButton.png")));

    @FXML
    private Button prev;
    private ImageView prevIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/BackwardButton.png")));

    @FXML
    private Button forw;
    private ImageView forwIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/ForwardButton.png")));

    @FXML
    private Button mute;

    @FXML
    private void initialize()
    {
        play.setGraphic(playIcon);
        forw.setGraphic(forwIcon);
        prev.setGraphic(prevIcon);

        engine = video.getEngine();
        engine.load(getClass().getResource(("/Video.html")).toString());

        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                page = (JSObject) engine.executeScript("window");
                page.setMember("javaConnector", this);
            }
        });

        timeLine.valueProperty().addListener((obs, oldValue, newValue) -> {
            engine.executeScript("player.seekTo(" + newValue.doubleValue() + ", true)");
        });
    }

    @FXML
    public void startVideo() {
        timeLine.setValue(0);
        play.setGraphic(pauseIcon);
        timeLine.setMax((double)engine.executeScript("player.getDuration()"));

    }

    @FXML
    private void togglePauseVideo() {
        engine.executeScript("toggleVideo();");
        if((int)page.getMember("state") == 1) {//playing
            play.setGraphic(playIcon);
        } else if ((int)page.getMember("state") == 2) {//paused
            play.setGraphic(pauseIcon);
        }
    }

    @FXML
    private void toggleMuteVideo() {

    }

    @FXML
    private void nextVideo() {

    }

    @FXML
    private void prevVideo() {

    }

}
