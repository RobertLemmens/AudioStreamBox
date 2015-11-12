package Server.Utility;

import Server.Controllers.ServerController;

/**
 * Created by Robert on 12-Nov-15.
 */
public class ClientUploader implements Runnable {

    private boolean isRunning = true;

    public ClientUploader(ServerController controller) {

    }

    @Override
    public void run() {
        while(isRunning) {
            // get files
        }
    }
}
