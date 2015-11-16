package Client.Utility;

import Client.Controllers.ClientController;
import Standard.APP_VAR;

import java.io.*;
import java.net.Socket;

/**
 * Created by Robert on 11-Nov-15.
 */
public class Connection{

    private Socket socket;
    private String host_ip = APP_VAR.HOST_IP;

    DataInputStream in;
    DataOutputStream out;

    private ClientController controller;

    public Connection(ClientController controller){
        this.controller = controller;
        try {
            socket = new Socket(host_ip, APP_VAR.PORT_NUMER );
        } catch (IOException e) {
            System.out.println("Something went wrong creating the socket. Is the host available?");
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            // doe dit zodra de applicatie afsuit //TODO: CO
            public void run() {
                try {
                    System.out.println("Socket closing");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })); // close socket zodra client afsluit

    }


    public DataInputStream listener(){ // returned het inputkanaal. hierop komt alles wat de server stuurt binnen
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Something went wrong while listening for data");
            e.printStackTrace();
        }

        return in;
    }

    public void transmitNumber(int number) { // wordt gebruikt om de server dingen te requesten.
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Something went wrong creating the outputstream");
            e.printStackTrace();
        }

        try {
            out.writeInt(number);
            out.flush();
            System.out.println("Transmitted number " +  number  + " to server!");
        } catch (IOException e) {
            System.out.println("Something went wrong while transmitting the number");
            e.printStackTrace();
        }
    }

    public void transmitUrl(String url) {
        byte[] data = new byte[0];
        try {
            data = url.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(data.length);
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
