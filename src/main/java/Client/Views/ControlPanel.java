package Client.Views;

import Client.Controllers.ClientController;
import Standard.AbstractView;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Robert on 08-Nov-15.
 */
public class ControlPanel extends AbstractView implements ActionListener{

    // place south

    private ClientController controller;

    private JButton playPause;
    private JButton next;
    private JButton previous;
    private JSlider volumeSlider;

    private JTextArea testArea;


    public ControlPanel(ClientController controller){
        this.controller = controller;
    }

    public void initComponents(){

        testArea = new JTextArea();
        testArea.setPreferredSize(new Dimension(200,100));

        playPause = new JButton("test"); // set image icons
        playPause.addActionListener(this);
        next = new JButton("Next");
        previous = new JButton("Previous");
        volumeSlider = new JSlider();

        add(testArea);
        add(playPause);
        add(next);
        add(previous);
    }

    @Override
    public void update() {

    }

    @Override
    public void check() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int s = 0;
        if(e.getSource().equals(playPause)){
            System.out.println("test button pressed");
            s = Integer.parseInt(testArea.getText());
            controller.transmitRequestToServer(s);
        }
        if(s == 0) {
            testArea.setText(controller.retrieveSonglist());
        } else if( s == 1) {
           testArea.setText("" + controller.returnCurrentSong());
        } else if( s == 2) {
            testArea.setText("" + controller.retrieveTimeInSong());
        } else {
            testArea.setText("" + controller.returnInvalid());
        }
    }
}
