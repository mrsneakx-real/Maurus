package maurus.features.popups.defender;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import maurus.App;
import maurus.features.popups.bounce.Bouncer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class ToastFromJava {

    private final int milchreis;

    // Constructor allows you to set which case to run
    public ToastFromJava(int milchreis) {
        this.milchreis = milchreis;
    }

    // Start method you can call on an instance
    public void start(Runnable onDone) {
        switch (milchreis) {
            case 1:
                runScript("C:/Program Files (x86)/Maurus/defenderoff.ps1");
                break;
            case 2:
                runScript("C:/Program Files (x86)/Maurus/maliciousfile.ps1");
                break;
            case 3:
                runScript("C:/Program Files (x86)/Maurus/sus.ps1");
                break;
        }
        onDone.run();
    }

    // Helper method to run a PowerShell script
    private void runScript(String psScriptPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder( //Makes new ProcessBuilder to start Powershell
                    "powershell.exe", "-ExecutionPolicy", "Bypass", "-File", psScriptPath);

            pb.redirectErrorStream(true); //Redirects all errors from Powershell
            Process process = pb.start(); //Starts the process

            // https://copilot.microsoft.com/shares/APcQjgfn1XrdQVsxdKrqT
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            //Waits until the script is finished.
            int exitCode = process.waitFor();
            System.out.println("PowerShell script exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}