package Client.Utility;

import Client.Controllers.ClientController;
import Standard.APP_VAR;

/**
 * Created by Robert on 13-Nov-15.
 */
public class MP3Agent implements Runnable {

    private ClientController controller;
    private Thread thread;
    private boolean isRunning = false;

    public MP3Agent(ClientController controller) {
        this.controller = controller;
        System.out.println("Created an mp3 agent!");
    }

    public void start() {
        if(isRunning){
            System.out.println("Thread already running, doing nothing");
        } else {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        isRunning = false;
        thread.yield();
    }

    @Override
    public void run() { //TODO: send heartbeats naar de server zodat de socket niet timeout

        while(isRunning) {
            int status = controller.getPlayerObject().getPlayerStatus();
            System.out.println("Checking for song status");
            if(status == APP_VAR.FINISHED) {
                System.out.println("Playback finished, skipping to next track");
                controller.nextSong(); // werkt!
            } else if (status == APP_VAR.PLAYING){
                System.out.println("Song currently playing");
            } else if(status == APP_VAR.PAUSED) {
                System.out.println("SOng paused");
            } else if(status == APP_VAR.NOTSTARTED) {
                System.out.println("Song not started");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
