package com.example.kaktuns_project_media;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
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
            stage.show();





        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}