package Server.Utility;

import Server.Controllers.ServerController;
import com.github.axet.vget.VGet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.spec.ECField;

/**
 * Created by rober on 1-11-2015.
 */


public class Downloader {

    public Downloader(ServerController controller){
        System.out.println("Entering constructor of downloader");
    }

    public void startDownload(String url, String path) {
        try {
            // eerst command, dan null, dan de directory waar de cmd opent(hier download youtube-dl automatisch al zijn bestanden.
            Process p = Runtime.getRuntime().exec("cmd /c C:\\Users\\Robert\\IdeaProjects\\AudioStreamBox\\src\\downloadsTest\\youtube-dl.exe "+url, null, new File(path)); //TODO: relative maken

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
}
