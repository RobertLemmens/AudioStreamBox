package Server.Controllers;

import Server.Utility.Connection;
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
    private Connection connection;

    public ServerController() {
        downloader = new Downloader(this);
        reciever = new Reciever(this);
        connection = new Connection();
    }

    public void startListeningForInput(){
        reciever.start();
        connection.startAcceptingClients();
    }

    public void downloadSong(String url){
        downloader.startDownload(url, WORKING_DIR);
    }



}
