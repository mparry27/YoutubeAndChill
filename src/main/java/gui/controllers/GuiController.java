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

public class GuiController {
    private WebEngine engine;
    private JSObject page;
    private double oldSliderValue = 100;

    @FXML
    private WebView video;

    @FXML
    private Slider timeLine;

    @FXML
    private Slider volume;

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
    private ImageView muteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/MuteButton.png")));
    private ImageView lowIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/LowButton.png")));
    private ImageView highIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/HighButton.png")));

    @FXML
    private void initialize()
    {
        play.setGraphic(playIcon);
        forw.setGraphic(forwIcon);
        prev.setGraphic(prevIcon);
        mute.setGraphic(highIcon);

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

        volume.valueProperty().addListener((obs, oldValue, newValue) -> {
            engine.executeScript("player.setVolume(" + newValue.doubleValue() + ", true)");
            if(volume.getValue() == 0) {
                mute.setGraphic(muteIcon);
            } else if(volume.getValue() < 50) {
                mute.setGraphic(lowIcon);
            } else if(volume.getValue() >= 50) {
                mute.setGraphic(highIcon);
            }
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
        if((boolean)engine.executeScript("player.isMuted()") || volume.getValue() == 0) {
            engine.executeScript("player.unMute()");
            mute.setGraphic(highIcon);
            volume.setValue(oldSliderValue);
        } else {
            engine.executeScript("player.mute()");
            mute.setGraphic(muteIcon);
            oldSliderValue = volume.getValue();
            volume.setValue(0);
        }
    }

    @FXML
    private void nextVideo() {

    }

    @FXML
    private void prevVideo() {

    }

}
