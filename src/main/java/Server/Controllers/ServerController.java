package Server.Controllers;

import Server.Utility.Connection;
import Server.Utility.Downloader;
import Standard.AbstractController;

/**
 * Created by rober on 1-11-2015.
 */
public class ServerController extends AbstractController{



    private Downloader downloader;
    private Connection connection;

    public ServerController() {
        downloader = new Downloader(this);
        connection = new Connection(this);
    }

    public void startListeningForInput(){
        connection.startAcceptingClients();
    }

    public void addSong(String url){
        downloader.addUrl(url);
    }



}
