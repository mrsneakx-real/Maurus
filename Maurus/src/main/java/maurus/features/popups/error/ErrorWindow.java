package maurus.features.popups.error;

import com.sun.jna.WString;

public class ErrorWindow {
    public static int errorWindow( String text, String caption, int Iconcode, int Buttontype) {
        //creates an error window with the specified Parameters
        int MB_ICON = Iconcode;
        int MB_BUTTON = Buttontype;
        int MB_TASKMODAL = 0x2000;//so its the right window
        int hwnd = Error.User32.INSTANCE.GetForegroundWindow();

        Error.User32.INSTANCE.MessageBeep(MB_ICON);


        int output = Error.User32.INSTANCE.MessageBoxW(
                hwnd,
                new WString(text),
                new WString(caption),
                MB_BUTTON | MB_ICON | MB_TASKMODAL
        );
        return output;
    }
}
