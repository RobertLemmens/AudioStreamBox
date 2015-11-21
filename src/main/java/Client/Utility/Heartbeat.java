package Client.Utility;

import Client.Controllers.ClientController;
import Standard.APP_VAR;

import java.io.IOException;

/**
 * Created by Robert on 16-11-2015.
 */
public class Heartbeat implements Runnable {

    // this class functions as a heartbeat to keep the connection alive. Sends a heartbeat, and waits for the server to respond.

    private ClientController controller;

    public Heartbeat(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {

        while(true) {
            controller.transmitRequestToServer(APP_VAR.REQUEST_HEARTBEAT);

            if(controller.amIUpdating()) {
                System.out.println("Server is busy, trying heartbeat later");
            } else {
                controller.setUpdating(true); // zorg dat er niets anders gerequest wordt tijdens de heartbeat
                try {
                    int request = controller.connectionListener().readInt();
                    if(request == APP_VAR.REQUEST_HEARTBEAT) {
                        System.out.println("Server connection is alive");
                    } else {
                        System.out.println("We got something different back from the heartbeat");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            controller.setUpdating(false); // zorg dat er weer dingen gerequest kunnen worden.
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
