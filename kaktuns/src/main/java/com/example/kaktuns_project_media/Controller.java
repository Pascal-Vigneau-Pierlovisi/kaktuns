package com.example.kaktuns_project_media;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable{

    @FXML private MediaView mediaView;
    @FXML private ScrollPane panePlaylist;
    @FXML private Label mediaName;
    @FXML private Slider VolumeSlider;
    @FXML private Label volumeValue;
    @FXML private VBox stageVbox;
    private final Player player = new Player();
    private ListView<String> listViewPlaylist = new ListView<>();

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

    public void setPanePlaylistLabel(ListView<String> listView) {
        Label titre=new Label();
        titre.setText(player.getPlaylist().getPlaylistTitle());
        VBox vbox = new VBox();
        vbox.getChildren().addAll(titre, listView);
        panePlaylist.setContent(vbox);
    }

    public void selectMedia() throws Exception {
        File file = selectFile();
        if (MediaFile.isMediaFile(file)) {
            player.getPlaylist().addMediaFile(new MediaFile(file));
            updatePlaylistName();
            setPanePlaylistLabel(listViewPlaylist);
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
        TextInputDialog dialog = new TextInputDialog("New Playlist");
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText("Enter a name for the new playlist:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            ArrayList<Playlist> allPlaylist = Playlist.deserialize();
            while (true) {
                for (Playlist playlist : allPlaylist) {
                    if (playlist.getPlaylistTitle().equals(result.get())) {
                        dialog.setHeaderText("Playlist " + result.get() + " already exists\nEnter a name for the new playlist:");
                        result = dialog.showAndWait();
                        break;
                    }
                }
                break;
            }
        }

        List<File> files = selectMultipleFile();
        player.getPlaylist().clear();

        result.ifPresent(s -> player.getPlaylist().setPlaylistTitle(s));

        for (File file: files) {
            if (MediaFile.isMediaFile(file)) {
                MediaFile mediaFile = new MediaFile(file.getPath());
                player.getPlaylist().addMediaFile(mediaFile);
            }
        }

        if (player.getPlaylist().getMediaFilesList().size() != 0) {
            updatePlaylistName();
            setPanePlaylistLabel(listViewPlaylist);
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
                updatePlaylistName();
                setPanePlaylistLabel(listViewPlaylist);
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
        updatePlaylistName();
        setPanePlaylistLabel(listViewPlaylist);
    }

    public void removeMediaFromPlaylist(int index) {
        player.getPlaylist().removeMediaFile(index);
        updatePlaylistName();
        setPanePlaylistLabel(listViewPlaylist);
    }

    public void savePlaylist() {
        player.getPlaylist().serialize();
    }

    public void deleteMediaWindow() {
        ArrayList<MediaFile> listFile = player.getPlaylist().getMediaFilesList();
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        int rowIndex = 0;
        for (File file : listFile) {
            CheckBox checkBox = new CheckBox();
            Label label = new Label(file.getName());
            grid.add(checkBox, 0, rowIndex);
            grid.add(label, 1, rowIndex);
            rowIndex++;
        }

        Button button = new Button("Valider");
        button.setOnAction(event -> {
            List<MediaFile> fichiersCochees = new ArrayList<>();
            for (int i = 0; i < listFile.size(); i++) {
                CheckBox checkBox = (CheckBox) grid.getChildren().get(i * 2);
                if (checkBox.isSelected()) {
                    fichiersCochees.add(listFile.get(i));
                }
            }
            for (MediaFile filecheck : fichiersCochees) {
                player.getPlaylist().removeMediaFile(filecheck);
                }

            updatePlaylistName();
            player.getPlaylist().mediaFileIndex = 0;
            setPanePlaylistLabel(listViewPlaylist);
            stage.close();
        });

        Scene scene = new Scene(new VBox(10, grid, button), 250, 250);
        stage.setScene(scene);
        stage.show();
    }

    public void selectPlaylist() {
        Stage stage = new Stage();
        stage.setTitle("Select Playlist");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        player.setAllPlaylist(Playlist.deserialize());
        for (Playlist playlist : player.getAllPlaylist()) {
            Button btn = new Button(playlist.getPlaylistTitle());
            btn.setOnAction(event -> {
                player.setPlaylist(playlist);
                updatePlaylistName();
                setPanePlaylistLabel(listViewPlaylist);
                if (player.getMediaPlayer() == null) {
                    player.setMediaFile();
                    mediaName.setText(player.getCurentMediaFile().getFileName());
                }
                stage.close();
            });
            vbox.getChildren().add(btn);
        }

        Scene scene = new Scene(vbox, 200, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void OrderMediaWindow() {
        ArrayList<MediaFile> listFile = player.getPlaylist().getMediaFilesList();
        Stage stage = new Stage();
        stage.setTitle("Gestion de l'ordre des fichiers");
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 400, 400);
        Label label = new Label("Ordre des fichiers :");
        vbox.getChildren().add(label);

        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (File file : listFile) {
            items.add(file.getName());
        }
        listView.setItems(items);
        vbox.getChildren().add(listView);
        HBox hbox = new HBox();

        Button upButton = new Button("Monter");
        upButton.setOnAction(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 1) {
                String selectedItem = items.remove(selectedIndex);
                items.add(selectedIndex - 1, selectedItem);
                listView.getSelectionModel().select(selectedIndex - 1);
            }
        });
        hbox.getChildren().add(upButton);

        Button downButton = new Button("Descendre");
        downButton.setOnAction(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex < items.size() - 1 && selectedIndex >= 0) {
                String selectedItem = items.remove(selectedIndex);
                items.add(selectedIndex + 1, selectedItem);
                listView.getSelectionModel().select(selectedIndex + 1);
            }
        });
        hbox.getChildren().add(downButton);
        vbox.getChildren().add(hbox);

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(event -> {
            ArrayList<MediaFile> newListFiles = new ArrayList<>();
            for (String name : items) {
                for (MediaFile file : listFile) {
                    if (file.getName().equals(name)) {
                        newListFiles.add(file);
                        break;
                    }
                }
            }

            Playlist newPlaylist = new Playlist(player.getPlaylist().getPlaylistTitle(),newListFiles);
            player.getPlaylist().clear();
            player.setPlaylist(newPlaylist);
            player.getPlaylist().mediaFileIndex = 0;
            updatePlaylistName();
            setPanePlaylistLabel(listViewPlaylist);

            stage.close();
        });
        vbox.getChildren().add(saveButton);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void updatePlaylistName() {
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        ArrayList<MediaFile> fileList = player.getPlaylist().getMediaFilesList();
        for (File file : fileList) {
            String fileName = file.getName();
            items.add(fileName);
        }
        listView.setItems(items);
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                if (player.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                    player.setMediaPlayerNotSelected(player.getMediaPlayer());
                }
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                player.getPlaylist().setMediaFileIndex(selectedIndex);
                player.setMediaFile();
                mediaName.setText(player.getCurentMediaFile().getFileName());
            }
        });
        listViewPlaylist = listView;
    }
}
