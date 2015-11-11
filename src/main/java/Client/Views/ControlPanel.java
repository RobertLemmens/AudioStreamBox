package Client.Views;

import Client.Controllers.ClientController;
import Standard.AbstractView;

import javax.swing.*;

/**
 * Created by Robert on 08-Nov-15.
 */
public class ControlPanel extends AbstractView {

    // place south

    private ClientController controller;

    private JButton playPause;
    private JButton next;
    private JButton previous;
    private JSlider volumeSlider;


    public ControlPanel(ClientController controller){

    }

    public void initComponents(){
        playPause = new JButton(); // set image icons
        next = new JButton();
        previous = new JButton();
        volumeSlider = new JSlider();
    }

    @Override
    public void update() {

    }

    @Override
    public void check() {

    }
}
