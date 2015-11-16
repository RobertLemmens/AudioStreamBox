package Client.Views;

import Client.Controllers.ClientController;
import Client.Utility.MP3Player;
import Standard.APP_VAR;
import Standard.AbstractView;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Robert on 08-Nov-15.
 */
public class ControlPanel extends AbstractView implements ActionListener{

    // place south

    private ClientController controller;

    private JButton playPause;
    private JButton sendUrl;
    private JButton next;
    private JButton previous;
    private JButton refresh;
    private JSlider volumeSlider;
    private JSlider songPosition;
    private final Dimension buttonDimensions = new Dimension(30,30);


    ImageIcon playIcon = new ImageIcon(this.getClass().getResource("/images/16/multimedia17.png"));
    ImageIcon nextIcon = new ImageIcon(this.getClass().getResource("/images/16/next.png"));
    ImageIcon previousIcon = new ImageIcon(this.getClass().getResource("/images/16/previous16.png"));
    ImageIcon addIcon = new ImageIcon(this.getClass().getResource("/images/16/add64.png"));
    ImageIcon refreshIcon = new ImageIcon(this.getClass().getResource("/images/16/circulararrow3.png"));
    ImageIcon pauseIcon = new ImageIcon(this.getClass().getResource("/images/16/multimediaoption168.png"));

    public ControlPanel(ClientController controller){
        this.controller = controller;
    }

    public void initComponents(){




        sendUrl = new JButton(addIcon);
        sendUrl.setPreferredSize(buttonDimensions);
        sendUrl.addActionListener(this);

        refresh = new JButton(refreshIcon);
        refresh.setPreferredSize(buttonDimensions);
        refresh.addActionListener(this);

        playPause = new JButton(playIcon); // set image icons
        playPause.setPreferredSize(buttonDimensions);
        playPause.addActionListener(this);

        next = new JButton(nextIcon);
        next.setPreferredSize(buttonDimensions);
        next.addActionListener(this);

        previous = new JButton(previousIcon);
        previous.setPreferredSize(buttonDimensions);
        previous.addActionListener(this);


        volumeSlider = new JSlider();

        songPosition = new JSlider(JSlider.HORIZONTAL);

        add(previous);
        add(playPause);
        add(next);



        add(sendUrl);
        add(refresh);

    }

    public void changeToPlayIcon() {
        playPause.setIcon(playIcon);
    }

    public void changeToPauseIcon() {
        playPause.setIcon(pauseIcon);
    } ///TODO: de update functie dit laten uitzoeken?

    @Override
    public void update() {

    }

    @Override
    public void check() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(controller.amIUpdating()){
            JOptionPane.showMessageDialog(null, "Currently updating your client, please try again in a few seconds", "Updating", JOptionPane.WARNING_MESSAGE);
        } else {
            if(e.getSource().equals(sendUrl)){ /// enter a dialog to send an url to the server
                System.out.println("Send url button pressed, opening window for insertion");

                String urlToSend = JOptionPane.showInputDialog(null, "Enter URL", "Add song dialog", JOptionPane.INFORMATION_MESSAGE);

                if(urlToSend == null ) {
                    System.out.println("No url was provided.");
                } else {
                    controller.transmitRequestToServer(APP_VAR.REQUEST_URL_DOWNLOAD); // transmit number to server to prepare for URL
                    controller.transmitUrlToServer(urlToSend);
                }
            }



            else if(e.getSource().equals(refresh)){
                System.out.println("Requesting songs, and downloading if we need some");
                controller.getMP3filesFromServer();
            }



            else if(e.getSource().equals(playPause)){

                if(controller.isPlaying() == APP_VAR.PLAYING) {
                    System.out.println("Pausing song!");
                    controller.pauseSong();
                } else if(controller.isPlaying() == APP_VAR.PAUSED){
                    System.out.println("Resuming song!");
                    controller.resumeSong();
                } else if(controller.isPlaying() == APP_VAR.NOTSTARTED){
                    controller.startSong();
                }
                else if (controller.isPlaying() == APP_VAR.FINISHED){
                    System.out.println("Playback finished, playing new selected song");
                    controller.startSong();
                }

                else {
                    System.out.println("player is null, starting");
                    controller.startSong();
                }
            }

            else if(e.getSource().equals(next)){
                System.out.println("Skipping to the next song");
                controller.nextSong();
            }

            else if(e.getSource().equals(previous)){
                System.out.println("Previous song");
                controller.previousSong();
            }

        }

    }
}
