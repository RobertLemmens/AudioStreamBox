package Standard;

/**
 * Created by Robert on 11-Nov-15.
 */
public class APP_VAR {

    public static final int PORT_NUMER = 8000;
    public static final String APP_TITLE = "Youtube sync";
    public static final String WORKING_DIR = "C:\\Users\\Robert\\IdeaProjects\\AudioStreamBox\\src\\downloadsTest";
    public static final String CLIENT_WORKING_DIR = System.getProperty("user.home")+"\\"+"AudioStreamBox\\";
    public final static int NOTSTARTED = 0;
    public final static int PLAYING = 1;
    public final static int PAUSED = 2;
    public final static int FINISHED = 3;

    public APP_VAR(){
        System.out.println("Working dir: " + CLIENT_WORKING_DIR);
    }

}
