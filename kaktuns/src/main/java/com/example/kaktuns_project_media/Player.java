package com.example.kaktuns_project_media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public class Player {

    private double volume;
    private Playlist playlist;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayerNotSelected;

    private ArrayList<Playlist> allPlaylist;

    public Player() {
        volume = 0;
        playlist = new Playlist();
        mediaPlayer = null;
        mediaPlayerNotSelected = null;
    }

    public void play() {
        mediaPlayer.play();
        mediaPlayer.setVolume(volume / 100);
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void run() {
        if (mediaPlayerNotSelected != null) {
            if (mediaPlayerNotSelected.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerNotSelected.pause();
                mediaPlayerNotSelected = null;
            }
        }
        MediaPlayer.Status mediaStatus = mediaPlayer.getStatus();
        if (mediaStatus == MediaPlayer.Status.PLAYING) {
            pause();
        } else {
            play();
        }
    }

    public void setMediaPlayerNotSelected(MediaPlayer mediaPlayerNotSelected) {
        this.mediaPlayerNotSelected = mediaPlayerNotSelected;
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
    public ArrayList<Playlist> getAllPlaylist() {
        return allPlaylist;
    }

    public void setAllPlaylist(ArrayList<Playlist> allPlaylist) {
        this.allPlaylist = allPlaylist;
    }

}
