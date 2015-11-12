package Server.Utility;

import Server.Controllers.ServerController;
import Standard.APP_VAR;
import com.github.axet.vget.VGet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;

/**
 * Created by rober on 1-11-2015.
 */


public class Downloader implements Runnable{

    private ArrayList<String> urlList;
    private ArrayList<String> downloadedUrls;
    private Thread thread;
    private boolean isRunning = true;

    public Downloader(ServerController controller){
        System.out.println("Entering constructor of downloader");
        urlList = new ArrayList<>();
        downloadedUrls = new ArrayList<>();
        isRunning = true;
        thread = new Thread(this);
        thread.start(); // start looking for downloads
    }

    public void addUrl(String s) {
        urlList.add(s);
    }

    public void startDownload(String url, String path) {
        try {
            urlList.remove(0);
            // eerst command, dan null, dan de directory waar de cmd opent(hier download youtube-dl automatisch al zijn bestanden.
            Process p = Runtime.getRuntime().exec("cmd /c C:\\Users\\Robert\\IdeaProjects\\AudioStreamBox\\src\\downloadsTest\\youtube-dl.exe "+"--extract-audio --audio-format mp3 "+url, null, new File(path)); //TODO: relative maken

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        // check een arraylist met songs( in de controller? of via de factory. Zit er iets nieuws in, download deze dan.
        while(isRunning){
            if(urlList.size() < 1){
                System.out.println("Nothing do download for me");
            } else {
                // download iets
                startDownload(urlList.get(0), APP_VAR.WORKING_DIR);
            }
            try {
                thread.sleep(1000); // check elke seconde voor nieuwe urls
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
