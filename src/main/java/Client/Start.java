package Client;

import Client.Controllers.ClientController;

/**
 * Created by rober on 1-11-2015.
 */
public class Start {

    ClientController controller = new ClientController();

    public Start() {
        controller.initComponents();
        controller.createConnection();
    }

    public static void main(String[] args){
        Start start = new Start();
    }

}


