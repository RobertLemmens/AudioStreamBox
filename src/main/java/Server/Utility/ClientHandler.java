package Server.Utility;

import Server.Controllers.ServerController;
import Standard.APP_VAR;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Robert on 11-Nov-15.
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private boolean isRunning = true;

    private ServerController controller;

    private ArrayList<File> fileList;
    private ArrayList<String> filePathlist;
    private HashSet<String> fileHelperList;

    public ClientHandler(Socket socket, ServerController controller){
        this.socket = socket;
        this.controller = controller;
        fileList = new ArrayList<>();
        fileHelperList = new HashSet<>();
        filePathlist = new ArrayList<>();
    }

    private DataInputStream in;
    private DataOutputStream out;

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(isRunning){ // lees en schrijf data
            System.out.println("Server listening for input from " + socket.getInetAddress().getHostName());



            int x = 0; // ontvang een client request.
            try {
                System.out.println("Trying to read an int...");
                x = in.readInt();
                System.out.println("Recieved something from the client!");
            } catch (IOException e) {
                isRunning = false; // stop verbinding als dit niet lukt
                try {
                    socket.close(); // close socket
                    Thread.yield();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }




            switch(x){ // kijk wat de client gerequest heeft en antwoord appropriatly
                case 0: {
                    // return songlist
                    try {
                        System.out.println(socket.getInetAddress().getHostName() + " Requested songlist");
                        String s = "Hallo, jij bent: " + socket.getInetAddress().getHostName();
                        byte[] data = s.getBytes("UTF-8");
                        out.writeInt(data.length);
                        out.write(data);
                    } catch (IOException e) {
                        isRunning = false; // stop verbinding als dit niet lukt
                        try {
                            socket.close(); // close socket
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    break;
                }
                case 1: {
                    // return current song
                    try {
                        System.out.println(socket.getInetAddress().getHostName() + " Requested current song");
                        out.writeInt(3);
                    } catch (IOException e) {
                        isRunning = false; // stop verbinding als dit niet lukt
                        try {
                            socket.close(); // close socket
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    // return current time in song
                    try {
                        System.out.println(socket.getInetAddress().getHostName() + " Requested current playtime");
                        out.writeDouble(2.3);
                    } catch (IOException e) {
                        isRunning = false; // stop verbinding als dit niet lukt
                        try {
                            socket.close(); // close socket
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    break;
                }
                case 3: {// heartbeat functie. Return iets simpels

                    break;
                }
                case 1000: { // als client 1000 stuurt kunnen we hierna een url verwachten. lees deze in
                    String s = "";
                    int length;
                    try {
                        length = in.readInt();
                        byte[] data = new byte[length];
                        in.readFully(data);
                        s = new String(data, "UTF-8");

                         // voeg deze url toe aan de arraylist met alle songs die door de downloader bekeken worden.
                        System.out.println("Adding " + s + " to the queue");
                        controller.addSong(s);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 100: {
                    System.out.println("request download van de songs"); //<-- deze is goed.

                    // check voor files in de folder // TEST CODE HIERONDER

                    File folder = new File(APP_VAR.WORKING_DIR);
                    File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".mp3");
                        }
                    });



                    if(filePathlist.size() == 0) { //TODO: FILTER MP3
                        System.out.println("First run for this client, adding all songs");
                        for(int i = 0; i < listOfFiles.length; i++) {
                            if(listOfFiles[i].getName().equals("youtube-dl.exe")) {
                                System.out.println("YOUTUBE DL GEVONDEN");
                            } else {
                                fileHelperList.add(listOfFiles[i].getName());
                                filePathlist.add(listOfFiles[i].getName());
                                fileList.add(listOfFiles[i]);
                            }
                        }
                    } else {
                        for(int i = 0; i < listOfFiles.length; i++){
                            if(listOfFiles[i].getName().equals("youtube-dl.exe")) {
                                System.out.println("YOUTUBE DL GEVONDEN");
                            } else {
                                if(fileHelperList.contains(listOfFiles[i].getName())) {
                                    System.out.println("Duplicate gevonden!!");
                                } else {
                                    fileHelperList.add(listOfFiles[i].getName());
                                    fileList.add(listOfFiles[i]);
                                    filePathlist.add(listOfFiles[i].getName());
                                }
                            }
                        }
                    }



                    try {
                        out.writeInt(fileList.size());
                        System.out.println("Sended the client the amount of numbers in the playlist: " + fileList.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    for(int i = 0; i < fileList.size(); i++) { // we verwachten dat de client nu songs gaat opvragen om te downloaden
                        System.out.println("Ik ga nu: " + fileList.size() + " keer loopen");
                        int s;
                        try {
                            System.out.println("IK BEN AAN HET LEZEN WELKE FILE ER NODIG IS!");
                            s = in.readInt(); // lees welke file nodig is.
                            System.out.println("IK HEB DE VOLGENDE WAARDE ONTVANGEN: " + s);
                            if(s == 999) {
                                System.out.println("The client told us he is up to date and needs no songs");
                                break;
                            } else {

                                System.out.println("Informing the client of the filename");
                                String songName = fileList.get(s).getName();
                                byte[] data2 = songName.getBytes("UTF-8");
                                out.writeInt(data2.length);
                                out.write(data2);

                                System.out.println("Sending the client the file now");
                                RandomAccessFile f = new RandomAccessFile(fileList.get(s).getAbsolutePath(), "r"); // r staat voor READ deze file. je kan ook W meegeven voor write.
                                byte[] data = new byte[(int) f.length()];
                                f.read(data); // creeer de byte array van de file.
                                System.out.println("Created a data byte with methode 2, file: " + fileList.get(s).getAbsolutePath());
                                System.out.println("byte information; Length: " + data.length + " Sending file now");
                                out.writeInt(data.length);
                                out.write(data);


                                System.out.println("Done sending the mp3 name and information to the client, exiting this block now");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    ///////// METHOD 2 ////////////////
                    /*
                    try {
                        RandomAccessFile f = new RandomAccessFile(listOfFiles[1].getAbsolutePath(), "r"); // r staat voor READ deze file. je kan ook W meegeven voor write.
                        byte[] data = new byte[(int)f.length()];
                        f.read(data); // creeer de byte array van de file.
                        System.out.println("Created a data byte with methode 2, file: " + listOfFiles[0].getAbsolutePath());
                        System.out.println("byte information; Length: " + data.length );
                        out.writeInt(data.length);
                        out.write(data);



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                    /////////// METHOD 2 WERKT;


                    // EINDE TEST CODE

                    break;
                }
                default: {
                    System.out.println("Invalid request");
                    String s = "Invalid request";
                    try {
                        byte[] data = s.getBytes("UTF-8");
                        out.writeInt(data.length);
                        out.write(data);
                    } catch (IOException e) {
                        isRunning = false; // stop verbinding als dit niet lukt
                        try {
                            socket.close(); // close socket
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    break;

                }
            }
        }


    }
}
