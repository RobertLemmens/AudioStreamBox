package Client.Utility;

import Client.Controllers.ClientController;
import Standard.APP_VAR;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Robert on 11-Nov-15.
 */
public class Connection{

    private Socket socket;
    private String host_ip = "192.168.178.6";

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
    }

    public DataInputStream listener(){
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Something went wrong while listening for data");
            e.printStackTrace();
        }

        return in;
    }

    public void transmitNumber(int number) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Something went wrong creating the outputstream");
            e.printStackTrace();
        }

        try {
            out.writeInt(number);
            out.flush();
            System.out.println("Transmitted number to server!");
        } catch (IOException e) {
            System.out.println("Something went wrong while transmitting the number");
            e.printStackTrace();
        }
    }


}
