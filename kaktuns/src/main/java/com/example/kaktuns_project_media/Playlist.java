package com.example.kaktuns_project_media;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {



    private String playTitle;
    private ArrayList<MediaFile> mediaFilesList;
    public int mediaFileIndex = 0;


    public Playlist(String playTitle, ArrayList<MediaFile> mediaFilesList) {
        this.playTitle = playTitle;
        this.mediaFilesList = mediaFilesList;

    }

    public Playlist(String playTitle) {
        this.playTitle=playTitle;
        this.mediaFilesList = new ArrayList<>();
    }

    public Playlist() {
        this.playTitle="Playlist \n";
        this.mediaFilesList = new ArrayList<>();

    }
    public String getPlayTitle() {return this.playTitle;}
    public void setMediaFileIndex(int index){
        this.mediaFileIndex=index;
    }

    public void addMediaFile(MediaFile mediaFile) {
        mediaFilesList.add(mediaFile);

    }

    public void addAllMediaFiles(List<MediaFile> mediaFiles) {
        mediaFilesList.addAll(mediaFiles);

    }

    public void removeMediaFile(MediaFile mediaFile) {
        mediaFilesList.remove(mediaFile);

    }

    public void removeMediaFile(int index) {
        mediaFilesList.remove(index);

    }

    public void clear() {
        mediaFilesList.clear();
        mediaFileIndex = 0;
    }



    public ArrayList<MediaFile> getMediaFilesList() {
        return mediaFilesList;
    }





    public void serialize() {
        try {
            ArrayList<Playlist> allPlaylist = deserialize();
            allPlaylist.add(this);
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\kaktuns\\playlists\\playlists.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(allPlaylist);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    public static ArrayList<Playlist> deserialize() {
        ArrayList<Playlist> allPlaylist = null;
        try {
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "\\kaktuns\\playlists\\playlists.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            try {
                try {
                    allPlaylist = (ArrayList<Playlist>) in.readObject();
                } catch (ClassCastException e) {
                    allPlaylist = new ArrayList<>();
                    allPlaylist.add((Playlist) in.readObject());
                }
            } catch (EOFException e) {
                System.out.println(e);
            }
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return allPlaylist == null ? new ArrayList<Playlist>() : allPlaylist;
    }


}
