package com.example.kaktuns_project_media;

import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable{

    @FXML private MediaView mediaView;
    @FXML private ScrollPane panePlaylist;
    @FXML private Label mediaName;
    @FXML private Slider VolumeSlider;
    @FXML private Label volumeValue;
    @FXML private VBox stageVbox;
    private MediaPlayer mediaPlayer=null;
    private ArrayList<File> listFile = new ArrayList<File>();
    private final Player player = new Player();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       VolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
           player.setVolume(newValue.doubleValue());
           volumeValue.setText(String.valueOf((int) player.getVolume()));
           if (player.getMediaPlayer() != null) {
               player.getMediaPlayer().setVolume(newValue.doubleValue() / 100);
           }
       });
    }

    public void runMedia(){
       player.run();
    }

    public void previousMedia() {
        player.previous();
        mediaName.setText(player.getCurentMediaFile().getFileName());
    }

    public void nextMedia(){
        player.next();
        mediaName.setText(player.getCurentMediaFile().getFileName());
    }

    public void resetMedia() {
        player.reset();
    }

    public File selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        return fileChooser.showOpenDialog(new Stage());
    }

    public void setMediaFile() {
        mediaView.setMediaPlayer(player.setMediaFile());
        mediaName.setText(player.getCurentMediaFile().getFileName());
    }

    public void setPanePlaylistLabel(String textLabel) {
        Label label = new Label();
        label.setText(textLabel);
        panePlaylist.setContent(label);
    }

    public void selectMedia() throws Exception {
        File file = selectFile();
        if (MediaFile.isMediaFile(file)) {
            player.getPlaylist().addMediaFile(new MediaFile(file));
            setPanePlaylistLabel(player.getPlaylist().getPlaylistName());
            if (player.getMediaPlayer() == null) {
                setMediaFile();
            }
        }
    }

    public List<File> selectMultipleFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Multiple File");
        return fileChooser.showOpenMultipleDialog(new Stage());
    }

    public void createPlaylist() throws Exception {
        List<File> files = selectMultipleFile();
        player.getPlaylist().clear();
        for (File file: files) {
            if (MediaFile.isMediaFile(file)) {
                MediaFile mediaFile = new MediaFile(file.getPath());
                player.getPlaylist().addMediaFile(mediaFile);
            }
        }

        if (player.getPlaylist().getMediaFilesList().size() != 0) {
            setPanePlaylistLabel(player.getPlaylist().getPlaylistName());
            setMediaFile();
        }
    }

    public File selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(new Stage());
    }

    public ArrayList<File> getAllFilesFromDirectory(File selectedDirectory) {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(selectedDirectory.listFiles())));
    }

    public void selectFolder() throws Exception {
        File selectedDirectory = selectDirectory();
        if (selectedDirectory != null) {
            ArrayList<File> files = getAllFilesFromDirectory(selectedDirectory);
            player.getPlaylist().clear();
            for (File file : files) {
                if (MediaFile.isMediaFile(file)) {
                    MediaFile mediaFile = new MediaFile(file.getPath());
                    player.getPlaylist().addMediaFile(mediaFile);
                }
            }
            if (player.getPlaylist().getMediaFilesList().size() != 0) {
                setPanePlaylistLabel(player.getPlaylist().getPlaylistName());
                setMediaFile();
            }
        }
    }

    public void addMediaToPlaylist() throws Exception {
        List<File> files = selectMultipleFile();
        for (File file: files) {
            if (MediaFile.isMediaFile(file)) {
                MediaFile mediaFile = new MediaFile(file.getPath());
                player.getPlaylist().addMediaFile(mediaFile);
            }
        }
        setPanePlaylistLabel(player.getPlaylist().getPlaylistName());
    }

    public void removeMediaFromPlaylist(int index) {
        player.getPlaylist().removeMediaFile(index);
        setPanePlaylistLabel(player.getPlaylist().getPlaylistName());
    }

    public void savePlaylist() {
        player.getPlaylist().serialize();
    }

    public void loadPlaylist() {
        player.setPlaylist(Playlist.deserialize());
        player.getPlaylist().mediaFileIndex = 0;
        setPanePlaylistLabel(player.getPlaylist().getPlaylistName());
        setMediaFile();
    }

    public void popup() {
        Stage stage = (Stage) stageVbox.getScene().getWindow();
        TilePane tilepane = new TilePane();
        Scene scene = new Scene(tilepane, 200, 200);
        for (int i = 0; i < listFile.size(); i++) {
            Label nomMedia = new Label(listFile.get(i).getName());
            CheckBox checkbox = new CheckBox();
            checkbox.setId(String.valueOf(i));
            tilepane.getChildren().add(nomMedia);
            tilepane.getChildren().add(checkbox);

        }
        stage.setScene(scene);
        stage.show();
    }

}
