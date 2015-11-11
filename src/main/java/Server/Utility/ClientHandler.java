package Server.Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Robert on 11-Nov-15.
 */
public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
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

        while(true){ // lees en schrijf data
            System.out.println("Server listening for input from " + socket.getInetAddress().getHostName());
            int x = 0;
            try {
                System.out.println("Trying to read an int...");
                x = in.readInt();
                System.out.println("Recieved something from the client!");
            } catch (IOException e) {
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
                        e.printStackTrace();
                    }
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
                        e.printStackTrace();
                    }

                }
            }
        }


    }
}
