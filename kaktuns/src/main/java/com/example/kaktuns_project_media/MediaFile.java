package com.example.kaktuns_project_media;

import java.io.File;

/**
 * The MediaFileMp3 class represents a mp3 and mp4 files.
 */
public class MediaFile extends File {

    private final String fileName;
    private final String fileFormat;

    /**
     * Creates a new instance of MediaFile by specifying the file path as a string.
     * @param pathname the file path of the media file.
     * @throws Exception if the file extension is invalid.
     */
    public MediaFile(String pathname) throws Exception {
        super(pathname);
        int pointIndex = this.getName().lastIndexOf('.');
        this.fileName = this.getName().substring(0, pointIndex);
        this.fileFormat = this.getName().substring(pointIndex);
        if (!MediaFile.isMediaFile(this)) {
            throw new Exception("Invalid argument Extension");
        }
    }

    /**
     * Creates a new instance of MediaFile by specifying the file object.
     * @param file the File object representing the media file.
     * @throws Exception if the file extension is invalid.
     */
    public MediaFile(File file) throws Exception {
        super(file.getPath());
        int pointIndex = this.getName().lastIndexOf('.');
        this.fileName = this.getName().substring(0, pointIndex);
        this.fileFormat = this.getName().substring(pointIndex);
        if (!MediaFile.isMediaFile(this)) {
            throw new Exception("Invalid argument Extension");
        }
    }

    public static boolean isMediaFile(File file) {
        int pointIndex = file.getName().lastIndexOf('.');
        if (pointIndex != -1) {
            String fileFormat = file.getName().substring(pointIndex);
            return fileFormat.equals(".mp3") || fileFormat.equals(".mp4");
        }
        return false;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }
}
