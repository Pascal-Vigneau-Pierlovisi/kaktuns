package com.example.kaktuns_project_media;

import java.io.File;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Controller implements Initializable{

    @FXML
    private MediaView mediaView;

    @FXML
    private ScrollPane panePlaylist;
    @FXML
    private Label mediaName;

    private MediaPlayer mediaPlayer;

    private int indexPlaylist=0;

    private ArrayList<File> listFile = new ArrayList<File>();
    private Stage stage;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {




    }

    public void playMedia() {
        this.mediaPlayer.play();


    }
    public void pauseMedia() {
        this.mediaPlayer.pause();
    }
    public void runMedia(){
       MediaPlayer.Status mediaStatus = mediaPlayer.getStatus();
       if (mediaStatus== MediaPlayer.Status.PLAYING){
         pauseMedia();
       }
       else {
           playMedia();
       }
    }


    public void previousMedia(){
        if(!listFile.isEmpty() && indexPlaylist!=0){
            pauseMedia();
            this.indexPlaylist=this.indexPlaylist-1;
            Media media = new Media(listFile.get(indexPlaylist).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(this.mediaPlayer);
            mediaName.setText(listFile.get(indexPlaylist).getName());
            playMedia();
        }

    }
    public void nextMedia(){
        if(!listFile.isEmpty() && indexPlaylist!=listFile.size()){
            pauseMedia();
            this.indexPlaylist=this.indexPlaylist+1;
            Media media = new Media(listFile.get(indexPlaylist).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(this.mediaPlayer);
            mediaName.setText(listFile.get(indexPlaylist).getName());
            playMedia();
        }
    }

    public void resetMedia() {

        if(this.mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            this.mediaPlayer.seek(Duration.seconds(0.0));
        }
    }

    public void selectMedia(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(new Stage());
        file = new File(file.getAbsolutePath());
        Media media = new Media(file.toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaView.setMediaPlayer(this.mediaPlayer);
        mediaName.setText(file.getName());


    }
    public void selectFolder(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        this.listFile=new ArrayList<>(Arrays.asList(Objects.requireNonNull(selectedDirectory.listFiles())));
        String textLabel= "Playlist sans nom \n\n";
        for(File file :listFile){
            textLabel=textLabel+"\n"+file.getName();

        }
        Label label1= new Label();
        label1.setText(textLabel.toString());
        panePlaylist.setContent(label1);

        Media media = new Media(listFile.get(0).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaView.setMediaPlayer(this.mediaPlayer);
        mediaName.setText(listFile.get(0).getName());
    }

    public void createPlaylist(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple File");
        List<File> files =fileChooser.showOpenMultipleDialog(new Stage()) ;
        this.listFile.clear();
        this.listFile.addAll(files);
        String textLabel= "Playlist sans nom \n\n";
        for(File file :listFile){
            textLabel=textLabel+"\n"+file.getName();

        }
        Label label1= new Label();
        label1.setText(textLabel.toString());
        panePlaylist.setContent(label1);

        Media media = new Media(listFile.get(0).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaView.setMediaPlayer(this.mediaPlayer);
        mediaName.setText(listFile.get(0).getName());


    }

    public void addMediaToPlaylist(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple File");
        List<File> files =fileChooser.showOpenMultipleDialog(new Stage()) ;
        this.listFile.addAll(files);

        String textLabel= "Playlist sans nom \n\n";
        for(File file :listFile){
            textLabel=textLabel+"\n"+file.getName();
        }
        Label label1= new Label();
        label1.setText(textLabel.toString());
        panePlaylist.setContent(label1);

    }

    public  void deleteMediaToPlaylist(){





    }
    public void savePlaylist(){
        System.out.println("hello");

    
    }
    public void loadPlaylist(){

    }

    }





