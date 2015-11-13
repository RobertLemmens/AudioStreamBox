package Client.Utility;

import Client.Controllers.ClientController;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Robert on 13-Nov-15.
 */
public class MP3Retriever {

    private ClientController controller;
    private int currentAmountOfMp3s = 0;

    public MP3Retriever(ClientController controller) {
        this.controller = controller;
        // transmit 100 om de server op de hoogte te brengen dat je de songs wilt, returned de hoeveelheid nummers die er zijn.


    }

    public void getMP3Files() {
        controller.transmitRequestToServer(100);
        int amountOfSongs = controller.retrieveAmountOfSongs();

        if(currentAmountOfMp3s == amountOfSongs){
            System.out.println("We are up to date! Letting the server know we need nothing");
            controller.transmitRequestToServer(999);
        } else {
            System.out.println("The server responded, we have " + (amountOfSongs - currentAmountOfMp3s) + " songs to download");
            System.out.println("Starting the loop, server should be ready for requests");

            for(int i = currentAmountOfMp3s; i < amountOfSongs; i++) { // loop en get de songs.
                controller.transmitRequestToServer(i);
                int length;
                try {
                    length = controller.connectionListener().readInt();
                    byte[] data = new byte[length];
                    controller.connectionListener().readFully(data);
                    System.out.println("Byte length ontvangen: " + data.length);
                    FileOutputStream fos = new FileOutputStream("C:\\"+i+".mp3");
                    fos.write(data);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            currentAmountOfMp3s = amountOfSongs;
            System.out.println("Retrieved all files from the server!");
        }


    }

}
