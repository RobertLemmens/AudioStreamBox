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

    private DefaultListModel model;
    private JList playlist;
    private JScrollPane scrollPane;
    // recieve playlist from the server (urls to download, order to place them in)
    public PlaylistPanel(ClientController controller){
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    public void initComponents() {
        model = new DefaultListModel();
        update();
        playlist = new JList(model);
        playlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlist.setSelectionForeground(Color.blue);
        scrollPane = new JScrollPane(playlist);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER); // anders resized the scrollpane niet mee (zonder borderlayout center)
    }

    public String getSelectedItem(){
        return (String) playlist.getSelectedValue();
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
    }

    @Override
    public void check() {

    }
}
