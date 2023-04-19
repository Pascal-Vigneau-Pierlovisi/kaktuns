package com.example.kaktuns_project_media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Player {

    private double volume;
    private Playlist playlist;
    private MediaPlayer mediaPlayer;

    public Player() {
        volume = 0;
        playlist = new Playlist();
        mediaPlayer = null;
    }

    public void play() {
        mediaPlayer.play();
        mediaPlayer.setVolume(volume / 100);
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void run() {
        MediaPlayer.Status mediaStatus = mediaPlayer.getStatus();
        if (mediaStatus == MediaPlayer.Status.PLAYING) {
            pause();
        } else {
            play();
        }
    }

    public MediaPlayer setMediaFile() {
        MediaFile MediaFile = playlist.getMediaFilesList().get(playlist.mediaFileIndex);
        Media media = new Media(MediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }

    public void previous() {
        if (!playlist.getMediaFilesList().isEmpty()) {
            pause();
            if (playlist.mediaFileIndex == 0) {
                playlist.mediaFileIndex = playlist.getMediaFilesList().size() - 1;
            } else {
                playlist.mediaFileIndex--;
            }
            setMediaFile();
            play();
        }
    }

    public void next() {
        if (!playlist.getMediaFilesList().isEmpty()) {
            pause();
            if (playlist.mediaFileIndex == (playlist.getMediaFilesList().size() - 1)) {
                playlist.mediaFileIndex = 0;
            } else {
                playlist.mediaFileIndex++;
            }
            setMediaFile();
            play();
        }
    }

    public void reset() {
        if (mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
        }
        pause();
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public MediaFile getCurentMediaFile() {
        return playlist.getMediaFilesList().get(playlist.mediaFileIndex);
    }



}
