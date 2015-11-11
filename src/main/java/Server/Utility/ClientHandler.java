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
            System.out.println("Server listening for input....");
            int x = 0;
            try {
                x = in.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch(x){
                case 0: {
                    // return songlist
                    try {
                        System.out.println(socket.getInetAddress().getHostName() + " Requested songlist");
                        out.writeUTF("Hallo, jij bent: " + socket.getInetAddress().getHostName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 1: {
                    // return current song
                    try {
                        System.out.println(socket.getInetAddress().getHostName() + " Requested current song");
                        out.writeInt(3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 2: {
                    // return current time in song
                    try {
                        System.out.println(socket.getInetAddress().getHostName() + " Requested current playtime");
                        out.writeDouble(2.3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }
}
