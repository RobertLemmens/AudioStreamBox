package Server.Utility;

import java.util.Scanner;

import Server.Controllers.ServerController;
import Server.Utility.Downloader;

/**
 * Created by Robert on 3-11-2015.
 */
public class Reciever implements Runnable {

    private Thread draad;
    private ServerController controller;
    private boolean isRunning = false;
    private Scanner input;

    public Reciever(ServerController controller){
        this.controller = controller;
        input = new Scanner(System.in);
    }

    public void start(){
        draad = new Thread(this);
        draad.start();
    }

    public void stop() {
        isRunning = false;
        try {
            draad.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        draad = null;

    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning){
            String text = input.next();
            controller.downloadSong(text); //TODO: song meta verzamelen, adden in playlist. Logica verzinnen om 1 voor 1 naar ALLE clients die connecten te senden.
            try {                               //TODO: de downloader een aparte thread maken zodat je kan blijven adden, processen parallel laten lopen
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // recieves songs from clients en client information

}
