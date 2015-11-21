package Client.Utility;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;

/**
 * Created by Robert on 13-Nov-15.
 */
public class MP3AdvancedPlayer {

    private FileInputStream fis;
    private BufferedInputStream bis;
    private Player player;

    private long totalSongLength;
    private long pauseLocation;

    public String filePath;

    public MP3AdvancedPlayer() {

    }

    public void stop() {
        if(player != null) {
            player.close();
        }
    }

    public void play(String path) {
        try {

            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);

            player = new Player(fis);

            totalSongLength = fis.available();

            filePath = path;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start(); // start thread voor het nummer

    }

    public void resume() {
        try {

            fis = new FileInputStream(filePath);
            bis = new BufferedInputStream(fis);


            fis.skip(totalSongLength - pauseLocation);

            player = new Player(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start(); // start thread voor het nummer

    }

    public void pause() {
        try {
            pauseLocation = fis.available();
            player.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
