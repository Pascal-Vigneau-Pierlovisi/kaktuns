package com.example.kaktuns_project_media;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {

    private String playlistName;
    private ArrayList<MediaFile> mediaFilesList;
    public int mediaFileIndex = 0;

    public Playlist(String playlistName, ArrayList<MediaFile> mediaFilesList) {
        this.playlistName = playlistName;
        this.mediaFilesList = mediaFilesList;
    }

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
        this.mediaFilesList = new ArrayList<>();
    }

    public Playlist() {
        this.playlistName = "Playlist \n";
        this.mediaFilesList = new ArrayList<>();
    }

    public void addMediaFile(MediaFile mediaFile) {
        mediaFilesList.add(mediaFile);
        updatePlaylistName(mediaFile);
    }

    public void addAllMediaFiles(List<MediaFile> mediaFiles) {
        mediaFilesList.addAll(mediaFiles);
        updatePlaylistName(mediaFiles);
    }

    public void removeMediaFile(MediaFile mediaFile) {
        mediaFilesList.remove(mediaFile);
        updatePlaylistName();
    }

    public void removeMediaFile(int index) {
        mediaFilesList.remove(index);
        updatePlaylistName();
    }

    public void clear() {
        mediaFilesList.clear();
        mediaFileIndex = 0;
    }

    public String getPlaylistTitle() {
        return playlistName.substring(0, playlistName.indexOf('\n'));
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public ArrayList<MediaFile> getMediaFilesList() {
        return mediaFilesList;
    }

    public void updatePlaylistName() {
        //playlistName = playlistName.substring(playlistName.indexOf(0, '\n'));
        String[] elements = playlistName.split("\n");
        playlistName = elements[0] + "\n";
        for (MediaFile mediaFile: mediaFilesList) {
            playlistName += '\n' + mediaFile.getFileName();
        }
    }

    public void updatePlaylistName(MediaFile mediaFile) {
        playlistName += '\n' + mediaFile.getFileName();
    }

    public void updatePlaylistName(List<MediaFile> mediaFilesList) {
        for (MediaFile mediaFile: mediaFilesList) {
            playlistName += '\n' + mediaFile.getFileName();
        }
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
                allPlaylist = (ArrayList<Playlist>) in.readObject();
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
