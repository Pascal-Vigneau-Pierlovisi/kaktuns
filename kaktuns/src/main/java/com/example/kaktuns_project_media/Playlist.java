package com.example.kaktuns_project_media;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {

    private String playlistTitle;
    private ArrayList<MediaFile> mediaFilesList;
    public int mediaFileIndex = 0;

    public Playlist(String playlistTitle, ArrayList<MediaFile> mediaFilesList) {
        this.playlistTitle = playlistTitle;
        this.mediaFilesList = mediaFilesList;

    }

    public Playlist(String playlistTitle) {
        this.playlistTitle = playlistTitle;
        this.mediaFilesList = new ArrayList<>();
    }

    public Playlist() {
        this.playlistTitle ="Playlist \n";
        this.mediaFilesList = new ArrayList<>();
    }

    public boolean isEmpty() {
        return mediaFilesList.isEmpty();
    }

    public String getPlaylistTitle() {return this.playlistTitle;}

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

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

    public static void serialize(ArrayList<Playlist> allPlaylist) {
        try {
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
                allPlaylist = (ArrayList<Playlist>) in.readObject();
            } catch (EOFException e) {
                System.out.println(e);
            } catch (ClassCastException e) {
                allPlaylist = new ArrayList<>();
                allPlaylist.add((Playlist) in.readObject());
            }
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return allPlaylist == null ? new ArrayList<Playlist>() : allPlaylist;
    }
}
