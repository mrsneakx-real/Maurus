package maurus.common.block;

import com.sun.jna.Library;
import com.sun.jna.Native;
    public class KeyBlock {
        public interface User32 extends Library {
            boolean BlockInput(boolean fBlockIt);
        }
        //uses block input to block ouse and keyboard for a given time
        public static void BlockInput(int TimeSleep) throws Exception {
            User32 user32 = Native.load("user32", User32.class);
            System.out.println("Blocking input for " + TimeSleep + " ms");
            user32.BlockInput(true);
            Thread.sleep(TimeSleep);
            user32.BlockInput(false);
            System.out.println("Unblocked.");
        }
    }


