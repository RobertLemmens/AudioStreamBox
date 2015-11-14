package Client.Utility;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;

/**
 * Created by Robert on 13-Nov-15.
 */
public class MP3Player {

    private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;

    private Player player;

    private final Object playerLock = new Object();

    private int playerStatus = NOTSTARTED;

    public MP3Player(final InputStream inputStream) {
        try {
            this.player = new Player(inputStream);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }



    public int getPlayerStatus() {
        return playerStatus;
    }


    // start met spelen (herstart als gepauzeerd is)
    public void play() {
        synchronized (playerLock){
            switch(playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;
                    t.start();
                    break;
                case PAUSED:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }


    // pauzeert playback, returned true als nieuwe state paused is
    public boolean pause() {
        synchronized (playerLock){
            if(playerStatus == PLAYING){
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }


    /// herstart playback. Als er niets speelt gebeurt er niets
    public boolean resume(){
        synchronized (playerLock){
            if(playerStatus == PAUSED){
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    //// stopt playback, als er niets speelt gebeurt er niets
    public void stop(){
        synchronized (playerLock){
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }



    private void playInternal(){
        while (playerStatus != FINISHED) {
            try {
                if (!player.play(1)) {
                    break;
                }
            } catch (final JavaLayerException e) {
                break;
            }
            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
        close();
    }

    // sluit player, wat er ook gebeurt,.
    public void close() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
        }
        try {
            player.close();
        } catch (final Exception e) {
            // ignore, app sluit toch af
        }
    }

}
