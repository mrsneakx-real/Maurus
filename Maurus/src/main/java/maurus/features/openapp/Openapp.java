package maurus.features.openapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.App;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Openapp {
    public static void start(Stage primaryStage, Runnable onDone) throws Exception {
        startApp();

        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(App.getAppclosetime()),actionEvent -> {
            onDone.run();
        }
        ));
        timeline.setCycleCount(1);//ensures it's only called once
        timeline.play();
    }

    public static List<File> findFiles(String path) throws Exception {
        List<File> lnkfiles = new ArrayList<>();
        File folder = new File(path);
        File[] folders = folder.listFiles();

    //so that it doesnt get error, when no acces granted or folder doesnt exist /is empty
    if (folders == null) return lnkfiles;

    for (File f : folders) {
        if (f.getName().toLowerCase().endsWith(".lnk")) {
            lnkfiles.add(f);
        }

        else if (f.isDirectory() && f != null) {
            lnkfiles.addAll(findFiles(f.getAbsolutePath())); }
    }
    return lnkfiles;
    }

    public static void startApp () throws Exception {
        List<File> links = getStartMenuLinks();
        List<String> apps = new ArrayList<>();

        for (File lnk : links) {
            String exe = ResolveShortcut.resolveShortcut(lnk);
            if (isGoodFile(exe)) {
                apps.add(exe);
            }

        }
        if (apps.isEmpty()) {
            System.out.println("No apps found!");
            return;
        }
        String chosen = apps.get((int)(Math.random() * apps.size()));//choosing random file
        System.out.println("This app is started: " + chosen);
        System.out.println(apps.size() + " apps found!");
        new ProcessBuilder("cmd", "/c", "start", "\"\"", chosen).start();
        //so it gets started with all the correct dependexies, beacsue it gets started like a user would tsart it
        //you dont have to make Process processs = new ... here beacue we start it directly
    }

    public static boolean isGoodFile (String path) {
        if (path == null) return false;
        path = path.toLowerCase();

        // skip updaters, uninstallers, ....
        //can be enabled if you want to not launch unsinstallers
        if (path.contains("update")) return false;
        if (path.contains("helper")) return false;
        //just to make sure there are apps with GUI only (cannot be 100% sure but this helps aa good bit)
        return path.endsWith(".exe");
    }

    public static List<File> getStartMenuLinks() throws Exception {
        List<File> links = new ArrayList<>();

        String[] paths = {
                "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs",
                System.getProperty("user.home") + //gets the user start programm folder
                "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs"
        };

        for (String base : paths) {
            links.addAll(findFiles(base));
        }
        return links;
    }



}