package Client.Views;

import Client.Controllers.ClientController;
import Standard.AbstractView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Robert on 08-Nov-15.
 */
public class PlayingPanel extends AbstractView{

    // place left

    private ClientController controller;

    private JPanel imagePanel;
    private JTextField nowPlayingField;
    private BufferedImage image;

    private JLabel picLabel;

    public PlayingPanel(ClientController controller){
        setLayout(new BorderLayout());
        this.controller = controller;
    }

    public void initComponents() {
        imagePanel = new JPanel();


        picLabel = new JLabel(new ImageIcon(this.getClass().getResource("/images/300x300/question.jpg")));


        nowPlayingField = new JTextField();
        nowPlayingField.setText("Now playing..");
        nowPlayingField.setEditable(false);


        add(picLabel, BorderLayout.CENTER);
        add(nowPlayingField, BorderLayout.SOUTH);
    }

    @Override
    public void update() {
        nowPlayingField.setText(controller.getSongNames().get(controller.getIndexOfCurrentSong()));
        nowPlayingField.setScrollOffset(100);
    }

    @Override
    public void check() {

    }
}
