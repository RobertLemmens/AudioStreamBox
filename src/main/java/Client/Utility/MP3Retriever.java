package Client.Utility;

import Client.Controllers.ClientController;
import Standard.APP_VAR;

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
        controller.transmitRequestToServer(APP_VAR.REQUEST_SONG_DOWNLOAD);
        int amountOfSongs = controller.retrieveAmountOfSongs();
        System.out.println("Server responded it has " + amountOfSongs + " in its playlist");
        boolean sendExitCommand = false;

        if(currentAmountOfMp3s < amountOfSongs && currentAmountOfMp3s != 0) // de server blijft luisteren naar een file om op te halen als de loops oneven zijn, de server loopt standaard de grootte van de filelist omdat die niet kan weten hoeveel songs de client nodig heeft
            sendExitCommand = true;                                           // daarom zorgen we ervoor dat als de client songs opvraagt met een loop die niet gelijk is aan de server loop, we een end signaal sturen(999). Als de server 999 ontvangt in dat blok stopt hij.
                                                                                // de client en server hebben alleen in het begin een even loop, en als de song counts gelijk zijn dan gaat de client niet eens de loop in. Dus alle andere gevallen is deze true;
        if(currentAmountOfMp3s == amountOfSongs){
            System.out.println("We are up to date! Letting the server know we need nothing");
            controller.transmitRequestToServer(APP_VAR.REQUEST_NOTHING);
            controller.setUpdating(false);
        } else {
            System.out.println("The server responded with " +  amountOfSongs +", we have " + (amountOfSongs - currentAmountOfMp3s) + " songs to download");
            System.out.println("Starting the loop, server should be ready for requests"); //TODO: Kijken of de server ECHT klaar is voor requests
            controller.setUpdating(true);                                                   // TODO: een socketmodel ontwerpen zodat het nooit kapot gaat. (ook die dubbel loop is slecht.
                                                                                                //TODO; Dubbel loop vervangen voor 1 loop in de client side, die blijft requesten.
            for(int i = currentAmountOfMp3s; i < amountOfSongs; i++) { // loop en get de songs.
                System.out.println("Ik ga nu: " + (amountOfSongs-currentAmountOfMp3s) +" keer loopen");
                controller.transmitRequestToServer(i);
                /////// get the file name
                int nameLength;
                String mp3Name="";
                try {
                    nameLength = controller.connectionListener().readInt();
                    byte[] nameData = new byte[nameLength];
                    System.out.println("Name length ontvangen: " + nameData.length);
                    controller.connectionListener().readFully(nameData);
                    mp3Name = new String(nameData, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ////// get the mp3 file
                int mp3Length;
                try {
                    mp3Length = controller.connectionListener().readInt();
                    byte[] mp3Data = new byte[mp3Length];
                    controller.connectionListener().readFully(mp3Data);
                    System.out.println("Byte length ontvangen: " + mp3Data.length);
                    FileOutputStream fos = new FileOutputStream(APP_VAR.CLIENT_WORKING_DIR+mp3Name);
                    fos.write(mp3Data);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controller.addSong(mp3Name); // voeg de mp3 naam toe zodat we deze in de view kunnen adden
            }
            if(sendExitCommand){
                controller.transmitRequestToServer(APP_VAR.REQUEST_NOTHING); // stuur het exit command als nodig is.
            }
            currentAmountOfMp3s = amountOfSongs;
            controller.setUpdating(false);
            System.out.println("Retrieved all files from the server!");
        }
    }
}
