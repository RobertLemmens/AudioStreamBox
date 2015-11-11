package Client.Controllers;

import Client.Utility.Connection;
import Client.Views.*;
import Standard.APP_VAR;
import Standard.AbstractController;
import Standard.AbstractView;

import javax.swing.*;
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

    }

    public void initComponents(){
        frame = new MainFrame();
        frame.setSize(400,200);
        frame.setTitle(APP_VAR.APP_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MainPanel();
        controlPanel = new ControlPanel(this);
        controlPanel.initComponents();
        playingPanel = new PlayingPanel(this);
        playlistPanel = new PlaylistPanel(this);


        frame.add(controlPanel);
        frame.setVisible(true);
    }

    public void createConnection(){
        connection = new Connection(this);
    }

    public void transmitRequestToServer(int choice){
        connection.transmitNumber(choice);
    }

    public String retrieveSonglist() {
        String s = "";
        int length;
        try {
            length = connection.listener().readInt();
            byte[] data = new byte[length];
            connection.listener().readFully(data);
            s = new String(data, "UTF-8");
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

    public String returnInvalid() {
        String s = "";
        int length;
        try {
            length = connection.listener().readInt();
            byte[] data = new byte[length];
            connection.listener().readFully(data);
            s = new String(data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
