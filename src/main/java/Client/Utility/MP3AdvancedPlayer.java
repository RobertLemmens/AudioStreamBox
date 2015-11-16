package Client.Utility;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;

/**
 * Created by Robert on 13-Nov-15.
 */
public class MP3AdvancedPlayer {

    private AdvancedPlayer player;

    public MP3AdvancedPlayer(final InputStream inputStream) {
        try {
            player = new AdvancedPlayer(inputStream);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
