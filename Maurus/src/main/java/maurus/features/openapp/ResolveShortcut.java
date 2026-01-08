package maurus.features.openapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class ResolveShortcut {
    //resolve shortcut gets the exe location from the lnk link
    public static String resolveShortcut(File shortcut) throws Exception {
        //resolves the lnk shortcut via a vbc console command/ vbs file
        File script = File.createTempFile("resolve", ".vbs");//creates vbs fle

        String vbs =
                "Set oWS = CreateObject(\"WScript.Shell\")\n" +
                        "Set Lnk = oWS.CreateShortcut(\"" + shortcut.getAbsolutePath().replace("\\", "\\\\") + "\")\n" +
                        //there are 2 \ replaced with 4 \ beacuse for java you need an extra \ to represent a literal backslah
                        "WScript.Echo Lnk.TargetPath\n";

        Files.write(script.toPath(), vbs.getBytes()); // adds the commands from above to the temp vbs file

        Process p = new ProcessBuilder("cscript.exe", "//NoLogo", script.getAbsolutePath())
                .redirectErrorStream(true)
                .start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String output = reader.readLine();

        script.delete();
        return output;
    }
}
