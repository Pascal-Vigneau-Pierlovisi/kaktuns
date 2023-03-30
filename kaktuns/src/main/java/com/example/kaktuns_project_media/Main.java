package com.example.kaktuns_project_media;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage)  {
        try{
            Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("v6.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Kaktuns");
            Image image=new Image("C:\\Users\\ulwar\\IdeaProjects\\kaktuns\\src\\main\\java\\com\\example\\kaktuns_project_media\\logo.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
