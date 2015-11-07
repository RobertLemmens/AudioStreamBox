package Server.Controllers;

import Server.Utility.Downloader;
import Server.Utility.Reciever;
import Standard.AbstractController;

/**
 * Created by rober on 1-11-2015.
 */
public class ServerController extends AbstractController{

    public static final String WORKING_DIR = "C:\\Users\\Robert\\IdeaProjects\\AudioStreamBox\\src\\downloadsTest";

    private Downloader downloader;
    private Reciever reciever;

    public ServerController() {
        downloader = new Downloader(this);
        reciever = new Reciever(this);
    }

    public void startListeningForInput(){
        reciever.start();
    }

    public void downloadSong(String url){
        downloader.startDownload(url, WORKING_DIR);
    }



}
