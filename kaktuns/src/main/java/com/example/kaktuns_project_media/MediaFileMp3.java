package com.example.kaktuns_project_media;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * The MediaFileMp3 class represents a mp3 file and provides methods to retrieve metadata from the file.
 */
public class MediaFileMp3 extends MediaFile {

    Metadata metadata;

    public MediaFileMp3(String pathname) throws Exception {
        super(pathname);
        try {
            metadata = getMetadata();
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
    }

    public MediaFileMp3(File file) throws Exception {
        super(file);
        try {
            metadata = getMetadata();
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return an optional containing the album name of the mp3 file,
     * or an empty optional if the album name is not present in the metadata
     */
    public Optional<String> getAlbum() {
        return Optional.ofNullable(metadata.get("xmpDM:album"));
    }

    /**
     * @return an optional containing the artist name of the mp3 file,
     * or an empty optional if the artist name is not present in the metadata
     */
    public Optional<String> getArtist() {
        return Optional.ofNullable(metadata.get("xmpDM:artist"));
    }

    /**
     * @return an optional containing an input stream for the cover art of the mp3 file,
     * or an empty optional if the cover art is not present in the metadata
     */
    public Optional<InputStream> getCoverArt() {
        byte[] coverArt = metadata.get("metadata:cover-art").getBytes();
        return coverArt != null ? Optional.of(new ByteArrayInputStream(coverArt)) : Optional.empty();
    }


    public static boolean isFileMp3(File file) {
        int pointIndex = file.getName().lastIndexOf('.');
        String fileFormat = file.getName().substring(pointIndex);
        return fileFormat.equals(".mp3");
    }

    private Metadata getMetadata() throws IOException, SAXException, TikaException {
        InputStream inputStream = null;
        try {
            inputStream = toURI().toURL().openStream();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Mp3Parser parser = new Mp3Parser();
            parser.parse(inputStream, handler, metadata, new ParseContext());
            return metadata;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
