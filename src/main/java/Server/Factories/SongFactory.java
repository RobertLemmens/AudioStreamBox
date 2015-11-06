package Server.Factories;

import Server.Models.Song;

import java.util.ArrayList;

/**
 * Created by Robert on 1-11-2015.
 */
public class SongFactory {

    // class could be seen as a the " playlist". This is the up to date list of all the songs that are added by the clients
    private ArrayList<Song> songs;

    public SongFactory() {
        songs = new ArrayList<Song>();
    }

    public void addSong(Song song){
        songs.add(song);
    }

    public void removeSong(int index) {
        songs.remove(index);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public Song getSong(int index){
        return songs.get(index);
    }

}
