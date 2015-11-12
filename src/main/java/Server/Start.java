package Server;

import Server.Controllers.ServerController;

/**
 * Created by rober on 1-11-2015.
 */
public class Start {

    public Start() {
        System.out.println("Starting the server");

        ServerController controller = new ServerController();
        controller.startListeningForInput();

    }

    public static void main(String[] args) {
        Start start = new Start();
    }

}
