package Client.Controllers;

import Client.Utility.MP3Agent;
import Client.Utility.MP3Player;

/**
 * Created by Robert on 13-Nov-15.
 */
public class MP3PlaybackController { /// move alle mp3 related controller zooi naar deze controller;


    private ClientController controller;

    private MP3Player player;
    private int nowPlayingIndex;
    private MP3Agent songAgent;

    public MP3PlaybackController(ClientController controller) {
        this.controller = controller;
    }

    // alleen de mp3 agent thread mag hier eigenlijk bij. De skip knoppen op de contolpanel moeten een "vote" inschakelen naar de server.
    public void startSong() {

    }

    public void nextSong() {

    }

    public void previousSong() {

    }

    public void pauseSong() {

    }

    public void stopPlaying() {

    }


}
