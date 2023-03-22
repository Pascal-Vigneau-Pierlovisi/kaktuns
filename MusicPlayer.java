import javazoom.jl.player.Player;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayer {

    private Player player;
    private String status;
    private Thread playThread;
    private File selectedFile;
    private JFileChooser jFileChooser;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private long songDuration, pauseTime;

    public MusicPlayer() {
        playThread = new Thread(runnablePlay);
        jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".mp3", "mp3");
        jFileChooser.setFileFilter(filter);
        status = "not selected";
        pauseTime = -1;
    }

    private File selectFile() {
        int returnValue = jFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jFileChooser.getSelectedFile();
        }
        return null;
    }

    private void printSelectionMessage() {
        switch (status) {
            case "not selected" -> System.out.println(
                    "1) Select song \n" +
                    "2) Exit");
            case "selected" -> System.out.println(
                    "\n" + selectedFile.getName() + " is selected \n" +
                    "1) Select another song \n" +
                    "2) Play \n" +
                    "3) Exit"
            );
            case "playing" -> System.out.println(
                    "\n" + selectedFile.getName() + " is playing \n" +
                    "1) Select another song \n" +
                    "2) Pause \n" +
                    "3) Exit"
            );
            case "on pause" -> System.out.println(
                    "\n" + selectedFile.getName() + " is on pause \n" +
                    "1) Select another song \n" +
                    "2) Resume \n" +
                    "3) Exit"
            );
        }
    }

    Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(selectedFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                if (pauseTime != -1) {
                    songDuration = fileInputStream.available();
                    fileInputStream.skip(songDuration - pauseTime);
                    pauseTime = -1;
                }
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void run() throws IOException {
        label:
        while (true) {
            // Print selection message and get choice
            Scanner myObj = new Scanner(System.in);
            int choice = -1;
            while (choice != 1 && choice != 2 && choice != 3) {
                this.printSelectionMessage();
                choice = myObj.nextInt();
            }

            // Exit program
            switch (choice) {
                case 3:
                    if (player != null) {
                        player.close();
                    }
                    break label;
                // Exit, play, pause, or resume
                case 2:
                    // Exit
                    switch (status) {
                        case "not selected":
                            if (player != null) {
                                player.close();
                            }
                            break label;
                        // Play
                        case "selected":
                        // Resume
                        case "on pause":
                            playThread.start();
                            status = "playing";
                            break;
                        // Pause
                        case "playing":
                            try {
                                pauseTime = fileInputStream.available();
                                player.close();
                                playThread = new Thread(runnablePlay);
                                status = "on pause";
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            break;
                    }
                    break;
                // Select song
                case 1:
                    if (player != null) {
                        player.close();
                        playThread = new Thread(runnablePlay);
                        pauseTime = -1;
                    }

                    while (!status.equals("selected")) {
                        File file = selectFile();
                        if (file != null) {
                            if (file.getName().contains(".mp3")) {
                                selectedFile = file;
                                status = "selected";
                            } else {
                                System.out.println("Invalid file extension");
                            }
                        } else {
                            System.out.println("File is not selected");
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.run();
    }
}
