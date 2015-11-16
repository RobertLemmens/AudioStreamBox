package Standard;

/**
 * Created by Robert on 11-Nov-15.
 */
public class APP_VAR {

    public static final int PORT_NUMER = 8000;
    public static final String APP_TITLE = "Youtube sync";
    public static final String WORKING_DIR = "C:\\Users\\rober\\IdeaProjects\\AudioStreamBox\\src\\downloadsTest"; //TODO: Relative maken
    public static final String CLIENT_WORKING_DIR = System.getProperty("user.home")+"\\"+"AudioStreamBox\\";
    public static final String HOST_IP = "192.168.1.13";
    public final static int NOTSTARTED = 0;
    public final static int PLAYING = 1;
    public final static int PAUSED = 2;
    public final static int FINISHED = 3;

    public static final int REQUEST_URL_DOWNLOAD = 1000;
    public static final int REQUEST_SONG_DOWNLOAD = 100;
    public static final int REQUEST_HEARTBEAT = 3;
    public static final int REQUEST_NOTHING = 999;

}
