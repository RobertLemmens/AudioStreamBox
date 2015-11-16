package Client.Controllers;

import Client.Utility.Connection;
import Client.Utility.MP3Agent;
import Client.Utility.MP3Player;
import Client.Utility.MP3Retriever;
import Client.Views.*;
import Standard.APP_VAR;
import Standard.AbstractController;
import Standard.AbstractView;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.util.ArrayList;

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
    private MP3Retriever mp3Retriever;
    private boolean updating = false;
    private MP3Player player;
    private int nowPlayingIndex;
    private MP3Agent songAgent;

    private MP3PlaybackController mp3PlaybackController;

    private ArrayList<String> songNames;


    public ClientController() {
        songNames = new ArrayList<>();
        mp3PlaybackController = new MP3PlaybackController(this);

        File theDir = new File(APP_VAR.CLIENT_WORKING_DIR);

// if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + APP_VAR.CLIENT_WORKING_DIR);
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                //handle it
            }
            if(result) {
                System.out.println("DIR created");
            }
        }

    }

    public void addSong(String s) {
        songNames.add(s);

        playlistPanel.update();
    }

    public ArrayList<String> getSongNames() {
        return songNames;
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
        playingPanel.initComponents();

        playlistPanel = new PlaylistPanel(this);
        playlistPanel.initComponents();

        mp3Retriever = new MP3Retriever(this);

        panel.add(playingPanel, BorderLayout.WEST);
        panel.add(playlistPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        songAgent = new MP3Agent(this);
    }

    public boolean amIUpdating(){
        return updating;
    }

    public void setUpdating(boolean upToDate){
        this.updating = upToDate;
    }

    public void createConnection(){
        connection = new Connection(this);
        getMP3filesFromServer();
    }

    public void getMP3filesFromServer() {
        mp3Retriever.getMP3Files();
    }

    public void transmitRequestToServer(int choice){
        connection.transmitNumber(choice);
    }

    public void transmitUrlToServer(String url){
        connection.transmitUrl(url);
    }

    public int retrieveAmountOfSongs() {
        int s = 0;
        try { //TODO waar de f komt die 15 vandaan, de server stuurt echt maar 0;
            s = connection.listener().readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public DataInputStream connectionListener() {
        return connection.listener();
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


    public MP3PlaybackController playbackAction() {
        return mp3PlaybackController;
    }

    /////////// player controls //////////////
    public int getIndexOfCurrentSong() {
        return nowPlayingIndex;
    }

    public void startSong(){
        nowPlayingIndex = playlistPanel.getSelectedIndex();
        if(player !=null){
            player.pause();
         //   player = null;
        }

        try {
            FileInputStream input = new FileInputStream(APP_VAR.CLIENT_WORKING_DIR+playlistPanel.getSelectedItem());
            player = new MP3Player(input);

            player.play();
            controlPanel.changeToPauseIcon();
            songAgent.start();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        playingPanel.update();
        playlistPanel.setColorPlaying(nowPlayingIndex);
    }

    public void startSong(String song){

        if(player !=null){
            player.pause();// momenteel de enige manier. anders kan de thread een nummer skippen omdat close en stop de value van playback op FINISHED zetten. Nog naar kijktn
          //  player = null;
        }

        try {
            FileInputStream input = new FileInputStream(APP_VAR.CLIENT_WORKING_DIR+song);
            player = new MP3Player(input);

            player.play();
            controlPanel.changeToPauseIcon();
            songAgent.start();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        playingPanel.update();
    }

    public int isPlaying(){
        if(player != null)
            return player.getPlayerStatus();
        else{
            return 99;
        }
    }

    public MP3Player getPlayerObject() {
        return player;
    }

    public void resumeSong() {
        player.resume();
        controlPanel.changeToPauseIcon();
    }

    public void nextSong(){
        System.out.println("SELECTED INDEX VOOR: " + nowPlayingIndex);
        int s = nowPlayingIndex;
        String songToPlay = "";
        if(s+1 == songNames.size()){
            songToPlay = songNames.get(0);
            nowPlayingIndex = 0;
        } else {
            songToPlay = songNames.get(s+1);
            nowPlayingIndex++;
        }
        startSong(songToPlay);
        playingPanel.update();
        System.out.println("SELECTED INDEX NU: " + nowPlayingIndex);
        playlistPanel.setColorPlaying(nowPlayingIndex);
    }

    public void previousSong() {
        System.out.println("SELECTED INDEX VOOR: " + nowPlayingIndex);
        int s = nowPlayingIndex;
        String songToPlay = "";
        if(s == 0) {
            songToPlay = songNames.get(songNames.size()-1);
            nowPlayingIndex = songNames.size()-1;
        } else {
            songToPlay = songNames.get(s-1);
            nowPlayingIndex--;
        }
        startSong(songToPlay);
        playingPanel.update();
        System.out.println("SELECTED INDEX NU: " + nowPlayingIndex);
        playlistPanel.setColorPlaying(nowPlayingIndex);
    }

    public void pauseSong() {
        player.pause();
        controlPanel.changeToPlayIcon();
        playingPanel.update();
    }



}
