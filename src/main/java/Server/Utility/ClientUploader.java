package Server.Utility;

import Server.Controllers.ServerController;
import Standard.APP_VAR;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Robert on 12-Nov-15.
 */
public class ClientUploader implements Runnable {

    private boolean isRunning = true;
    private ServerController controller;
    private ArrayList<File> files = new ArrayList<>();
    private Socket socket;
    public ClientUploader(ServerController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
    }

    public void fileHandler() {
        File folder = new File(APP_VAR.WORKING_DIR);
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()) {
                System.out.println("File: " + listOfFiles[i].getName());
            } else if(listOfFiles[i].isDirectory()) {
                System.out.println("Directory: " + listOfFiles[i].getName());
            }
        }

    }

    @Override
    public void run() { //TODO: client moet zelf periodiek de songlist opvragen. Server respond alleen.
        while(isRunning) {
            // get files
            fileHandler();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
