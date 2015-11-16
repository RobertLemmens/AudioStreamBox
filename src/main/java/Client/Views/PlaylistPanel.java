package Client.Views;

import Client.Controllers.ClientController;
import Standard.AbstractView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Robert on 08-Nov-15.
 */
public class PlaylistPanel  extends AbstractView{

    // place right
    private ClientController controller;

    private DefaultListModel<String> model;
    private JList<String> playlist;
    private JScrollPane scrollPane;
    // recieve playlist from the server (urls to download, order to place them in)
    public PlaylistPanel(ClientController controller){
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    public void initComponents() {
        model = new DefaultListModel<String>();
        playlist = new JList<String>(model);
        playlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlist.setSelectionForeground(Color.blue);
        scrollPane = new JScrollPane(playlist);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER); // anders resized the scrollpane niet mee (zonder borderlayout center)
        update();// ALS IETS BREAKT 19:45 DAN IS DIT DE OORZAAK
    }

    public String getSelectedItem(){
        return playlist.getSelectedValue();
    }

    public int getSelectedIndex() {
        return playlist.getSelectedIndex();
    }

    public void setColorPlaying(int index){
        playlist.setSelectedIndex(index);
    }

    @Override
    public void update() {
        model.removeAllElements();
        for(String s : controller.getSongNames()) {
            model.addElement(s);
        }
        playlist.validate();
        playlist.repaint();
    }

    @Override
    public void check() {

    }
}
