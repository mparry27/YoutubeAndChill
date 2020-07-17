package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/main.fxml"));
        primaryStage.setTitle("YouTube and Chill");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image("file:../resources/images/YoutubeAndChillIcon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
