package Client.Controllers;

import Client.Utility.Connection;
import Client.Views.*;
import Standard.AbstractController;
import Standard.AbstractView;

import java.io.IOException;

/**
 * Created by rober on 1-11-2015.
 */
public class ClientController extends AbstractController {

    private MainFrame frame;
    private MainPanel panel;
    private ControlPanel controlPanel;
    private PlayingPanel playingPanel;
    private PlaylistPanel playlistPanel;
    private Connection connection;

    public ClientController() {
        connection = new Connection(this);
    }

    public void initComponents(){
        frame = new MainFrame();
        panel = new MainPanel();
        controlPanel = new ControlPanel(this);
        playingPanel = new PlayingPanel(this);
        playlistPanel = new PlaylistPanel(this);
    }

    public void transmitRequestToServer(int choice){
        connection.transmitNumber(choice);
    }

    public String retrieveSonglist() {
        String s = "";
        try {
            s = connection.listener().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public int returnCurrentSong(){
        int s = 0;
        try {
            s = connection.listener().readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public double retrieveTimeInSong(){
        double s = 0.0;
        try {
            s = connection.listener().readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
