package Server.Utility;

import Server.Controllers.ServerController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Robert on 11-Nov-15.
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private boolean isRunning = true;

    private ServerController controller;

    public ClientHandler(Socket socket, ServerController controller){
        this.socket = socket;
        this.controller = controller;
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
            int x = 0;
            try {
                System.out.println("Trying to read an int...");
                x = in.readInt();
                System.out.println("Recieved something from the client!");
            } catch (IOException e) {
                isRunning = false; // stop verbinding als dit niet lukt
                try {
                    socket.close(); // close socket
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }

            switch(x){
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
                    System.out.println("Starting a thread that will download the songs to the client"); //<-- not needed denk ik nu......
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
