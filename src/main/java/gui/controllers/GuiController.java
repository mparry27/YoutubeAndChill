package gui.controllers;

import domain.video.Video;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class GuiController{
    private WebEngine engine;
    private JSObject page;
    private double oldSliderValue = 100;
    private ArrayList<Video> videoQueue;
    private Timeline timelineSync;

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
    private TextField url;

    @FXML
    private Button add;
    private ImageView addIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/AddButton.png")));

    @FXML
    private void initialize() {
        play.setGraphic(playIcon);
        forw.setGraphic(forwIcon);
        prev.setGraphic(prevIcon);
        mute.setGraphic(highIcon);
        add.setGraphic(addIcon);

        engine = video.getEngine();
        engine.load(getClass().getResource(("/Video.html")).toString());

        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                page = (JSObject) engine.executeScript("window");
                page.setMember("javaConnector", this);
            }
        });

        timelineSync = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!timeLine.isFocused() && (int)page.getMember("state") == 1) {
                    timeLine.setValue((double)engine.executeScript("player.getCurrentTime()"));
                }
            }
        }));

        timeLine.valueProperty().addListener((obs, oldValue, newValue) -> {
            if((int)page.getMember("state") == 2)
                engine.executeScript("player.seekTo(" + newValue.doubleValue() + ");");
        });

        volume.valueProperty().addListener((obs, oldValue, newValue) -> {
            engine.executeScript("player.setVolume(" + newValue.doubleValue() + ", true)");
            if(volume.getValue() == 0) {
                mute.setGraphic(muteIcon);
            } else if(volume.getValue() < 50) {
                engine.executeScript("player.unMute()");
                mute.setGraphic(lowIcon);
            } else if(volume.getValue() >= 50) {
                engine.executeScript("player.unMute()");
                mute.setGraphic(highIcon);
            }
        });
    }

    @FXML
    public void startVideo() {
        timeLine.setValue(0);
        play.setGraphic(pauseIcon);
        timeLine.setMax((double)engine.executeScript("player.getDuration()"));
        timelineSync.setCycleCount(Timeline.INDEFINITE);
        timelineSync.play();
    }

    @FXML
    private void startTimelineSeek() {
        if((int)page.getMember("state") == 1){
            togglePauseVideo();
            engine.executeScript("player.seekTo(" + timeLine.getValue() + ");");
        }
    }

    @FXML
    private void endTimelineSeek() {
        if((int)page.getMember("state") == 1) {
            togglePauseVideo();
        }
        play.requestFocus();
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

    @FXML
    private void highlightText() {
        url.clear();
        url.requestFocus();
    }
}
