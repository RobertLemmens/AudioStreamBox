package Server;

import Server.Controllers.ServerController;
import Server.Utility.Downloader;
import Server.Utility.Reciever;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
