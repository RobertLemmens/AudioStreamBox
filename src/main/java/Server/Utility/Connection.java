package Server.Utility;

import Standard.APP_VAR;
import com.sun.deploy.util.SessionState;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Robert on 11-Nov-15.
 */
public class Connection implements Runnable{

    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets; // list with all client sockets;
    private ArrayList<String> clientList;
    private DataOutputStream out;
    private DataInputStream in;
    private Thread thread;

    public static int Clients = 0;

    public Connection() {
        try {
            serverSocket = new ServerSocket(APP_VAR.PORT_NUMER);
            sockets = new ArrayList<>();
            clientList = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Something wrong with creating the socket");
            e.printStackTrace();
        }
    }

    public void startAcceptingClients(){
        thread = new Thread(this);
        thread.start();
    }

    private void Accept(){ // creates a socket for client and adds socket to socket list
        try {
            Socket socket = serverSocket.accept();
            InetAddress inetAddress = socket.getInetAddress();
            clientList.add(inetAddress.getHostName() + "---" + inetAddress.getHostAddress());
            sockets.add(socket);
            ClientHandler task = new ClientHandler(socket);

            new Thread(task).start(); // start thread voor deze client

            Clients++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("Accepting clients");
            Accept();
        }
    }
}
